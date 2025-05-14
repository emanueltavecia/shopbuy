package com.shop.buy.controller;

import com.shop.buy.dto.SaleDTO;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Sales", description = "Sale management endpoints")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @Operation(
        summary = "Get all sales",
        description = "Retrieves a list of all sales in the system",
        tags = {"Sales"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved all sales",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = SaleDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    @Operation(
        summary = "Get sale by ID",
        description = "Retrieves a specific sale by its unique identifier",
        tags = {"Sales"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the sale",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = SaleDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Sale not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SaleDTO> getSaleById(
            @Parameter(description = "ID of the sale to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(saleService.getSaleById(id));
    }

    @Operation(
        summary = "Get sales by customer ID",
        description = "Retrieves all sales associated with a specific customer",
        tags = {"Sales"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved sales for the customer",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = SaleDTO.class))),
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
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SaleDTO>> getSalesByCustomerId(
            @Parameter(description = "ID of the customer to find sales for", required = true)
            @PathVariable Long customerId) {
        return ResponseEntity.ok(saleService.getSalesByCustomerId(customerId));
    }

    @Operation(
        summary = "Search sales by date range",
        description = "Retrieves all sales that occurred within the specified date range",
        tags = {"Sales"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved sales within date range",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = SaleDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid date range (e.g., startDate after endDate)",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/date")
    public ResponseEntity<List<SaleDTO>> getSalesByDateRange(
            @Parameter(description = "Start date and time (inclusive)", required = true, example = "2023-01-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "End date and time (inclusive)", required = true, example = "2023-12-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(saleService.getSalesByDateRange(startDate, endDate));
    }

    @Operation(
        summary = "Create a new sale",
        description = "Creates a new sale in the system with the associated sale items",
        tags = {"Sales"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Sale successfully created",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = SaleDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Customer, employee or products not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<SaleDTO> createSale(
            @Parameter(description = "Sale details for creation including sale items", required = true)
            @Valid @RequestBody SaleDTO saleDTO) {
        return new ResponseEntity<>(saleService.createSale(saleDTO), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing sale",
        description = "Updates an existing sale's information based on the given ID",
        tags = {"Sales"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Sale successfully updated",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = SaleDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Sale, customer, employee or products not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SaleDTO> updateSale(
            @Parameter(description = "ID of the sale to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated sale information", required = true)
            @Valid @RequestBody SaleDTO saleDTO) {
        return ResponseEntity.ok(saleService.updateSale(id, saleDTO));
    }

    @Operation(
        summary = "Delete a sale",
        description = "Removes a sale and its associated items from the system by its ID",
        tags = {"Sales"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Sale successfully deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404", 
            description = "Sale not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(
            @Parameter(description = "ID of the sale to delete", required = true)
            @PathVariable Long id) {
        saleService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
