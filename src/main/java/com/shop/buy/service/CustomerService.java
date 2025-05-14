package com.shop.buy.service;

import com.shop.buy.dto.CustomerDTO;
import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
    List<CustomerDTO> getCustomersByName(String name);
    CustomerDTO getCustomerByCpf(String cpf);
    CustomerDTO getCustomerByEmail(String email);
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
    void deleteCustomer(Long id);
}
