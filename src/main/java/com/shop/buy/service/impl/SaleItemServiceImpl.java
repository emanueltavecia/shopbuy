package com.shop.buy.service.impl;

import com.shop.buy.dto.SaleItemDTO;
import com.shop.buy.model.Product;
import com.shop.buy.model.Sale;
import com.shop.buy.model.SaleItem;
import com.shop.buy.repository.ProductRepository;
import com.shop.buy.repository.SaleItemRepository;
import com.shop.buy.repository.SaleRepository;
import com.shop.buy.service.SaleItemService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleItemServiceImpl implements SaleItemService {

  private final SaleItemRepository saleItemRepository;
  private final SaleRepository saleRepository;
  private final ProductRepository productRepository;

  @Autowired
  public SaleItemServiceImpl(
      SaleItemRepository saleItemRepository,
      SaleRepository saleRepository,
      ProductRepository productRepository) {
    this.saleItemRepository = saleItemRepository;
    this.saleRepository = saleRepository;
    this.productRepository = productRepository;
  }

  @Override
  public List<SaleItemDTO> getAllSaleItems() {
    return saleItemRepository.findAllSaleItems().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public SaleItemDTO getSaleItemById(Long id) {
    SaleItem saleItem =
        saleItemRepository
            .findSaleItemById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Item de venda não encontrado com id: " + id));
    return convertToDTO(saleItem);
  }

  @Override
  public List<SaleItemDTO> getSaleItemsBySaleId(Long saleId) {
    return saleItemRepository.findSaleItemsBySaleId(saleId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public List<SaleItemDTO> getSaleItemsByProductId(Long productId) {
    return saleItemRepository.findSaleItemsByProductId(productId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public SaleItemDTO createSaleItem(SaleItemDTO saleItemDTO) {
    SaleItem saleItem = convertToEntity(saleItemDTO);
    SaleItem savedSaleItem = saleItemRepository.saveSaleItem(saleItem);
    return convertToDTO(savedSaleItem);
  }

  @Override
  @Transactional
  public SaleItemDTO updateSaleItem(Long id, SaleItemDTO saleItemDTO) {

    saleItemRepository
        .findSaleItemById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("Item de venda não encontrado com id: " + id));

    SaleItem saleItem = convertToEntity(saleItemDTO);
    SaleItem updatedSaleItem = saleItemRepository.updateSaleItem(id, saleItem);
    return convertToDTO(updatedSaleItem);
  }

  @Override
  @Transactional
  public void deleteSaleItem(Long id) {

    saleItemRepository
        .findSaleItemById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("Item de venda não encontrado com id: " + id));

    saleItemRepository.deleteSaleItem(id);
  }

  private SaleItemDTO convertToDTO(SaleItem saleItem) {
    SaleItemDTO dto = new SaleItemDTO();
    dto.setId(saleItem.getId());
    dto.setSaleId(saleItem.getSale().getId());
    dto.setProductId(saleItem.getProduct().getId());
    dto.setQuantity(saleItem.getQuantity());
    dto.setUnitPrice(saleItem.getUnitPrice());
    return dto;
  }

  private SaleItem convertToEntity(SaleItemDTO dto) {
    SaleItem saleItem = new SaleItem();
    saleItem.setId(dto.getId());
    saleItem.setQuantity(dto.getQuantity());
    saleItem.setUnitPrice(dto.getUnitPrice());

    Sale sale =
        saleRepository
            .findSaleById(dto.getSaleId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException("Venda não encontrada com id: " + dto.getSaleId()));
    saleItem.setSale(sale);

    Product product =
        productRepository
            .findProductById(dto.getProductId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Produto não encontrado com id: " + dto.getProductId()));
    saleItem.setProduct(product);

    return saleItem;
  }
}
