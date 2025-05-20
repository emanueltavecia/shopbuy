package com.shop.buy.controller;

import com.shop.buy.dto.ProductDTO;
import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.ProductService;
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
@RequestMapping("/api/products")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

        private final ProductService productService;

        public ProductController(ProductService productService) {
                this.productService = productService;
        }

        @Operation(summary = "Obter todos os produtos", description = "Retorna uma lista de todos os produtos registrados", tags = {
                        "Produtos" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produtos recuperados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping
        public ResponseEntity<List<ProductDTO>> getAllProducts() {
                return ResponseEntity.ok(productService.getAllProducts());
        }

        @Operation(summary = "Obter produto por ID", description = "Retorna um produto específico pelo seu ID", tags = {
                        "Produtos" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produto recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<ProductDTO> getProductById(
                        @Parameter(description = "ID do produto a ser recuperado", required = true) @PathVariable Long id) {
                return ResponseEntity.ok(productService.getProductById(id));
        }

        @Operation(summary = "Obter produtos por categoria", description = "Retorna todos os produtos pertencentes a uma categoria específica", tags = {
                        "Produtos" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produtos recuperados por categoria com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/search/category/{categoryId}")
        public ResponseEntity<List<ProductDTO>> getProductsByCategory(
                        @Parameter(description = "ID da categoria para filtrar produtos", required = true) @PathVariable Long categoryId) {
                return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
        }

        @Operation(summary = "Obter produtos por marca", description = "Retorna todos os produtos pertencentes a uma marca específica", tags = {
                        "Produtos" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produtos recuperados por marca com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Marca não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/search/brand/{brandId}")
        public ResponseEntity<List<ProductDTO>> getProductsByBrand(
                        @Parameter(description = "ID da marca para filtrar produtos", required = true) @PathVariable Long brandId) {
                return ResponseEntity.ok(productService.getProductsByBrand(brandId));
        }

        @Operation(summary = "Criar um novo produto", description = "Cria um novo produto", tags = {
                        "Produtos" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Produto criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Categoria ou marca referenciada não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "409", description = "Produto com o mesmo SKU já existe", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<ProductDTO> createProduct(
                        @Parameter(description = "Detalhes do produto para criação", required = true) @Valid @RequestBody ProductDTO productDTO) {
                return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
        }

        @Operation(summary = "Atualizar um produto existente", description = "Atualiza as informações de um produto existente com base no ID fornecido", tags = {
                        "Produtos" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado ou categoria/marca referenciada não encontrada", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "409", description = "SKU do produto conflita com um produto existente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<ProductDTO> updateProduct(
                        @Parameter(description = "ID do produto a ser atualizado", required = true) @PathVariable Long id,
                        @Parameter(description = "Informações atualizadas do produto", required = true) @Valid @RequestBody ProductDTO productDTO) {
                return ResponseEntity.ok(productService.updateProduct(id, productDTO));
        }

        @Operation(summary = "Excluir um produto", description = "Remove um produto do sistema pelo seu ID", tags = {
                        "Produtos" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "409", description = "Não é possível excluir o produto porque ele está referenciado em vendas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<SuccessResponse> deleteProduct(
                        @Parameter(description = "ID do produto a ser excluído", required = true) @PathVariable Long id) {
                productService.deleteProduct(id);
                return ResponseEntity.ok(new SuccessResponse("Produto excluído com sucesso"));
        }
}
