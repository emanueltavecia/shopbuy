package com.shop.buy.service.impl;

import com.shop.buy.dto.ProductDTO;
import com.shop.buy.exception.ResourceNotFoundException;
import com.shop.buy.model.Brand;
import com.shop.buy.model.Category;
import com.shop.buy.model.Product;
import com.shop.buy.repository.BrandRepository;
import com.shop.buy.repository.CategoryRepository;
import com.shop.buy.repository.ProductRepository;
import com.shop.buy.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductServiceImpl(ProductRepository productRepository, 
                             CategoryRepository categoryRepository,
                             BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return convertToDTO(product);
    }

    @Override
    public List<ProductDTO> findByCategoryId(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> findByBrandId(Long brandId) {
        if (!brandRepository.existsById(brandId)) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }
        return productRepository.findByBrandId(brandId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
        
        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + productDTO.getBrandId()));
        
        Product product = convertToEntity(productDTO);
        product.setCategory(category);
        product.setBrand(brand);
        
        product = productRepository.save(product);
        return convertToDTO(product);
    }

    @Override
    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDTO.getCategoryId()));
        
        Brand brand = brandRepository.findById(productDTO.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + productDTO.getBrandId()));
        
        existingProduct.setName(productDTO.getName());
        existingProduct.setSize(productDTO.getSize());
        existingProduct.setColor(productDTO.getColor());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setCategory(category);
        existingProduct.setBrand(brand);
        
        existingProduct = productRepository.save(existingProduct);
        return convertToDTO(existingProduct);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        productDTO.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        productDTO.setBrandId(product.getBrand() != null ? product.getBrand().getId() : null);
        return productDTO;
    }
    
    private Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        // Category and Brand will be set separately
        return product;
    }
}
