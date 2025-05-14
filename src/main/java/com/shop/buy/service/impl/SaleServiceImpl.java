package com.shop.buy.service.impl;

import com.shop.buy.dto.SaleDTO;
import com.shop.buy.model.Customer;
import com.shop.buy.model.Sale;
import com.shop.buy.repository.CustomerRepository;
import com.shop.buy.repository.SaleRepository;
import com.shop.buy.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CustomerRepository customerRepository) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<SaleDTO> getAllSales() {
        return saleRepository.findAllSales().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SaleDTO getSaleById(Long id) {
        Sale sale = saleRepository.findSaleById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found with id: " + id));
        return convertToDTO(sale);
    }

    @Override
    public List<SaleDTO> getSalesByCustomerId(Long customerId) {
        return saleRepository.findSalesByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SaleDTO> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findSalesByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SaleDTO createSale(SaleDTO saleDTO) {
        Sale sale = convertToEntity(saleDTO);
        Sale savedSale = saleRepository.saveSale(sale);
        return convertToDTO(savedSale);
    }

    @Override
    @Transactional
    public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
        // Verify sale exists
        saleRepository.findSaleById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found with id: " + id));
        
        Sale sale = convertToEntity(saleDTO);
        Sale updatedSale = saleRepository.updateSale(id, sale);
        return convertToDTO(updatedSale);
    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
        // Verify sale exists
        saleRepository.findSaleById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found with id: " + id));
        
        saleRepository.deleteSale(id);
    }

    private SaleDTO convertToDTO(Sale sale) {
        SaleDTO dto = new SaleDTO();
        dto.setId(sale.getId());
        dto.setCustomerId(sale.getCustomer().getId());
        dto.setSaleDate(sale.getSaleDate());
        dto.setTotalValue(sale.getTotalValue());
        return dto;
    }

    private Sale convertToEntity(SaleDTO dto) {
        Sale sale = new Sale();
        sale.setId(dto.getId());
        sale.setSaleDate(dto.getSaleDate());
        sale.setTotalValue(dto.getTotalValue());
        
        Customer customer = customerRepository.findCustomerById(dto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + dto.getCustomerId()));
        sale.setCustomer(customer);
        
        return sale;
    }
}
