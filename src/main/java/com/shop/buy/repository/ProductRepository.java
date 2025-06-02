package com.shop.buy.repository;

import com.shop.buy.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand JOIN FETCH p.supplier")
    List<Product> findAllProducts();

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand JOIN FETCH p.supplier WHERE p.id = :id")
    Optional<Product> findProductById(@Param("id") Long id);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand JOIN FETCH p.supplier WHERE p.category.id = :categoryId")
    List<Product> findProductsByCategoryId(@Param("categoryId") Long categoryId);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand JOIN FETCH p.supplier WHERE p.brand.id = :brandId")
    List<Product> findProductsByBrandId(@Param("brandId") Long brandId);

    @Query("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand JOIN FETCH p.supplier WHERE p.supplier.id = :supplierId")
    List<Product> findProductsBySupplierId(@Param("supplierId") Long supplierId);

    default Product saveProduct(Product product) {
        return save(product);
    }

    @Modifying
    @Query("UPDATE Product p SET p.name = :#{#product.name}, p.size = :#{#product.size}, " +
            "p.color = :#{#product.color}, p.price = :#{#product.price}, " +
            "p.category = :#{#product.category}, p.brand = :#{#product.brand}, " +
            "p.supplier = :#{#product.supplier} WHERE p.id = :id")
    void updateProductData(@Param("id") Long id, @Param("product") Product product);

    default Product updateProduct(Long id, Product product) {
        product.setId(id);
        return save(product);
    }

    @Modifying
    default void deleteProduct(Long id) {
        deleteById(id);
    }
}
