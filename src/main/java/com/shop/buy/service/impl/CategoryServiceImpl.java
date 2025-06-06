package com.shop.buy.service.impl;

import com.shop.buy.dto.CategoryDTO;
import com.shop.buy.model.Category;
import com.shop.buy.repository.CategoryRepository;
import com.shop.buy.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryServiceImpl(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  @Override
  public List<CategoryDTO> getAllCategories() {
    return categoryRepository.findAllCategories().stream()
        .map(this::convertToDTO)
        .collect(Collectors.toList());
  }

  @Override
  public CategoryDTO getCategoryById(Long id) {
    Category category =
        categoryRepository
            .findCategoryById(id)
            .orElseThrow(
                () -> new EntityNotFoundException("Categoria não encontrada com id: " + id));
    return convertToDTO(category);
  }

  @Override
  @Transactional
  public CategoryDTO createCategory(CategoryDTO categoryDTO) {
    Category category = convertToEntity(categoryDTO);
    Category savedCategory = categoryRepository.saveCategory(category);
    return convertToDTO(savedCategory);
  }

  @Override
  @Transactional
  public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {

    categoryRepository
        .findCategoryById(id)
        .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + id));

    Category category = convertToEntity(categoryDTO);
    Category updatedCategory = categoryRepository.updateCategory(id, category);
    return convertToDTO(updatedCategory);
  }

  @Override
  @Transactional
  public void deleteCategory(Long id) {

    categoryRepository
        .findCategoryById(id)
        .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + id));

    categoryRepository.deleteCategory(id);
  }

  private CategoryDTO convertToDTO(Category category) {
    CategoryDTO dto = new CategoryDTO();
    dto.setId(category.getId());
    dto.setName(category.getName());
    dto.setDescription(category.getDescription());
    return dto;
  }

  private Category convertToEntity(CategoryDTO dto) {
    Category category = new Category();
    category.setId(dto.getId());
    category.setName(dto.getName());
    category.setDescription(dto.getDescription());
    return category;
  }
}
