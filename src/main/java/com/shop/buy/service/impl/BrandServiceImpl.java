package com.shop.buy.service.impl;

import com.shop.buy.dto.BrandDTO;
import com.shop.buy.exception.DuplicateResourceException;
import com.shop.buy.exception.ResourceNotFoundException;
import com.shop.buy.model.Brand;
import com.shop.buy.repository.BrandRepository;
import com.shop.buy.service.BrandService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    Brand brand =
        brandRepository
            .findBrandById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));
    return convertToDTO(brand);
  }

  @Override
  public List<BrandDTO> getBrandsByName(String name) {
    return brandRepository.findBrandsByNameContaining(name).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public BrandDTO createBrand(BrandDTO brandDTO) {
    // Check if brand with same name already exists
    brandRepository.findBrandsByNameContaining(brandDTO.getName()).stream()
        .filter(b -> b.getName().equalsIgnoreCase(brandDTO.getName()))
        .findFirst()
        .ifPresent(
            brand -> {
              throw new DuplicateResourceException("Marca", "nome", brandDTO.getName());
            });

    try {
      Brand brand = convertToEntity(brandDTO);
      Brand savedBrand = brandRepository.saveBrand(brand);
      return convertToDTO(savedBrand);
    } catch (DataIntegrityViolationException e) {
      // This will catch any database constraint violations
      if (e.getMessage().contains("unique")
          || (e.getRootCause() != null && e.getRootCause().getMessage().contains("unique"))) {
        throw new DuplicateResourceException("Marca com o mesmo nome já existe");
      }
      throw e;
    }
  }

  @Override
  @Transactional
  public BrandDTO updateBrand(Long id, BrandDTO brandDTO) {
    // Verify brand exists
    brandRepository
        .findBrandById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));

    // Check if brand with same name already exists (except for the current brand)
    brandRepository.findBrandsByNameContaining(brandDTO.getName()).stream()
        .filter(b -> b.getName().equalsIgnoreCase(brandDTO.getName()) && !b.getId().equals(id))
        .findFirst()
        .ifPresent(
            brand -> {
              throw new DuplicateResourceException("Marca", "nome", brandDTO.getName());
            });

    try {
      Brand brand = convertToEntity(brandDTO);
      Brand updatedBrand = brandRepository.updateBrand(id, brand);
      return convertToDTO(updatedBrand);
    } catch (DataIntegrityViolationException e) {
      // This will catch any database constraint violations
      if (e.getMessage().contains("unique")
          || (e.getRootCause() != null && e.getRootCause().getMessage().contains("unique"))) {
        throw new DuplicateResourceException("Marca com o mesmo nome já existe");
      }
      throw e;
    }
  }

  @Override
  @Transactional
  public void deleteBrand(Long id) {
    // Verify brand exists
    brandRepository
        .findBrandById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Marca", "id", id));

    try {
      brandRepository.deleteBrand(id);
    } catch (DataIntegrityViolationException e) {
      throw new RuntimeException(
          "Não é possível excluir a marca, pois está sendo referenciada por outros registros");
    }
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
