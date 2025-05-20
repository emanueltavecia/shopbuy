package com.shop.buy.service;

import com.shop.buy.dto.BrandDTO;
import java.util.List;

public interface BrandService {
  List<BrandDTO> getAllBrands();

  BrandDTO getBrandById(Long id);

  List<BrandDTO> getBrandsByName(String name);

  BrandDTO createBrand(BrandDTO brandDTO);

  BrandDTO updateBrand(Long id, BrandDTO brandDTO);

  void deleteBrand(Long id);
}
