package com.shop.buy.service;

import com.shop.buy.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    List<BrandDTO> findAll();
    BrandDTO findById(Long id);
    BrandDTO create(BrandDTO brandDTO);
    BrandDTO update(Long id, BrandDTO brandDTO);
    void delete(Long id);
}
