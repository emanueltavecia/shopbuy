package com.shop.buy.service;

import com.shop.buy.dto.SupplierDTO;
import java.util.List;

public interface SupplierService {
    List<SupplierDTO> getAllSuppliers();
    SupplierDTO getSupplierById(Long id);
    List<SupplierDTO> getSuppliersByName(String name);
    SupplierDTO getSupplierByCnpj(String cnpj);
    SupplierDTO getSupplierByEmail(String email);
    SupplierDTO createSupplier(SupplierDTO supplierDTO);
    SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO);
    void deleteSupplier(Long id);
}
