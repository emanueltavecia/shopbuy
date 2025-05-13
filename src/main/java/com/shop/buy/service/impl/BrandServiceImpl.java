package com.shop.buy.service.impl;

import com.shop.buy.dto.BrandDTO;
import com.shop.buy.exception.ResourceAlreadyExistsException;
import com.shop.buy.exception.ResourceNotFoundException;
import com.shop.buy.model.Brand;
import com.shop.buy.repository.BrandRepository;
import com.shop.buy.service.BrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDTO> findAll() {
        return brandRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO findById(Long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        return convertToDTO(brand);
    }

    @Override
    @Transactional
    public BrandDTO create(BrandDTO brandDTO) {
        if (brandRepository.existsByName(brandDTO.getName())) {
            throw new ResourceAlreadyExistsException("Brand already exists with name: " + brandDTO.getName());
        }
        
        Brand brand = convertToEntity(brandDTO);
        brand = brandRepository.save(brand);
        return convertToDTO(brand);
    }

    @Override
    @Transactional
    public BrandDTO update(Long id, BrandDTO brandDTO) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        
        // Check if name is being changed and if the new name already exists
        if (!existingBrand.getName().equals(brandDTO.getName()) &&
                brandRepository.existsByName(brandDTO.getName())) {
            throw new ResourceAlreadyExistsException("Brand already exists with name: " + brandDTO.getName());
        }
        
        existingBrand.setName(brandDTO.getName());
        existingBrand.setCountry(brandDTO.getCountry());
        existingBrand.setDescription(brandDTO.getDescription());
        
        existingBrand = brandRepository.save(existingBrand);
        return convertToDTO(existingBrand);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found with id: " + id);
        }
        brandRepository.deleteById(id);
    }
    
    private BrandDTO convertToDTO(Brand brand) {
        BrandDTO brandDTO = new BrandDTO();
        BeanUtils.copyProperties(brand, brandDTO);
        return brandDTO;
    }
    
    private Brand convertToEntity(BrandDTO brandDTO) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandDTO, brand);
        return brand;
    }
}
