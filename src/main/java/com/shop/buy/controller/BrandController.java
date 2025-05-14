package com.shop.buy.controller;

import com.shop.buy.dto.BrandDTO;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.BrandService;
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
@RequestMapping("/api/brands")
@Tag(name = "Brands", description = "Brand management endpoints")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @Operation(
        summary = "Get all brands",
        description = "Retrieves a list of all registered clothing brands in the system",
        tags = {"Brands"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved all brands",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @Operation(
        summary = "Get brand by ID",
        description = "Retrieves a specific brand by its unique identifier",
        tags = {"Brands"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the brand",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Brand not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BrandDTO> getBrandById(
            @Parameter(description = "ID of the brand to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(brandService.getBrandById(id));
    }

    @Operation(
        summary = "Search brands by name",
        description = "Retrieves all brands that contain the specified name string",
        tags = {"Brands"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved matching brands",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/name")
    public ResponseEntity<List<BrandDTO>> getBrandsByName(
            @Parameter(description = "Name or partial name to search for", required = true)
            @RequestParam String name) {
        return ResponseEntity.ok(brandService.getBrandsByName(name));
    }

    @Operation(
        summary = "Search brands by country",
        description = "Retrieves all brands that are from the specified country",
        tags = {"Brands"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved brands from the specified country",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/country")
    public ResponseEntity<List<BrandDTO>> getBrandsByCountry(
            @Parameter(description = "Country name to search for", required = true)
            @RequestParam String country) {
        return ResponseEntity.ok(brandService.getBrandsByCountry(country));
    }

    @Operation(
        summary = "Create a new brand",
        description = "Creates a new clothing brand in the system",
        tags = {"Brands"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Brand successfully created",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Brand already exists",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(
            @Parameter(description = "Brand details for creation", required = true)
            @Valid @RequestBody BrandDTO brandDTO) {
        return new ResponseEntity<>(brandService.createBrand(brandDTO), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing brand",
        description = "Updates an existing brand's information based on the given ID",
        tags = {"Brands"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Brand successfully updated",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Brand not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Brand name already in use",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<BrandDTO> updateBrand(
            @Parameter(description = "ID of the brand to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated brand information", required = true)
            @Valid @RequestBody BrandDTO brandDTO) {
        return ResponseEntity.ok(brandService.updateBrand(id, brandDTO));
    }

    @Operation(
        summary = "Delete a brand",
        description = "Removes a brand from the system by its ID",
        tags = {"Brands"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Brand successfully deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404", 
            description = "Brand not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(
            @Parameter(description = "ID of the brand to delete", required = true)
            @PathVariable Long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.noContent().build();
    }
}
