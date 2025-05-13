package com.shop.buy.controller;

import com.shop.buy.dto.ApiResponse;
import com.shop.buy.dto.SaleDTO;
import com.shop.buy.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sale", description = "Sale management endpoints")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    @Operation(summary = "Get all sales", description = "Retrieves a list of all sales")
    public ResponseEntity<ApiResponse<List<SaleDTO>>> getAllSales() {
        List<SaleDTO> sales = saleService.findAll();
        return ResponseEntity.ok(ApiResponse.success(sales));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sale by ID", description = "Retrieves a sale by its ID")
    public ResponseEntity<ApiResponse<SaleDTO>> getSaleById(@PathVariable Long id) {
        SaleDTO sale = saleService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(sale));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get sales by customer", description = "Retrieves all sales for a specific customer")
    public ResponseEntity<ApiResponse<List<SaleDTO>>> getSalesByCustomer(@PathVariable Long customerId) {
        List<SaleDTO> sales = saleService.findByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success(sales));
    }

    @GetMapping("/date-range")
    @Operation(summary = "Get sales by date range", description = "Retrieves all sales within a specific date range")
    public ResponseEntity<ApiResponse<List<SaleDTO>>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<SaleDTO> sales = saleService.findByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(sales));
    }

    @PostMapping
    @Operation(summary = "Create a new sale", description = "Creates a new sale with items")
    public ResponseEntity<ApiResponse<SaleDTO>> createSale(@Valid @RequestBody SaleDTO saleDTO) {
        SaleDTO createdSale = saleService.create(saleDTO);
        return new ResponseEntity<>(ApiResponse.success("Sale created successfully", createdSale), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a sale", description = "Updates an existing sale")
    public ResponseEntity<ApiResponse<SaleDTO>> updateSale(@PathVariable Long id, @Valid @RequestBody SaleDTO saleDTO) {
        SaleDTO updatedSale = saleService.update(id, saleDTO);
        return ResponseEntity.ok(ApiResponse.success("Sale updated successfully", updatedSale));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sale", description = "Deletes a sale by its ID")
    public ResponseEntity<ApiResponse<Void>> deleteSale(@PathVariable Long id) {
        saleService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Sale deleted successfully", null));
    }
}
