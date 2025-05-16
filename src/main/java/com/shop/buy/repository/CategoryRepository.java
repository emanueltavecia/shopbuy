package com.shop.buy.repository;

import com.shop.buy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM categories", nativeQuery = true)
    List<Category> findAllCategories();
    
    @Query(value = "SELECT * FROM categories WHERE id = :id", nativeQuery = true)
    Optional<Category> findCategoryById(@Param("id") Long id);
    
    @Query(value = "INSERT INTO categories (name, description) VALUES (:#{#category.name}, :#{#category.description}) RETURNING *", nativeQuery = true)
    Category saveCategory(@Param("category") Category category);
    
    @Query(value = "UPDATE categories SET name = :#{#category.name}, description = :#{#category.description} WHERE id = :id RETURNING *", nativeQuery = true)
    Category updateCategory(@Param("id") Long id, @Param("category") Category category);
    
    @Query(value = "DELETE FROM categories WHERE id = :id", nativeQuery = true)
    void deleteCategory(@Param("id") Long id);
}
