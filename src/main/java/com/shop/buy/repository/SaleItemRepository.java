package com.shop.buy.repository;

import com.shop.buy.model.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleItemRepository extends JpaRepository<SaleItem, Long> {

    @Query(value = "SELECT * FROM sale_items", nativeQuery = true)
    List<SaleItem> findAllSaleItems();
    
    @Query(value = "SELECT * FROM sale_items WHERE id = :id", nativeQuery = true)
    Optional<SaleItem> findSaleItemById(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM sale_items WHERE sale_id = :saleId", nativeQuery = true)
    List<SaleItem> findSaleItemsBySaleId(@Param("saleId") Long saleId);
    
    @Query(value = "SELECT * FROM sale_items WHERE product_id = :productId", nativeQuery = true)
    List<SaleItem> findSaleItemsByProductId(@Param("productId") Long productId);
    
    @Query(value = "INSERT INTO sale_items (sale_id, product_id, quantity, unit_price) VALUES (:#{#saleItem.sale.id}, :#{#saleItem.product.id}, :#{#saleItem.quantity}, :#{#saleItem.unitPrice}) RETURNING *", nativeQuery = true)
    SaleItem saveSaleItem(@Param("saleItem") SaleItem saleItem);
    
    @Query(value = "UPDATE sale_items SET sale_id = :#{#saleItem.sale.id}, product_id = :#{#saleItem.product.id}, quantity = :#{#saleItem.quantity}, unit_price = :#{#saleItem.unitPrice} WHERE id = :id RETURNING *", nativeQuery = true)
    SaleItem updateSaleItem(@Param("id") Long id, @Param("saleItem") SaleItem saleItem);
    
    @Query(value = "DELETE FROM sale_items WHERE id = :id", nativeQuery = true)
    void deleteSaleItem(@Param("id") Long id);
}
