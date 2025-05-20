package com.shop.buy.controller;

import com.shop.buy.dto.SaleDTO;
import com.shop.buy.dto.SuccessResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@Tag(name = "Vendas", description = "Endpoints para gerenciamento de vendas")
public class SaleController {

        private final SaleService saleService;

        public SaleController(SaleService saleService) {
                this.saleService = saleService;
        }

        @Operation(summary = "Obter todas as vendas", description = "Retorna uma lista de todas as vendas registradas", tags = {
                        "Vendas" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Vendas recuperadas com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping
        public ResponseEntity<List<SaleDTO>> getAllSales() {
                return ResponseEntity.ok(saleService.getAllSales());
        }

        @Operation(summary = "Obter venda por ID", description = "Retorna uma venda específica pelo seu identificador único", tags = {
                        "Vendas" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Venda recuperada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<SaleDTO> getSaleById(
                        @Parameter(description = "ID da venda a ser recuperada", required = true) @PathVariable Long id) {
                return ResponseEntity.ok(saleService.getSaleById(id));
        }

        @Operation(summary = "Obter vendas por ID do cliente", description = "Retorna todas as vendas associadas a um cliente específico", tags = {
                        "Vendas" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Vendas do cliente recuperadas com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/customer/{customerId}")
        public ResponseEntity<List<SaleDTO>> getSalesByCustomerId(
                        @Parameter(description = "ID do cliente para encontrar vendas", required = true) @PathVariable Long customerId) {
                return ResponseEntity.ok(saleService.getSalesByCustomerId(customerId));
        }

        @Operation(summary = "Criar uma nova venda", description = "Cria uma nova venda com os itens de venda associados", tags = {
                        "Vendas" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Venda criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Cliente, funcionário ou produtos não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<SaleDTO> createSale(
                        @Parameter(description = "Detalhes da venda para criação incluindo itens de venda", required = true) @Valid @RequestBody SaleDTO saleDTO) {
                return new ResponseEntity<>(saleService.createSale(saleDTO), HttpStatus.CREATED);
        }

        @Operation(summary = "Atualizar uma venda existente", description = "Atualiza as informações de uma venda existente com base no ID fornecido", tags = {
                        "Vendas" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Venda atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SaleDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Venda, cliente, funcionário ou produtos não encontrados", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<SaleDTO> updateSale(
                        @Parameter(description = "ID da venda a ser atualizada", required = true) @PathVariable Long id,
                        @Parameter(description = "Informações atualizadas da venda", required = true) @Valid @RequestBody SaleDTO saleDTO) {
                return ResponseEntity.ok(saleService.updateSale(id, saleDTO));
        }

        @Operation(summary = "Excluir uma venda", description = "Remove uma venda e seus itens associados do sistema pelo seu ID", tags = {
                        "Vendas" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Venda excluída com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Venda não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<SuccessResponse> deleteSale(
                        @Parameter(description = "ID da venda a ser excluída", required = true) @PathVariable Long id) {
                saleService.deleteSale(id);
                return ResponseEntity.ok(new SuccessResponse("Venda excluída com sucesso"));
        }
}
