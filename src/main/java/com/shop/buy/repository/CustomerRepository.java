package com.shop.buy.repository;

import com.shop.buy.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Customer> findByCpf(String cpf);
    Optional<Customer> findByEmail(String email);
}
