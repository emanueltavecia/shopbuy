package com.shop.buy.service.impl;

import com.shop.buy.dto.CustomerDTO;
import com.shop.buy.model.Customer;
import com.shop.buy.repository.CustomerRepository;
import com.shop.buy.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAllCustomers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findCustomerById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }



    @Override
    @Transactional
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = convertToEntity(customerDTO);
        Customer savedCustomer = customerRepository.saveCustomer(customer);
        return convertToDTO(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
        // Verify customer exists
        customerRepository.findCustomerById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        
        Customer customer = convertToEntity(customerDTO);
        Customer updatedCustomer = customerRepository.updateCustomer(id, customer);
        return convertToDTO(updatedCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        // Verify customer exists
        customerRepository.findCustomerById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        
        customerRepository.deleteCustomer(id);
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setCpf(customer.getCpf());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        return dto;
    }

    private Customer convertToEntity(CustomerDTO dto) {
        Customer customer = new Customer();
        customer.setId(dto.getId());
        customer.setName(dto.getName());
        customer.setCpf(dto.getCpf());
        customer.setPhone(dto.getPhone());
        customer.setEmail(dto.getEmail());
        return customer;
    }
}
