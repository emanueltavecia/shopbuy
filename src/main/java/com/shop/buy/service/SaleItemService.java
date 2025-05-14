package com.shop.buy.service;

import com.shop.buy.dto.SaleItemDTO;
import java.util.List;

public interface SaleItemService {
    List<SaleItemDTO> getAllSaleItems();
    SaleItemDTO getSaleItemById(Long id);
    List<SaleItemDTO> getSaleItemsBySaleId(Long saleId);
    List<SaleItemDTO> getSaleItemsByProductId(Long productId);
    SaleItemDTO createSaleItem(SaleItemDTO saleItemDTO);
    SaleItemDTO updateSaleItem(Long id, SaleItemDTO saleItemDTO);
    void deleteSaleItem(Long id);
}
