package com.shop.buy.controller;

import com.shop.buy.dto.ApiResponse;
import com.shop.buy.dto.BrandDTO;
import com.shop.buy.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@Tag(name = "Brand", description = "Brand management endpoints")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @Operation(summary = "Get all brands", description = "Retrieves a list of all brands")
    public ResponseEntity<ApiResponse<List<BrandDTO>>> getAllBrands() {
        List<BrandDTO> brands = brandService.findAll();
        return ResponseEntity.ok(ApiResponse.success(brands));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get brand by ID", description = "Retrieves a brand by its ID")
    public ResponseEntity<ApiResponse<BrandDTO>> getBrandById(@PathVariable Long id) {
        BrandDTO brand = brandService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(brand));
    }

    @PostMapping
    @Operation(summary = "Create a new brand", description = "Creates a new brand")
    public ResponseEntity<ApiResponse<BrandDTO>> createBrand(@Valid @RequestBody BrandDTO brandDTO) {
        BrandDTO createdBrand = brandService.create(brandDTO);
        return new ResponseEntity<>(ApiResponse.success("Brand created successfully", createdBrand), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a brand", description = "Updates an existing brand")
    public ResponseEntity<ApiResponse<BrandDTO>> updateBrand(@PathVariable Long id, @Valid @RequestBody BrandDTO brandDTO) {
        BrandDTO updatedBrand = brandService.update(id, brandDTO);
        return ResponseEntity.ok(ApiResponse.success("Brand updated successfully", updatedBrand));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a brand", description = "Deletes a brand by its ID")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable Long id) {
        brandService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Brand deleted successfully", null));
    }
}
