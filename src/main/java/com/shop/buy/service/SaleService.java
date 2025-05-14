package com.shop.buy.service;

import com.shop.buy.dto.SaleDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {
    List<SaleDTO> getAllSales();
    SaleDTO getSaleById(Long id);
    List<SaleDTO> getSalesByCustomerId(Long customerId);
    List<SaleDTO> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    SaleDTO createSale(SaleDTO saleDTO);
    SaleDTO updateSale(Long id, SaleDTO saleDTO);
    void deleteSale(Long id);
}
