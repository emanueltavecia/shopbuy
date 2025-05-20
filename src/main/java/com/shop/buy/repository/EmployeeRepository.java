package com.shop.buy.repository;

import com.shop.buy.model.Employee;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  @Query(value = "SELECT * FROM employees", nativeQuery = true)
  List<Employee> findAllEmployees();

  @Query(value = "SELECT * FROM employees WHERE id = :id", nativeQuery = true)
  Optional<Employee> findEmployeeById(@Param("id") Long id);

  @Query(
      value =
          "INSERT INTO employees (name, role, email, hire_date) VALUES (:#{#employee.name}, :#{#employee.role}, :#{#employee.email}, :#{#employee.hireDate}) RETURNING *",
      nativeQuery = true)
  Employee saveEmployee(@Param("employee") Employee employee);

  @Query(
      value =
          "UPDATE employees SET name = :#{#employee.name}, role = :#{#employee.role}, email = :#{#employee.email}, hire_date = :#{#employee.hireDate} WHERE id = :id RETURNING *",
      nativeQuery = true)
  Employee updateEmployee(@Param("id") Long id, @Param("employee") Employee employee);

  @Query(value = "DELETE FROM employees WHERE id = :id", nativeQuery = true)
  @org.springframework.data.jpa.repository.Modifying
  void deleteEmployee(@Param("id") Long id);
}
