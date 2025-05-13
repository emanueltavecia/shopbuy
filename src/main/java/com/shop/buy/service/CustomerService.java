package com.shop.buy.service;

import com.shop.buy.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();
    CustomerDTO findById(Long id);
    CustomerDTO findByCpf(String cpf);
    CustomerDTO findByEmail(String email);
    CustomerDTO create(CustomerDTO customerDTO);
    CustomerDTO update(Long id, CustomerDTO customerDTO);
    void delete(Long id);
}
