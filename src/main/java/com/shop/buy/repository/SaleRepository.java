package com.shop.buy.repository;

import com.shop.buy.model.Sale;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

  @Query("SELECT s FROM Sale s")
  List<Sale> findAllSales();

  @Query("SELECT s FROM Sale s WHERE s.customer.id = :customerId")
  List<Sale> findSalesByCustomerId(@Param("customerId") Long customerId);

  @Query("SELECT s FROM Sale s WHERE s.employee.id = :employeeId")
  List<Sale> findSalesByEmployeeId(@Param("employeeId") Long employeeId);

  @org.springframework.data.jpa.repository.Modifying
  @Query("DELETE FROM Sale s WHERE s.id = :id")
  void deleteSale(@Param("id") Long id);
}
