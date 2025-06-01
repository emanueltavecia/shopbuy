package com.shop.buy.service;

import com.shop.buy.dto.DirectSaleItemDTO;
import java.util.List;

public interface SaleItemService {
  List<DirectSaleItemDTO> getAllSaleItems();

  DirectSaleItemDTO getSaleItemById(Long id);

  List<DirectSaleItemDTO> getSaleItemsBySaleId(Long saleId);

  List<DirectSaleItemDTO> getSaleItemsByProductId(Long productId);

  DirectSaleItemDTO createSaleItem(DirectSaleItemDTO saleItemDTO);

  DirectSaleItemDTO updateSaleItem(Long id, DirectSaleItemDTO saleItemDTO);

  void deleteSaleItem(Long id);
}
