package com.shop.buy.controller;

import com.shop.buy.dto.CustomerDTO;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Customer management endpoints")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(
        summary = "Get all customers",
        description = "Retrieves a list of all customers in the system",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved all customers",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @Operation(
        summary = "Get customer by ID",
        description = "Retrieves a specific customer by their unique identifier",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the customer",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Customer not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @Operation(
        summary = "Search customers by name",
        description = "Retrieves all customers that contain the specified name string",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved matching customers",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/name")
    public ResponseEntity<List<CustomerDTO>> getCustomersByName(
            @Parameter(description = "Name or partial name to search for", required = true)
            @RequestParam String name) {
        return ResponseEntity.ok(customerService.getCustomersByName(name));
    }

    @Operation(
        summary = "Get customer by CPF",
        description = "Retrieves a customer by their CPF document number",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the customer",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Customer with specified CPF not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/cpf")
    public ResponseEntity<CustomerDTO> getCustomerByCpf(
            @Parameter(description = "CPF document number to search for", required = true, example = "12345678900")
            @RequestParam String cpf) {
        return ResponseEntity.ok(customerService.getCustomerByCpf(cpf));
    }

    @Operation(
        summary = "Get customer by email",
        description = "Retrieves a customer by their email address",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the customer",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Customer with specified email not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/email")
    public ResponseEntity<CustomerDTO> getCustomerByEmail(
            @Parameter(description = "Email address to search for", required = true, example = "customer@example.com")
            @RequestParam String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @Operation(
        summary = "Create a new customer",
        description = "Creates a new customer in the system",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Customer successfully created",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Customer with the same CPF or email already exists",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(
            @Parameter(description = "Customer details for creation", required = true)
            @Valid @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing customer",
        description = "Updates an existing customer's information based on the given ID",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Customer successfully updated",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CustomerDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Customer not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Customer CPF or email conflicts with an existing customer",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @Parameter(description = "ID of the customer to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated customer information", required = true)
            @Valid @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
    }

    @Operation(
        summary = "Delete a customer",
        description = "Removes a customer from the system by their ID",
        tags = {"Customers"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Customer successfully deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404", 
            description = "Customer not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Cannot delete customer because they have associated sales",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete", required = true)
            @PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
