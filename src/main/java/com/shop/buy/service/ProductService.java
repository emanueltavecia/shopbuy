package com.shop.buy.service;

import com.shop.buy.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO findById(Long id);
    List<ProductDTO> findByCategoryId(Long categoryId);
    List<ProductDTO> findByBrandId(Long brandId);
    ProductDTO create(ProductDTO productDTO);
    ProductDTO update(Long id, ProductDTO productDTO);
    void delete(Long id);
}
