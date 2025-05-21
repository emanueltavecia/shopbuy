package com.shop.buy.controller;

import com.shop.buy.dto.BrandDTO;
import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.BrandService;
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
@RequestMapping("/api/brands")
@Tag(name = "Marcas", description = "Endpoints para gerenciamento de marcas")
public class BrandController {

  private final BrandService brandService;

  public BrandController(BrandService brandService) {
    this.brandService = brandService;
  }

  @Operation(
      summary = "Obter todas as marcas",
      description = "Retorna uma lista de todas as marcas de roupas registradas",
      tags = {"Marcas"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Marcas retornadas com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = BrandDTO.class)))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping
  public ResponseEntity<List<BrandDTO>> getAllBrands() {
    return ResponseEntity.ok(brandService.getAllBrands());
  }

  @Operation(
      summary = "Obter marca por ID",
      description = "Retorna uma marca específica pelo seu ID",
      tags = {"Marcas"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Marca retornada com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Marca não encontrada",
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
  public ResponseEntity<BrandDTO> getBrandById(
      @Parameter(description = "ID da marca a ser buscada", required = true) @PathVariable
          Long id) {
    return ResponseEntity.ok(brandService.getBrandById(id));
  }

  @Operation(
      summary = "Criar uma nova marca",
      description = "Cria uma nova marca de roupa",
      tags = {"Marcas"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Marca criada com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados do body inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Marca já existe",
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
  public ResponseEntity<BrandDTO> createBrand(
      @Parameter(description = "Detalhes da marca para criação", required = true)
          @Valid
          @RequestBody
          BrandDTO brandDTO) {
    return new ResponseEntity<>(brandService.createBrand(brandDTO), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Atualizar uma marca existente",
      description = "Atualiza as informações de uma marca existente com base no ID fornecido",
      tags = {"Marcas"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Marca atualizada com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = BrandDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados do body inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Marca não encontrada",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Nome da marca já em uso",
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
  public ResponseEntity<BrandDTO> updateBrand(
      @Parameter(description = "ID da marca a ser atualizada", required = true) @PathVariable
          Long id,
      @Parameter(description = "Informações atualizadas da marca", required = true)
          @Valid
          @RequestBody
          BrandDTO brandDTO) {
    return ResponseEntity.ok(brandService.updateBrand(id, brandDTO));
  }

  @Operation(
      summary = "Excluir uma marca",
      description = "Exclui uma marca do sistema pelo seu ID",
      tags = {"Marcas"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Marca excluída com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Marca não encontrada",
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
  public ResponseEntity<SuccessResponse> deleteBrand(
      @Parameter(description = "ID da marca a ser excluída", required = true) @PathVariable
          Long id) {
    brandService.deleteBrand(id);
    return ResponseEntity.ok(new SuccessResponse("Marca excluída com sucesso"));
  }
}
