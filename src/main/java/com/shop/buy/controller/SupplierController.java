package com.shop.buy.controller;

import com.shop.buy.dto.ApiResponse;
import com.shop.buy.dto.SupplierDTO;
import com.shop.buy.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Supplier", description = "Supplier management endpoints")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    @Operation(summary = "Get all suppliers", description = "Retrieves a list of all suppliers")
    public ResponseEntity<ApiResponse<List<SupplierDTO>>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.findAll();
        return ResponseEntity.ok(ApiResponse.success(suppliers));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get supplier by ID", description = "Retrieves a supplier by its ID")
    public ResponseEntity<ApiResponse<SupplierDTO>> getSupplierById(@PathVariable Long id) {
        SupplierDTO supplier = supplierService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(supplier));
    }

    @GetMapping("/cnpj/{cnpj}")
    @Operation(summary = "Get supplier by CNPJ", description = "Retrieves a supplier by CNPJ")
    public ResponseEntity<ApiResponse<SupplierDTO>> getSupplierByCnpj(@PathVariable String cnpj) {
        SupplierDTO supplier = supplierService.findByCnpj(cnpj);
        return ResponseEntity.ok(ApiResponse.success(supplier));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get supplier by email", description = "Retrieves a supplier by email")
    public ResponseEntity<ApiResponse<SupplierDTO>> getSupplierByEmail(@PathVariable String email) {
        SupplierDTO supplier = supplierService.findByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(supplier));
    }

    @PostMapping
    @Operation(summary = "Create a new supplier", description = "Creates a new supplier")
    public ResponseEntity<ApiResponse<SupplierDTO>> createSupplier(@Valid @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO createdSupplier = supplierService.create(supplierDTO);
        return new ResponseEntity<>(ApiResponse.success("Supplier created successfully", createdSupplier), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a supplier", description = "Updates an existing supplier")
    public ResponseEntity<ApiResponse<SupplierDTO>> updateSupplier(@PathVariable Long id, @Valid @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO updatedSupplier = supplierService.update(id, supplierDTO);
        return ResponseEntity.ok(ApiResponse.success("Supplier updated successfully", updatedSupplier));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a supplier", description = "Deletes a supplier by its ID")
    public ResponseEntity<ApiResponse<Void>> deleteSupplier(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Supplier deleted successfully", null));
    }
}
