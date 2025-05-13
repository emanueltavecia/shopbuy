package com.shop.buy.service.impl;

import com.shop.buy.dto.SupplierDTO;
import com.shop.buy.exception.ResourceAlreadyExistsException;
import com.shop.buy.exception.ResourceNotFoundException;
import com.shop.buy.model.Supplier;
import com.shop.buy.repository.SupplierRepository;
import com.shop.buy.service.SupplierService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<SupplierDTO> findAll() {
        return supplierRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SupplierDTO findById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return convertToDTO(supplier);
    }

    @Override
    public SupplierDTO findByCnpj(String cnpj) {
        Supplier supplier = supplierRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with CNPJ: " + cnpj));
        return convertToDTO(supplier);
    }

    @Override
    public SupplierDTO findByEmail(String email) {
        Supplier supplier = supplierRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with email: " + email));
        return convertToDTO(supplier);
    }

    @Override
    @Transactional
    public SupplierDTO create(SupplierDTO supplierDTO) {
        if (supplierRepository.existsByCnpj(supplierDTO.getCnpj())) {
            throw new ResourceAlreadyExistsException("Supplier already exists with CNPJ: " + supplierDTO.getCnpj());
        }
        
        if (supplierRepository.existsByEmail(supplierDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Supplier already exists with email: " + supplierDTO.getEmail());
        }
        
        Supplier supplier = convertToEntity(supplierDTO);
        supplier = supplierRepository.save(supplier);
        return convertToDTO(supplier);
    }

    @Override
    @Transactional
    public SupplierDTO update(Long id, SupplierDTO supplierDTO) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        
        // Check if CNPJ is being changed and if the new CNPJ already exists
        if (!existingSupplier.getCnpj().equals(supplierDTO.getCnpj()) &&
                supplierRepository.existsByCnpj(supplierDTO.getCnpj())) {
            throw new ResourceAlreadyExistsException("Supplier already exists with CNPJ: " + supplierDTO.getCnpj());
        }
        
        // Check if email is being changed and if the new email already exists
        if (!existingSupplier.getEmail().equals(supplierDTO.getEmail()) &&
                supplierRepository.existsByEmail(supplierDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Supplier already exists with email: " + supplierDTO.getEmail());
        }
        
        existingSupplier.setName(supplierDTO.getName());
        existingSupplier.setCnpj(supplierDTO.getCnpj());
        existingSupplier.setPhone(supplierDTO.getPhone());
        existingSupplier.setEmail(supplierDTO.getEmail());
        
        existingSupplier = supplierRepository.save(existingSupplier);
        return convertToDTO(existingSupplier);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
    
    private SupplierDTO convertToDTO(Supplier supplier) {
        SupplierDTO supplierDTO = new SupplierDTO();
        BeanUtils.copyProperties(supplier, supplierDTO);
        return supplierDTO;
    }
    
    private Supplier convertToEntity(SupplierDTO supplierDTO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(supplierDTO, supplier);
        return supplier;
    }
}
