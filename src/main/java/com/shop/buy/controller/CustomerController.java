package com.shop.buy.controller;

import com.shop.buy.dto.CustomerDTO;
import com.shop.buy.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<CustomerDTO>> getCustomersByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.getCustomersByName(name));
    }

    @GetMapping("/search/cpf")
    public ResponseEntity<CustomerDTO> getCustomerByCpf(@RequestParam String cpf) {
        return ResponseEntity.ok(customerService.getCustomerByCpf(cpf));
    }

    @GetMapping("/search/email")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @PathVariable Long id,
            @Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
