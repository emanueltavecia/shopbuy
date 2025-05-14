package com.shop.buy.service.impl;

import com.shop.buy.dto.ProductDTO;
import com.shop.buy.model.Brand;
import com.shop.buy.model.Category;
import com.shop.buy.model.Product;
import com.shop.buy.repository.BrandRepository;
import com.shop.buy.repository.CategoryRepository;
import com.shop.buy.repository.ProductRepository;
import com.shop.buy.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, 
                              CategoryRepository categoryRepository, 
                              BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAllProducts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> getProductsByName(String name) {
        return productRepository.findProductsByNameContaining(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByCategory(Long categoryId) {
        return productRepository.findProductsByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByBrand(Long brandId) {
        return productRepository.findProductsByBrandId(brandId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.findProductsByPriceRange(minPrice, maxPrice).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.saveProduct(product);
        return convertToDTO(savedProduct);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        // Verify product exists
        productRepository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        
        Product product = convertToEntity(productDTO);
        Product updatedProduct = productRepository.updateProduct(id, product);
        return convertToDTO(updatedProduct);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        // Verify product exists
        productRepository.findProductById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
        
        productRepository.deleteProduct(id);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSize(product.getSize());
        dto.setColor(product.getColor());
        dto.setPrice(product.getPrice());
        dto.setCategoryId(product.getCategory().getId());
        dto.setBrandId(product.getBrand().getId());
        return dto;
    }

    private Product convertToEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setSize(dto.getSize());
        product.setColor(dto.getColor());
        product.setPrice(dto.getPrice());
        
        Category category = categoryRepository.findCategoryById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + dto.getCategoryId()));
        product.setCategory(category);
        
        Brand brand = brandRepository.findBrandById(dto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with id: " + dto.getBrandId()));
        product.setBrand(brand);
        
        return product;
    }
}
