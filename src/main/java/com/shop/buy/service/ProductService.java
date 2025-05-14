package com.shop.buy.service;

import com.shop.buy.dto.ProductDTO;
import com.shop.buy.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    List<ProductDTO> getProductsByName(String name);
    List<ProductDTO> getProductsByCategory(Long categoryId);
    List<ProductDTO> getProductsByBrand(Long brandId);
    List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
