package com.shop.buy.service.impl;

import com.shop.buy.dto.NestedSaleItemDTO;
import com.shop.buy.dto.SaleDTO;
import com.shop.buy.model.Customer;
import com.shop.buy.model.Employee;
import com.shop.buy.model.PaymentMethod;
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
        .findById(id)
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
    if (saleDTO.getItems() != null
        && !saleDTO.getItems().isEmpty()
        && saleDTO.getDiscount() != null) {
      validateDiscount(saleDTO);
    }

    Sale sale = convertToEntity(saleDTO);

    if (sale.getPaymentMethod() == null) {
      throw new IllegalArgumentException("Método de pagamento é obrigatório");
    }

    Sale savedSale = saleRepository.save(sale);

    if (saleDTO.getItems() != null && !saleDTO.getItems().isEmpty()) {
      List<SaleItem> items = createSaleItems(saleDTO.getItems(), savedSale);
      for (SaleItem item : items) {
        saleItemRepository.saveSaleItem(item);
      }

      savedSale = saleRepository
          .findById(savedSale.getId())
          .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada"));

      List<SaleItem> savedItems = saleItemRepository.findSaleItemsBySaleId(savedSale.getId());
      savedSale.setItems(savedItems);
    }

    return convertToDTO(savedSale);
  }

  private void validateDiscount(SaleDTO saleDTO) {
    BigDecimal subtotal = BigDecimal.ZERO;
    if (saleDTO.getItems() != null) {
      subtotal = saleDTO.getItems().stream()
          .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
          .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    if (saleDTO.getDiscount() != null && saleDTO.getDiscount().compareTo(subtotal) > 0) {
      throw new IllegalArgumentException(
          "O desconto não pode ser maior que o valor total dos itens");
    }
  }

  @Override
  @Transactional
  public SaleDTO updateSale(Long id, SaleDTO saleDTO) {
    if (saleDTO.getItems() != null
        && !saleDTO.getItems().isEmpty()
        && saleDTO.getDiscount() != null) {
      validateDiscount(saleDTO);
    }

    saleRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + id));

    List<SaleItem> existingItems = saleItemRepository.findSaleItemsBySaleId(id);
    for (SaleItem item : existingItems) {
      saleItemRepository.deleteSaleItem(item.getId());
    }

    Sale sale = convertToEntity(saleDTO);
    sale.setId(id);

    if (sale.getPaymentMethod() == null) {
      throw new IllegalArgumentException("Método de pagamento é obrigatório");
    }

    Sale updatedSale = saleRepository.save(sale);

    if (saleDTO.getItems() != null && !saleDTO.getItems().isEmpty()) {
      List<SaleItem> items = createSaleItems(saleDTO.getItems(), updatedSale);
      for (SaleItem item : items) {
        saleItemRepository.saveSaleItem(item);
      }

      List<SaleItem> savedItems = saleItemRepository.findSaleItemsBySaleId(updatedSale.getId());
      updatedSale.setItems(savedItems);
    }

    return convertToDTO(updatedSale);
  }

  @Override
  @Transactional
  public void deleteSale(Long id) {

    saleRepository
        .findById(id)
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

        if (itemDTO.getProductId() == null) {
          throw new IllegalArgumentException("Produto é obrigatório para todos os itens da venda");
        }
        if (itemDTO.getQuantity() == null || itemDTO.getQuantity() < 1) {
          throw new IllegalArgumentException("Quantidade deve ser informada e ser pelo menos 1");
        }
        if (itemDTO.getUnitPrice() == null
            || itemDTO.getUnitPrice().compareTo(BigDecimal.ZERO) <= 0) {
          throw new IllegalArgumentException(
              "Preço unitário deve ser informado e ser um valor positivo");
        }

        Product product = productRepository
            .findProductById(itemDTO.getProductId())
            .orElseThrow(
                () -> new EntityNotFoundException(
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
    dto.setCustomer(sale.getCustomer());
    dto.setCustomerId(sale.getCustomer().getId());
    dto.setEmployee(sale.getEmployee());
    dto.setEmployeeId(sale.getEmployee().getId());
    dto.setSaleDate(sale.getSaleDate());
    dto.setDiscount(sale.getDiscount());
    if (sale.getPaymentMethod() != null) {
      dto.setPaymentMethod(sale.getPaymentMethod().name());
    }

    BigDecimal totalValue = calculateTotalValue(sale);
    dto.setTotalValue(totalValue);

    if (sale.getItems() != null) {
      dto.setItems(
          sale.getItems().stream().map(this::convertToSaleItemDTO).collect(Collectors.toList()));
    }
    return dto;
  }

  private BigDecimal calculateTotalValue(Sale sale) {
    if (sale.getItems() == null || sale.getItems().isEmpty()) {
      return BigDecimal.ZERO;
    }

    BigDecimal total = sale.getItems().stream()
        .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    if (sale.getDiscount() != null) {
      total = total.subtract(sale.getDiscount());
      if (total.compareTo(BigDecimal.ZERO) < 0) {
        total = BigDecimal.ZERO;
      }
    }

    return total;
  }

  private NestedSaleItemDTO convertToSaleItemDTO(SaleItem saleItem) {
    NestedSaleItemDTO dto = new NestedSaleItemDTO();
    dto.setId(saleItem.getId());
    dto.setProduct(saleItem.getProduct());
    dto.setProductId(saleItem.getProduct().getId());
    dto.setQuantity(saleItem.getQuantity());
    dto.setUnitPrice(saleItem.getUnitPrice());
    return dto;
  }

  private Sale convertToEntity(SaleDTO dto) {
    Sale sale = new Sale();
    sale.setId(dto.getId());
    sale.setSaleDate(dto.getSaleDate());
    sale.setDiscount(dto.getDiscount());

    if (dto.getPaymentMethod() != null && !dto.getPaymentMethod().isEmpty()) {
      try {
        sale.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod().toUpperCase()));
      } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException(
            "Método de pagamento inválido: "
                + dto.getPaymentMethod()
                + ". Valores válidos: CREDIT_CARD, BANK_SLIP, PIX");
      }
    } else {
      throw new IllegalArgumentException("Método de pagamento é obrigatório");
    }

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
