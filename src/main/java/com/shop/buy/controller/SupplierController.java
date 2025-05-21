package com.shop.buy.controller;

import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.dto.SupplierDTO;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
@RequestMapping("/api/suppliers")
@Tag(name = "Fornecedores", description = "Endpoints para gerenciamento de fornecedores")
public class SupplierController {

  private final SupplierService supplierService;

  public SupplierController(SupplierService supplierService) {
    this.supplierService = supplierService;
  }

  @Operation(
      summary = "Obter todos os fornecedores",
      description = "Retorna uma lista de todos os fornecedores registrados",
      tags = {"Fornecedores"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedores retornados com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SupplierDTO.class)))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping
  public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
    return ResponseEntity.ok(supplierService.getAllSuppliers());
  }

  @Operation(
      summary = "Obter fornecedor por ID",
      description = "Retorna um fornecedor específico pelo seu ID",
      tags = {"Fornecedores"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedor retornado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Fornecedor não encontrado",
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
  public ResponseEntity<SupplierDTO> getSupplierById(
      @Parameter(description = "ID do fornecedor a ser retornado", required = true) @PathVariable
          Long id) {
    return ResponseEntity.ok(supplierService.getSupplierById(id));
  }

  @Operation(
      summary = "Criar um novo fornecedor",
      description = "Cria um novo fornecedor",
      tags = {"Fornecedores"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Fornecedor criado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados de entrada inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Fornecedor com o mesmo CNPJ ou email já existe",
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
  public ResponseEntity<SupplierDTO> createSupplier(
      @Parameter(description = "Detalhes do fornecedor para criação", required = true)
          @Valid
          @RequestBody
          SupplierDTO supplierDTO) {
    return new ResponseEntity<>(supplierService.createSupplier(supplierDTO), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Atualizar um fornecedor existente",
      description = "Atualiza as informações de um fornecedor existente com base no ID fornecido",
      tags = {"Fornecedores"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedor atualizado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SupplierDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados de entrada inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Fornecedor não encontrado",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "CNPJ ou email do fornecedor conflita com um fornecedor existente",
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
  public ResponseEntity<SupplierDTO> updateSupplier(
      @Parameter(description = "ID do fornecedor a ser atualizado", required = true) @PathVariable
          Long id,
      @Parameter(description = "Informações atualizadas do fornecedor", required = true)
          @Valid
          @RequestBody
          SupplierDTO supplierDTO) {
    return ResponseEntity.ok(supplierService.updateSupplier(id, supplierDTO));
  }

  @Operation(
      summary = "Excluir um fornecedor",
      description = "Remove um fornecedor do sistema pelo seu ID",
      tags = {"Fornecedores"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Fornecedor excluído com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Fornecedor não encontrado",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Não é possível excluir o fornecedor porque está associado a produtos",
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
  public ResponseEntity<SuccessResponse> deleteSupplier(
      @Parameter(description = "ID do fornecedor a ser excluído", required = true) @PathVariable
          Long id) {
    supplierService.deleteSupplier(id);
    return ResponseEntity.ok(new SuccessResponse("Fornecedor excluído com sucesso"));
  }
}
