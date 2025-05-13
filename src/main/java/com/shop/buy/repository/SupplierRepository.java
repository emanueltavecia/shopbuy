package com.shop.buy.repository;

import com.shop.buy.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
    Optional<Supplier> findByCnpj(String cnpj);
    Optional<Supplier> findByEmail(String email);
}
