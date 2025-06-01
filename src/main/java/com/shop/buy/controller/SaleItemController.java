package com.shop.buy.controller;

import com.shop.buy.dto.DirectSaleItemDTO;
import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.SaleItemService;
import com.shop.buy.validation.ValidationGroups;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sale-items")
@Tag(name = "Itens de Venda", description = "Endpoints para gerenciamento de itens de venda")
@Validated
public class SaleItemController {

        private final SaleItemService saleItemService;

        public SaleItemController(SaleItemService saleItemService) {
                this.saleItemService = saleItemService;
        }

        @Operation(summary = "Obter todos os itens de venda", description = "Retorna uma lista de todos os itens de venda registrados", tags = {
                        "Itens de Venda" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Itens de venda retornados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DirectSaleItemDTO.class)))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping
        public ResponseEntity<List<DirectSaleItemDTO>> getAllSaleItems() {
                return ResponseEntity.ok(saleItemService.getAllSaleItems());
        }

        @Operation(summary = "Obter item de venda por ID", description = "Retorna um item de venda específico pelo seu ID", tags = {
                        "Itens de Venda" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Item de venda retornado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DirectSaleItemDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Item de venda não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<DirectSaleItemDTO> getSaleItemById(
                        @Parameter(description = "ID do item de venda a ser retornado", required = true) @PathVariable Long id) {
                return ResponseEntity.ok(saleItemService.getSaleItemById(id));
        }

        @Operation(summary = "Obter itens por ID da venda", description = "Retorna todos os itens associados a uma venda específica", tags = {
                        "Itens de Venda" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Itens de venda retornados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DirectSaleItemDTO.class)))),
                        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/sale/{saleId}")
        public ResponseEntity<List<DirectSaleItemDTO>> getSaleItemsBySaleId(
                        @Parameter(description = "ID da venda para retornar itens", required = true) @PathVariable Long saleId) {
                return ResponseEntity.ok(saleItemService.getSaleItemsBySaleId(saleId));
        }

        @Operation(summary = "Obter itens por ID do produto", description = "Retorna todos os itens de venda que contêm um produto específico", tags = {
                        "Itens de Venda" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Itens de venda contendo o produto retornados com sucesso", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DirectSaleItemDTO.class)))),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/product/{productId}")
        public ResponseEntity<List<DirectSaleItemDTO>> getSaleItemsByProductId(
                        @Parameter(description = "ID do produto para encontrar nos itens de venda", required = true) @PathVariable Long productId) {
                return ResponseEntity.ok(saleItemService.getSaleItemsByProductId(productId));
        }

        @Operation(summary = "Criar um novo item de venda", description = "Cria um novo item de venda. Nota: O campo saleId é obrigatório quando criar o item diretamente por esta API.", tags = {
                        "Itens de Venda" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Item de venda criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DirectSaleItemDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Venda ou produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<DirectSaleItemDTO> createSaleItem(
                        @Parameter(description = "Detalhes do item de venda para criação", required = true) @Validated(ValidationGroups.DirectSaleItemOperation.class) @Valid @RequestBody DirectSaleItemDTO saleItemDTO) {
                return new ResponseEntity<>(saleItemService.createSaleItem(saleItemDTO), HttpStatus.CREATED);
        }

        @Operation(summary = "Atualizar um item de venda existente", description = "Atualiza as informações de um item de venda existente com base no ID fornecido", tags = {
                        "Itens de Venda" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Item de venda atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DirectSaleItemDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Item de venda, venda ou produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<DirectSaleItemDTO> updateSaleItem(
                        @Parameter(description = "ID do item de venda a ser atualizado", required = true) @PathVariable Long id,
                        @Parameter(description = "Informações atualizadas do item de venda", required = true) @Validated(ValidationGroups.DirectSaleItemOperation.class) @Valid @RequestBody DirectSaleItemDTO saleItemDTO) {
                return ResponseEntity.ok(saleItemService.updateSaleItem(id, saleItemDTO));
        }

        @Operation(summary = "Excluir um item de venda", description = "Remove um item de venda do sistema pelo seu ID", tags = {
                        "Itens de Venda" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Item de venda excluído com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Item de venda não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<SuccessResponse> deleteSaleItem(
                        @Parameter(description = "ID do item de venda a ser excluído", required = true) @PathVariable Long id) {
                saleItemService.deleteSaleItem(id);
                return ResponseEntity.ok(new SuccessResponse("Item de venda excluído com sucesso"));
        }
}
