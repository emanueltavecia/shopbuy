package com.shop.buy.repository;

import com.shop.buy.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = "SELECT * FROM suppliers", nativeQuery = true)
    List<Supplier> findAllSuppliers();
    
    @Query(value = "SELECT * FROM suppliers WHERE id = :id", nativeQuery = true)
    Optional<Supplier> findSupplierById(@Param("id") Long id);
    
    @Query(value = "INSERT INTO suppliers (name, cnpj, phone, email) VALUES (:#{#supplier.name}, :#{#supplier.cnpj}, :#{#supplier.phone}, :#{#supplier.email}) RETURNING *", nativeQuery = true)
    Supplier saveSupplier(@Param("supplier") Supplier supplier);
    
    @Query(value = "UPDATE suppliers SET name = :#{#supplier.name}, cnpj = :#{#supplier.cnpj}, phone = :#{#supplier.phone}, email = :#{#supplier.email} WHERE id = :id RETURNING *", nativeQuery = true)
    Supplier updateSupplier(@Param("id") Long id, @Param("supplier") Supplier supplier);
    
    @Query(value = "DELETE FROM suppliers WHERE id = :id", nativeQuery = true)
    void deleteSupplier(@Param("id") Long id);
}
