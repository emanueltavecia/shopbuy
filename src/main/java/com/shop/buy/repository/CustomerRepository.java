package com.shop.buy.repository;

import com.shop.buy.model.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query(value = "SELECT * FROM customers", nativeQuery = true)
  List<Customer> findAllCustomers();

  @Query(value = "SELECT * FROM customers WHERE id = :id", nativeQuery = true)
  Optional<Customer> findCustomerById(@Param("id") Long id);

  @Query(
      value =
          "INSERT INTO customers (name, cpf, phone, email) VALUES (:#{#customer.name}, :#{#customer.cpf}, :#{#customer.phone}, :#{#customer.email}) RETURNING *",
      nativeQuery = true)
  Customer saveCustomer(@Param("customer") Customer customer);

  @Query(
      value =
          "UPDATE customers SET name = :#{#customer.name}, cpf = :#{#customer.cpf}, phone = :#{#customer.phone}, email = :#{#customer.email} WHERE id = :id RETURNING *",
      nativeQuery = true)
  Customer updateCustomer(@Param("id") Long id, @Param("customer") Customer customer);

  @Query(value = "DELETE FROM customers WHERE id = :id", nativeQuery = true)
  @org.springframework.data.jpa.repository.Modifying
  void deleteCustomer(@Param("id") Long id);
}
