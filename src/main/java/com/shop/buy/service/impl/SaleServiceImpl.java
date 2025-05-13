package com.shop.buy.service.impl;

import com.shop.buy.dto.SaleDTO;
import com.shop.buy.dto.SaleItemDTO;
import com.shop.buy.exception.ResourceNotFoundException;
import com.shop.buy.model.Customer;
import com.shop.buy.model.Product;
import com.shop.buy.model.Sale;
import com.shop.buy.model.SaleItem;
import com.shop.buy.repository.CustomerRepository;
import com.shop.buy.repository.ProductRepository;
import com.shop.buy.repository.SaleItemRepository;
import com.shop.buy.repository.SaleRepository;
import com.shop.buy.service.SaleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final SaleItemRepository saleItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public SaleServiceImpl(SaleRepository saleRepository, 
                          SaleItemRepository saleItemRepository,
                          CustomerRepository customerRepository,
                          ProductRepository productRepository) {
        this.saleRepository = saleRepository;
        this.saleItemRepository = saleItemRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<SaleDTO> findAll() {
        return saleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SaleDTO findById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        return convertToDTO(sale);
    }

    @Override
    public List<SaleDTO> findByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer not found with id: " + customerId);
        }
        return saleRepository.findByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findBySaleDateBetween(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SaleDTO create(SaleDTO saleDTO) {
        Customer customer = customerRepository.findById(saleDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + saleDTO.getCustomerId()));
        
        // Create new sale with customer
        Sale sale = new Sale();
        sale.setCustomer(customer);
        sale.setSaleDate(LocalDateTime.now());
        
        // Calculate total based on items
        BigDecimal total = BigDecimal.ZERO;
        
        // Save sale first to get ID
        sale = saleRepository.save(sale);
        
        List<SaleItem> items = new ArrayList<>();
        
        // Process each sale item
        for (SaleItemDTO itemDTO : saleDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDTO.getProductId()));
            
            SaleItem item = new SaleItem();
            item.setSale(sale);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice() != null ? itemDTO.getUnitPrice() : product.getPrice());
            
            items.add(item);
            
            // Add to total
            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        
        // Save all sale items
        saleItemRepository.saveAll(items);
        
        // Update sale with total and items
        sale.setTotalValue(total);
        sale.setItems(items);
        sale = saleRepository.save(sale);
        
        return convertToDTO(sale);
    }

    @Override
    @Transactional
    public SaleDTO update(Long id, SaleDTO saleDTO) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with id: " + id));
        
        Customer customer = customerRepository.findById(saleDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + saleDTO.getCustomerId()));
        
        existingSale.setCustomer(customer);
        
        // If the date is provided in the DTO, update it, otherwise keep the existing one
        if (saleDTO.getSaleDate() != null) {
            existingSale.setSaleDate(saleDTO.getSaleDate());
        }
        
        // Remove existing items
        saleItemRepository.deleteAll(existingSale.getItems());
        existingSale.getItems().clear();
        
        // Calculate new total based on items
        BigDecimal total = BigDecimal.ZERO;
        List<SaleItem> newItems = new ArrayList<>();
        
        // Process each sale item
        for (SaleItemDTO itemDTO : saleDTO.getItems()) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + itemDTO.getProductId()));
            
            SaleItem item = new SaleItem();
            item.setSale(existingSale);
            item.setProduct(product);
            item.setQuantity(itemDTO.getQuantity());
            item.setUnitPrice(itemDTO.getUnitPrice() != null ? itemDTO.getUnitPrice() : product.getPrice());
            
            newItems.add(item);
            
            // Add to total
            BigDecimal itemTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }
        
        // Save all new sale items
        saleItemRepository.saveAll(newItems);
        
        // Update sale with total and items
        existingSale.setTotalValue(total);
        existingSale.setItems(newItems);
        existingSale = saleRepository.save(existingSale);
        
        return convertToDTO(existingSale);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!saleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sale not found with id: " + id);
        }
        saleRepository.deleteById(id);
    }
    
    private SaleDTO convertToDTO(Sale sale) {
        SaleDTO saleDTO = new SaleDTO();
        saleDTO.setId(sale.getId());
        saleDTO.setCustomerId(sale.getCustomer() != null ? sale.getCustomer().getId() : null);
        saleDTO.setSaleDate(sale.getSaleDate());
        saleDTO.setTotalValue(sale.getTotalValue());
        
        List<SaleItemDTO> itemDTOs = sale.getItems().stream()
                .map(this::convertToItemDTO)
                .collect(Collectors.toList());
        saleDTO.setItems(itemDTOs);
        
        return saleDTO;
    }
    
    private SaleItemDTO convertToItemDTO(SaleItem item) {
        SaleItemDTO itemDTO = new SaleItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setSaleId(item.getSale() != null ? item.getSale().getId() : null);
        itemDTO.setProductId(item.getProduct() != null ? item.getProduct().getId() : null);
        itemDTO.setQuantity(item.getQuantity());
        itemDTO.setUnitPrice(item.getUnitPrice());
        return itemDTO;
    }
}
