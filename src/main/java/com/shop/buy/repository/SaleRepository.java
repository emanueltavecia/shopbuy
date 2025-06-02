package com.shop.buy.repository;

import com.shop.buy.model.Sale;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(value = "SELECT * FROM sales", nativeQuery = true)
    List<Sale> findAllSales();

    @Query(value = "SELECT * FROM sales WHERE id = :id", nativeQuery = true)
    Optional<Sale> findSaleById(@Param("id") Long id);

    @Query(value = "SELECT * FROM sales WHERE customer_id = :customerId", nativeQuery = true)
    List<Sale> findSalesByCustomerId(@Param("customerId") Long customerId);

    @Query(value = "SELECT * FROM sales WHERE employee_id = :employeeId", nativeQuery = true)
    List<Sale> findSalesByEmployeeId(@Param("employeeId") Long employeeId);

    @Query(value = "INSERT INTO sales (customer_id, employee_id, sale_date, discount) VALUES (:#{#sale.customer.id}, :#{#sale.employee.id}, :#{#sale.saleDate}, :#{#sale.discount}) RETURNING *", nativeQuery = true)
    Sale saveSale(@Param("sale") Sale sale);

    @Query(value = "UPDATE sales SET customer_id = :#{#sale.customer.id}, employee_id = :#{#sale.employee.id}, sale_date = :#{#sale.saleDate}, discount = :#{#sale.discount} WHERE id = :id RETURNING *", nativeQuery = true)
    Sale updateSale(@Param("id") Long id, @Param("sale") Sale sale);

    @Query(value = "DELETE FROM sales WHERE id = :id", nativeQuery = true)
    @org.springframework.data.jpa.repository.Modifying
    void deleteSale(@Param("id") Long id);
}
