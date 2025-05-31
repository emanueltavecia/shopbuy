package com.shop.buy.service;

import com.shop.buy.dto.SaleDTO;
import java.util.List;

public interface SaleService {
  List<SaleDTO> getAllSales();

  SaleDTO getSaleById(Long id);

  List<SaleDTO> getSalesByCustomerId(Long customerId);

  List<SaleDTO> getSalesByEmployeeId(Long employeeId);

  SaleDTO createSale(SaleDTO saleDTO);

  SaleDTO updateSale(Long id, SaleDTO saleDTO);

  void deleteSale(Long id);
}
