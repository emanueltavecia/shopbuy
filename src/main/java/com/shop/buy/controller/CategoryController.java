package com.shop.buy.controller;

import com.shop.buy.dto.CategoryDTO;
import com.shop.buy.dto.SuccessResponse;
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
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @Operation(
      summary = "Obter todas as categorias",
      description = "Retorna uma lista de todas as categorias registradas",
      tags = {"Categorias"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categorias retornadas com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    return ResponseEntity.ok(categoryService.getAllCategories());
  }

  @Operation(
      summary = "Obter categoria por ID",
      description = "Retorna uma categoria específica pelo seu ID",
      tags = {"Categorias"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria retornada com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(
      @Parameter(description = "ID da categoria a ser buscada", required = true) @PathVariable
          Long id) {
    return ResponseEntity.ok(categoryService.getCategoryById(id));
  }

  @Operation(
      summary = "Criar uma nova categoria",
      description = "Cria uma nova categoria",
      tags = {"Categorias"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Categoria criada com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados do body inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Categoria já existe",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping
  public ResponseEntity<CategoryDTO> createCategory(
      @Parameter(description = "Detalhes da categoria para criação", required = true)
          @Valid
          @RequestBody
          CategoryDTO categoryDTO) {
    return new ResponseEntity<>(categoryService.createCategory(categoryDTO), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Atualizar uma categoria existente",
      description = "Atualiza as informações de uma categoria existente com base no ID fornecido",
      tags = {"Categorias"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria atualizada com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoryDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados do body inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Nome da categoria já em uso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PutMapping("/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(
      @Parameter(description = "ID da categoria a ser atualizada", required = true) @PathVariable
          Long id,
      @Parameter(description = "Informações atualizadas da categoria", required = true)
          @Valid
          @RequestBody
          CategoryDTO categoryDTO) {
    return ResponseEntity.ok(categoryService.updateCategory(id, categoryDTO));
  }

  @Operation(
      summary = "Excluir uma categoria",
      description = "Exclui uma categoria de produto do sistema pelo seu ID",
      tags = {"Categorias"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Categoria excluída com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Categoria não encontrada",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<SuccessResponse> deleteCategory(
      @Parameter(description = "ID da categoria a ser excluída", required = true) @PathVariable
          Long id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.ok(new SuccessResponse("Categoria excluída com sucesso"));
  }
}
