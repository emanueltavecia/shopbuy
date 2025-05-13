package com.shop.buy.service;

import com.shop.buy.dto.SupplierDTO;

import java.util.List;

public interface SupplierService {
    List<SupplierDTO> findAll();
    SupplierDTO findById(Long id);
    SupplierDTO findByCnpj(String cnpj);
    SupplierDTO findByEmail(String email);
    SupplierDTO create(SupplierDTO supplierDTO);
    SupplierDTO update(Long id, SupplierDTO supplierDTO);
    void delete(Long id);
}
