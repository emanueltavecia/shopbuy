package com.shop.buy.service.impl;

import com.shop.buy.dto.BrandDTO;
import com.shop.buy.model.Brand;
import com.shop.buy.repository.BrandRepository;
import com.shop.buy.service.BrandService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<BrandDTO> getAllBrands() {
        return brandRepository.findAllBrands().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BrandDTO getBrandById(Long id) {
        Brand brand = brandRepository.findBrandById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
        return convertToDTO(brand);
    }

    @Override
    public List<BrandDTO> getBrandsByName(String name) {
        return brandRepository.findBrandsByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BrandDTO> getBrandsByCountry(String country) {
        return brandRepository.findBrandsByCountry(country).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BrandDTO createBrand(BrandDTO brandDTO) {
        Brand brand = convertToEntity(brandDTO);
        Brand savedBrand = brandRepository.saveBrand(brand);
        return convertToDTO(savedBrand);
    }

    @Override
    @Transactional
    public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
        // Verify brand exists
        brandRepository.findBrandById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
        
        Brand brand = convertToEntity(brandDTO);
        Brand updatedBrand = brandRepository.updateBrand(id, brand);
        return convertToDTO(updatedBrand);
    }

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        // Verify brand exists
        brandRepository.findBrandById(id)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + id));
        
        brandRepository.deleteBrand(id);
    }

    private BrandDTO convertToDTO(Brand brand) {
        BrandDTO dto = new BrandDTO();
        dto.setId(brand.getId());
        dto.setName(brand.getName());
        dto.setCountry(brand.getCountry());
        dto.setDescription(brand.getDescription());
        return dto;
    }

    private Brand convertToEntity(BrandDTO dto) {
        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setName(dto.getName());
        brand.setCountry(dto.getCountry());
        brand.setDescription(dto.getDescription());
        return brand;
    }
}
