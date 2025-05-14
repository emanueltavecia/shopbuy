package com.shop.buy.repository;

import com.shop.buy.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT * FROM sales", nativeQuery = true)
    List<Sale> findAllSales();
    
    @Query(value = "SELECT * FROM sales WHERE id = :id", nativeQuery = true)
    Optional<Sale> findSaleById(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM sales WHERE customer_id = :customerId", nativeQuery = true)
    List<Sale> findSalesByCustomerId(@Param("customerId") Long customerId);
    
    @Query(value = "SELECT * FROM sales WHERE sale_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    List<Sale> findSalesByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query(value = "INSERT INTO sales (customer_id, sale_date, total_value) VALUES (:#{#sale.customer.id}, :#{#sale.saleDate}, :#{#sale.totalValue}) RETURNING *", nativeQuery = true)
    Sale saveSale(@Param("sale") Sale sale);
    
    @Query(value = "UPDATE sales SET customer_id = :#{#sale.customer.id}, sale_date = :#{#sale.saleDate}, total_value = :#{#sale.totalValue} WHERE id = :id RETURNING *", nativeQuery = true)
    Sale updateSale(@Param("id") Long id, @Param("sale") Sale sale);
    
    @Query(value = "DELETE FROM sales WHERE id = :id", nativeQuery = true)
    void deleteSale(@Param("id") Long id);
}
