package com.shop.buy.controller;

import com.shop.buy.dto.SupplierDTO;
import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@Tag(name = "Suppliers", description = "Supplier management endpoints")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @Operation(summary = "Get all suppliers", description = "Retrieves a list of all suppliers in the system", tags = {
            "Suppliers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all suppliers", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SupplierDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @Operation(summary = "Get supplier by ID", description = "Retrieves a specific supplier by its unique identifier", tags = {
            "Suppliers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the supplier", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SupplierDTO.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(
            @Parameter(description = "ID of the supplier to retrieve", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @Operation(summary = "Create a new supplier", description = "Creates a new supplier in the system", tags = {
            "Suppliers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Supplier successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SupplierDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Supplier with the same CNPJ or email already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(
            @Parameter(description = "Supplier details for creation", required = true) @Valid @RequestBody SupplierDTO supplierDTO) {
        return new ResponseEntity<>(supplierService.createSupplier(supplierDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing supplier", description = "Updates an existing supplier's information based on the given ID", tags = {
            "Suppliers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SupplierDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Supplier CNPJ or email conflicts with an existing supplier", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(
            @Parameter(description = "ID of the supplier to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated supplier information", required = true) @Valid @RequestBody SupplierDTO supplierDTO) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
    }

    @Operation(summary = "Delete a supplier", description = "Removes a supplier from the system by its ID", tags = {
            "Suppliers" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Supplier successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Supplier not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Cannot delete supplier because it is associated with products", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteSupplier(
            @Parameter(description = "ID of the supplier to delete", required = true) @PathVariable Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok(new SuccessResponse("Produto excluído com sucesso"));
    }
}
