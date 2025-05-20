package com.shop.buy.service.impl;

import com.shop.buy.dto.SupplierDTO;
import com.shop.buy.model.Supplier;
import com.shop.buy.repository.SupplierRepository;
import com.shop.buy.service.SupplierService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SupplierServiceImpl implements SupplierService {

  private final SupplierRepository supplierRepository;

  @Autowired
  public SupplierServiceImpl(SupplierRepository supplierRepository) {
    this.supplierRepository = supplierRepository;
  }

  @Override
  public List<SupplierDTO> getAllSuppliers() {
    return supplierRepository.findAllSuppliers().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public SupplierDTO getSupplierById(Long id) {
    Supplier supplier =
        supplierRepository
            .findSupplierById(id)
            .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));
    return convertToDTO(supplier);
  }

  @Override
  @Transactional
  public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
    Supplier supplier = convertToEntity(supplierDTO);
    Supplier savedSupplier = supplierRepository.saveSupplier(supplier);
    return convertToDTO(savedSupplier);
  }

  @Override
  @Transactional
  public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
    // Verify supplier exists
    supplierRepository
        .findSupplierById(id)
        .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));

    Supplier supplier = convertToEntity(supplierDTO);
    Supplier updatedSupplier = supplierRepository.updateSupplier(id, supplier);
    return convertToDTO(updatedSupplier);
  }

  @Override
  @Transactional
  public void deleteSupplier(Long id) {
    // Verify supplier exists
    supplierRepository
        .findSupplierById(id)
        .orElseThrow(() -> new EntityNotFoundException("Supplier not found with id: " + id));

    supplierRepository.deleteSupplier(id);
  }

  private SupplierDTO convertToDTO(Supplier supplier) {
    SupplierDTO dto = new SupplierDTO();
    dto.setId(supplier.getId());
    dto.setName(supplier.getName());
    dto.setCnpj(supplier.getCnpj());
    dto.setPhone(supplier.getPhone());
    dto.setEmail(supplier.getEmail());
    return dto;
  }

  private Supplier convertToEntity(SupplierDTO dto) {
    Supplier supplier = new Supplier();
    supplier.setId(dto.getId());
    supplier.setName(dto.getName());
    supplier.setCnpj(dto.getCnpj());
    supplier.setPhone(dto.getPhone());
    supplier.setEmail(dto.getEmail());
    return supplier;
  }
}
