package com.shop.buy.service.impl;

import com.shop.buy.dto.CustomerDTO;
import com.shop.buy.exception.ResourceAlreadyExistsException;
import com.shop.buy.exception.ResourceNotFoundException;
import com.shop.buy.model.Customer;
import com.shop.buy.repository.CustomerRepository;
import com.shop.buy.service.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return convertToDTO(customer);
    }

    @Override
    public CustomerDTO findByCpf(String cpf) {
        Customer customer = customerRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with CPF: " + cpf));
        return convertToDTO(customer);
    }

    @Override
    public CustomerDTO findByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
        return convertToDTO(customer);
    }

    @Override
    @Transactional
    public CustomerDTO create(CustomerDTO customerDTO) {
        if (customerRepository.existsByCpf(customerDTO.getCpf())) {
            throw new ResourceAlreadyExistsException("Customer already exists with CPF: " + customerDTO.getCpf());
        }
        
        if (customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Customer already exists with email: " + customerDTO.getEmail());
        }
        
        Customer customer = convertToEntity(customerDTO);
        customer = customerRepository.save(customer);
        return convertToDTO(customer);
    }

    @Override
    @Transactional
    public CustomerDTO update(Long id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        
        // Check if CPF is being changed and if the new CPF already exists
        if (!existingCustomer.getCpf().equals(customerDTO.getCpf()) &&
                customerRepository.existsByCpf(customerDTO.getCpf())) {
            throw new ResourceAlreadyExistsException("Customer already exists with CPF: " + customerDTO.getCpf());
        }
        
        // Check if email is being changed and if the new email already exists
        if (!existingCustomer.getEmail().equals(customerDTO.getEmail()) &&
                customerRepository.existsByEmail(customerDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Customer already exists with email: " + customerDTO.getEmail());
        }
        
        existingCustomer.setName(customerDTO.getName());
        existingCustomer.setCpf(customerDTO.getCpf());
        existingCustomer.setPhone(customerDTO.getPhone());
        existingCustomer.setEmail(customerDTO.getEmail());
        
        existingCustomer = customerRepository.save(existingCustomer);
        return convertToDTO(existingCustomer);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
    
    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    
    private Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
