package com.shop.buy.service;

import com.shop.buy.dto.ProductDTO;
import java.util.List;

public interface ProductService {
  List<ProductDTO> getAllProducts();

  ProductDTO getProductById(Long id);

  List<ProductDTO> getProductsByCategory(Long categoryId);

  List<ProductDTO> getProductsByBrand(Long brandId);

  List<ProductDTO> getProductsBySupplier(Long supplierId);

  ProductDTO createProduct(ProductDTO productDTO);

  ProductDTO updateProduct(Long id, ProductDTO productDTO);

  void deleteProduct(Long id);
}
