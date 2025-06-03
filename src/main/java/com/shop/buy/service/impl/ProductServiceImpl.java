package com.shop.buy.service.impl;

import com.shop.buy.dto.ProductDTO;
import com.shop.buy.model.Brand;
import com.shop.buy.model.Category;
import com.shop.buy.model.Product;
import com.shop.buy.model.Supplier;
import com.shop.buy.repository.BrandRepository;
import com.shop.buy.repository.CategoryRepository;
import com.shop.buy.repository.ProductRepository;
import com.shop.buy.repository.SupplierRepository;
import com.shop.buy.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final BrandRepository brandRepository;
  private final SupplierRepository supplierRepository;

  @Autowired
  public ProductServiceImpl(
      ProductRepository productRepository,
      CategoryRepository categoryRepository,
      BrandRepository brandRepository,
      SupplierRepository supplierRepository) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.brandRepository = brandRepository;
    this.supplierRepository = supplierRepository;
  }

  @Override
  public List<ProductDTO> getAllProducts() {
    return productRepository.findAllProducts().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public ProductDTO getProductById(Long id) {
    Product product =
        productRepository
            .findProductById(id)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));
    return convertToDTO(product);
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
  public List<ProductDTO> getProductsBySupplier(Long supplierId) {
    return productRepository.findProductsBySupplierId(supplierId).stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  @Transactional
  public ProductDTO createProduct(ProductDTO productDTO) {
    Product product = convertToEntity(productDTO);
    Product savedProduct = productRepository.save(product);
    return convertToDTO(savedProduct);
  }

  @Override
  @Transactional
  public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
    productRepository
        .findProductById(id)
        .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));

    Product product = convertToEntity(productDTO);
    product.setId(id);
    Product updatedProduct = productRepository.save(product);
    return convertToDTO(updatedProduct);
  }

  @Override
  @Transactional
  public void deleteProduct(Long id) {
    productRepository
        .findProductById(id)
        .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));

    productRepository.deleteById(id);
  }

  private ProductDTO convertToDTO(Product product) {
    ProductDTO dto = new ProductDTO();
    dto.setId(product.getId());
    dto.setName(product.getName());
    dto.setSize(product.getSize());
    dto.setColor(product.getColor());
    dto.setPrice(product.getPrice());
    dto.setCategory(product.getCategory());
    dto.setCategoryId(product.getCategory().getId());
    dto.setBrand(product.getBrand());
    dto.setBrandId(product.getBrand().getId());
    dto.setSupplier(product.getSupplier());
    dto.setSupplierId(product.getSupplier().getId());
    return dto;
  }

  private Product convertToEntity(ProductDTO dto) {
    Product product = new Product();
    product.setId(dto.getId());
    product.setName(dto.getName());
    product.setSize(dto.getSize());
    product.setColor(dto.getColor());
    product.setPrice(dto.getPrice());

    Category category =
        categoryRepository
            .findCategoryById(dto.getCategoryId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Categoria não encontrada com id: " + dto.getCategoryId()));
    product.setCategory(category);

    Brand brand =
        brandRepository
            .findBrandById(dto.getBrandId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Marca não encontrada com id: " + dto.getBrandId()));
    product.setBrand(brand);

    Supplier supplier =
        supplierRepository
            .findSupplierById(dto.getSupplierId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Fornecedor não encontrado com id: " + dto.getSupplierId()));
    product.setSupplier(supplier);

    return product;
  }
}
