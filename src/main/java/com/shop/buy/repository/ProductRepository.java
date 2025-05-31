package com.shop.buy.repository;

import com.shop.buy.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  @Query(value = "SELECT * FROM products", nativeQuery = true)
  List<Product> findAllProducts();

  @Query(value = "SELECT * FROM products WHERE id = :id", nativeQuery = true)
  Optional<Product> findProductById(@Param("id") Long id);

  @Query(value = "SELECT * FROM products WHERE category_id = :categoryId", nativeQuery = true)
  List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);

  @Query(value = "SELECT * FROM products WHERE brand_id = :brandId", nativeQuery = true)
  List<Product> findProductsByBrandId(@Param("brandId") Long brandId);

  @Query(value = "SELECT * FROM products WHERE supplier_id = :supplierId", nativeQuery = true)
  List<Product> findProductsBySupplierId(@Param("supplierId") Long supplierId);

  @Query(
      value =
          "INSERT INTO products (name, size, color, price, category_id, brand_id, supplier_id) VALUES (:#{#product.name}, :#{#product.size}, :#{#product.color}, :#{#product.price}, :#{#product.category.id}, :#{#product.brand.id}, :#{#product.supplier.id}) RETURNING *",
      nativeQuery = true)
  Product saveProduct(@Param("product") Product product);

  @Query(
      value =
          "UPDATE products SET name = :#{#product.name}, size = :#{#product.size}, color = :#{#product.color}, price = :#{#product.price}, category_id = :#{#product.category.id}, brand_id = :#{#product.brand.id}, supplier_id = :#{#product.supplier.id} WHERE id = :id RETURNING *",
      nativeQuery = true)
  Product updateProduct(@Param("id") Long id, @Param("product") Product product);

  @Query(value = "DELETE FROM products WHERE id = :id", nativeQuery = true)
  @org.springframework.data.jpa.repository.Modifying
  void deleteProduct(@Param("id") Long id);
}
