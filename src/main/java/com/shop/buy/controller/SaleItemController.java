package com.shop.buy.controller;

import com.shop.buy.dto.SaleItemDTO;
import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.SaleItemService;
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
@RequestMapping("/api/sale-items")
@Tag(name = "Sale Items", description = "Sale item management endpoints")
public class SaleItemController {

    private final SaleItemService saleItemService;

    public SaleItemController(SaleItemService saleItemService) {
        this.saleItemService = saleItemService;
    }

    @Operation(summary = "Get all sale items", description = "Retrieves a list of all sale items in the system", tags = {
            "Sale Items" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all sale items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleItemDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<SaleItemDTO>> getAllSaleItems() {
        return ResponseEntity.ok(saleItemService.getAllSaleItems());
    }

    @Operation(summary = "Get sale item by ID", description = "Retrieves a specific sale item by its unique identifier", tags = {
            "Sale Items" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the sale item", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sale item not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<SaleItemDTO> getSaleItemById(
            @Parameter(description = "ID of the sale item to retrieve", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(saleItemService.getSaleItemById(id));
    }

    @Operation(summary = "Get items by sale ID", description = "Retrieves all items associated with a specific sale", tags = {
            "Sale Items" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sale items", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Sale not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<List<SaleItemDTO>> getSaleItemsBySaleId(
            @Parameter(description = "ID of the sale to retrieve items for", required = true) @PathVariable Long saleId) {
        return ResponseEntity.ok(saleItemService.getSaleItemsBySaleId(saleId));
    }

    @Operation(summary = "Get items by product ID", description = "Retrieves all sale items that contain a specific product", tags = {
            "Sale Items" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved sale items containing the product", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<SaleItemDTO>> getSaleItemsByProductId(
            @Parameter(description = "ID of the product to find in sale items", required = true) @PathVariable Long productId) {
        return ResponseEntity.ok(saleItemService.getSaleItemsByProductId(productId));
    }

    @Operation(summary = "Create a new sale item", description = "Creates a new sale item in the system", tags = {
            "Sale Items" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sale item successfully created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sale or product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<SaleItemDTO> createSaleItem(
            @Parameter(description = "Sale item details for creation", required = true) @Valid @RequestBody SaleItemDTO saleItemDTO) {
        return new ResponseEntity<>(saleItemService.createSaleItem(saleItemDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing sale item", description = "Updates an existing sale item's information based on the given ID", tags = {
            "Sale Items" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale item successfully updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sale item, sale or product not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<SaleItemDTO> updateSaleItem(
            @Parameter(description = "ID of the sale item to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated sale item information", required = true) @Valid @RequestBody SaleItemDTO saleItemDTO) {
        return ResponseEntity.ok(saleItemService.updateSaleItem(id, saleItemDTO));
    }

    @Operation(summary = "Delete a sale item", description = "Removes a sale item from the system by its ID", tags = {
            "Sale Items" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sale item successfully deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
            @ApiResponse(responseCode = "404", description = "Sale item not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteSaleItem(
            @Parameter(description = "ID of the sale item to delete", required = true) @PathVariable Long id) {
        saleItemService.deleteSaleItem(id);
        return ResponseEntity.ok(new SuccessResponse("Item de venda excluído com sucesso"));
    }
}
