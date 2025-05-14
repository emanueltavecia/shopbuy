package com.shop.buy.controller;

import com.shop.buy.dto.CategoryDTO;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.CategoryService;
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
@RequestMapping("/api/categories")
@Tag(name = "Categories", description = "Category management endpoints")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
        summary = "Get all categories",
        description = "Retrieves a list of all product categories in the system",
        tags = {"Categories"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved all categories",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @Operation(
        summary = "Get category by ID",
        description = "Retrieves a specific product category by its unique identifier",
        tags = {"Categories"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the category",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Category not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "ID of the category to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(
        summary = "Search categories by name",
        description = "Retrieves all categories that contain the specified name string",
        tags = {"Categories"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved matching categories",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByName(
            @Parameter(description = "Name or partial name to search for", required = true)
            @RequestParam String name) {
        return ResponseEntity.ok(categoryService.getCategoriesByName(name));
    }

    @Operation(
        summary = "Create a new category",
        description = "Creates a new product category in the system",
        tags = {"Categories"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Category successfully created",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Category already exists",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @Parameter(description = "Category details for creation", required = true)
            @Valid @RequestBody CategoryDTO categoryDTO) {
        return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing category",
        description = "Updates an existing category's information based on the given ID",
        tags = {"Categories"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Category successfully updated",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Category not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Category name already in use",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @Parameter(description = "ID of the category to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated category information", required = true)
            @Valid @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
    }

    @Operation(
        summary = "Delete a category",
        description = "Removes a product category from the system by its ID",
        tags = {"Categories"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Category successfully deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404", 
            description = "Category not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID of the category to delete", required = true)
            @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
