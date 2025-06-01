package com.shop.buy.service.impl;

import com.shop.buy.dto.SaleDTO;
import com.shop.buy.dto.NestedSaleItemDTO;
import com.shop.buy.model.Customer;
import com.shop.buy.model.Employee;
import com.shop.buy.model.Product;
import com.shop.buy.model.Sale;
import com.shop.buy.model.SaleItem;
import com.shop.buy.repository.CustomerRepository;
import com.shop.buy.repository.EmployeeRepository;
import com.shop.buy.repository.ProductRepository;
import com.shop.buy.repository.SaleItemRepository;
import com.shop.buy.repository.SaleRepository;
import com.shop.buy.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleServiceImpl implements SaleService {

  private final SaleRepository saleRepository;
  private final SaleItemRepository saleItemRepository;
  private final CustomerRepository customerRepository;
  private final EmployeeRepository employeeRepository;
  private final ProductRepository productRepository;

  @Autowired
  public SaleServiceImpl(
      SaleRepository saleRepository,
      SaleItemRepository saleItemRepository,
      CustomerRepository customerRepository,
      EmployeeRepository employeeRepository,
      ProductRepository productRepository) {
    this.saleRepository = saleRepository;
    this.saleItemRepository = saleItemRepository;
    this.customerRepository = customerRepository;
    this.employeeRepository = employeeRepository;
    this.productRepository = productRepository;
  }

  @Override
  public List<SaleDTO> getAllSales() {
    return saleRepository.findAllSales().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public SaleDTO getSaleById(Long id) {
    Sale sale = saleRepository
        .findSaleById(id)
        .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + id));
    return convertToDTO(sale);
  }

  @Override
  public List<SaleDTO> getSalesByCustomerId(Long customerId) {
    return saleRepository.findSalesByCustomerId(customerId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<SaleDTO> getSalesByEmployeeId(Long employeeId) {
    return saleRepository.findSalesByEmployeeId(employeeId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public SaleDTO createSale(SaleDTO saleDTO) {
    Sale sale = convertToEntity(saleDTO);
    Sale savedSale = saleRepository.saveSale(sale);

    // Create and save sale items
    if (saleDTO.getItems() != null && !saleDTO.getItems().isEmpty()) {
      List<SaleItem> items = createSaleItems(saleDTO.getItems(), savedSale);
      for (SaleItem item : items) {
        saleItemRepository.saveSaleItem(item);
      }

      // Fetch the complete sale with items
      savedSale = saleRepository.findSaleById(savedSale.getId())
          .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));
    }

    return convertToDTO(savedSale);
  }

  @Override
  @Transactional
  public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
    // Verify sale exists
    saleRepository
        .findSaleById(id)
        .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + id));

    // Delete existing sale items
    List<SaleItem> existingItems = saleItemRepository.findSaleItemsBySaleId(id);
    for (SaleItem item : existingItems) {
      saleItemRepository.deleteSaleItem(item.getId());
    }

    // Update the sale
    Sale sale = convertToEntity(saleDTO);
    Sale updatedSale = saleRepository.updateSale(id, sale);

    // Create new sale items
    List<SaleItem> savedItems = createSaleItems(saleDTO.getItems(), updatedSale);
    updatedSale.setItems(savedItems);

    return convertToDTO(updatedSale);
  }

  @Override
  @Transactional
  public void deleteSale(Long id) {

    saleRepository
        .findSaleById(id)
        .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + id));

    List<SaleItem> items = saleItemRepository.findSaleItemsBySaleId(id);
    for (SaleItem item : items) {
      saleItemRepository.deleteSaleItem(item.getId());
    }

    saleRepository.deleteSale(id);
  }

  private List<SaleItem> createSaleItems(List<NestedSaleItemDTO> itemDTOs, Sale sale) {
    List<SaleItem> items = new ArrayList<>();

    if (itemDTOs != null && !itemDTOs.isEmpty()) {
      for (NestedSaleItemDTO itemDTO : itemDTOs) {
        // Validação adicional de itens
        if (itemDTO.getProductId() == null) {
          throw new IllegalArgumentException("Produto é obrigatório para todos os itens da venda");
        }
        if (itemDTO.getQuantity() == null || itemDTO.getQuantity() < 1) {
          throw new IllegalArgumentException("Quantidade deve ser informada e ser pelo menos 1");
        }
        if (itemDTO.getUnitPrice() == null || itemDTO.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
          throw new IllegalArgumentException("Preço unitário deve ser informado e ser um valor positivo");
        }

        Product product = productRepository.findProductById(itemDTO.getProductId())
            .orElseThrow(() -> new EntityNotFoundException(
                "Produto não encontrado com id: " + itemDTO.getProductId()));

        SaleItem saleItem = new SaleItem();
        saleItem.setSale(sale);
        saleItem.setProduct(product);
        saleItem.setQuantity(itemDTO.getQuantity());
        saleItem.setUnitPrice(itemDTO.getUnitPrice());
        items.add(saleItem);
      }
    }

    return items;
  }

  private SaleDTO convertToDTO(Sale sale) {
    SaleDTO dto = new SaleDTO();
    dto.setId(sale.getId());
    dto.setCustomerId(sale.getCustomer().getId());
    dto.setEmployeeId(sale.getEmployee().getId());
    dto.setSaleDate(sale.getSaleDate());
    dto.setTotalValue(sale.getTotalValue());

    if (sale.getItems() != null) {
      dto.setItems(
          sale.getItems().stream()
              .map(this::convertToSaleItemDTO)
              .collect(Collectors.toList()));
    }
    return dto;
  }

  private NestedSaleItemDTO convertToSaleItemDTO(SaleItem saleItem) {
    NestedSaleItemDTO dto = new NestedSaleItemDTO();
    dto.setId(saleItem.getId());
    dto.setProductId(saleItem.getProduct().getId());
    dto.setQuantity(saleItem.getQuantity());
    dto.setUnitPrice(saleItem.getUnitPrice());
    return dto;
  }

  private Sale convertToEntity(SaleDTO dto) {
    Sale sale = new Sale();
    sale.setId(dto.getId());
    sale.setSaleDate(dto.getSaleDate());
    sale.setTotalValue(dto.getTotalValue());

    Customer customer = customerRepository
        .findCustomerById(dto.getCustomerId())
        .orElseThrow(
            () -> new EntityNotFoundException(
                "Cliente não encontrado com id: " + dto.getCustomerId()));
    sale.setCustomer(customer);

    Employee employee = employeeRepository
        .findEmployeeById(dto.getEmployeeId())
        .orElseThrow(
            () -> new EntityNotFoundException(
                "Funcionário não encontrado com id: " + dto.getEmployeeId()));
    sale.setEmployee(employee);

    return sale;
  }
}
