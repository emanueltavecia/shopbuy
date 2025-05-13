package com.shop.buy.service;

import com.shop.buy.dto.SaleDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {
    List<SaleDTO> findAll();
    SaleDTO findById(Long id);
    List<SaleDTO> findByCustomerId(Long customerId);
    List<SaleDTO> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    SaleDTO create(SaleDTO saleDTO);
    SaleDTO update(Long id, SaleDTO saleDTO);
    void delete(Long id);
}
