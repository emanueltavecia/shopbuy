package com.shop.buy.controller;

import com.shop.buy.dto.CustomerDTO;
import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.CustomerService;
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
@RequestMapping("/api/customers")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class CustomerController {

        private final CustomerService customerService;

        public CustomerController(CustomerService customerService) {
                this.customerService = customerService;
        }

        @Operation(summary = "Obter todos os clientes", description = "Retorna uma lista de todos os clientes registrados", tags = {
                        "Clientes" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Clientes recuperados com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping
        public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
                return ResponseEntity.ok(customerService.getAllCustomers());
        }

        @Operation(summary = "Obter cliente por ID", description = "Retorna um cliente específico pelo seu ID", tags = {
                        "Clientes" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cliente recuperado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<CustomerDTO> getCustomerById(
                        @Parameter(description = "ID do cliente a ser recuperado", required = true) @PathVariable Long id) {
                return ResponseEntity.ok(customerService.getCustomerById(id));
        }

        @Operation(summary = "Criar um novo cliente", description = "Cria um novo cliente", tags = {
                        "Clientes" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "409", description = "Cliente com o mesmo CPF ou email já existe", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PostMapping
        public ResponseEntity<CustomerDTO> createCustomer(
                        @Parameter(description = "Detalhes do cliente para criação", required = true) @Valid @RequestBody CustomerDTO customerDTO) {
                return new ResponseEntity<>(customerService.createCustomer(customerDTO), HttpStatus.CREATED);
        }

        @Operation(summary = "Atualizar um cliente existente", description = "Atualiza as informações de um cliente existente com base no ID fornecido", tags = {
                        "Clientes" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "409", description = "CPF ou email do cliente conflita com um cliente existente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<CustomerDTO> updateCustomer(
                        @Parameter(description = "ID do cliente a ser atualizado", required = true) @PathVariable Long id,
                        @Parameter(description = "Informações atualizadas do cliente", required = true) @Valid @RequestBody CustomerDTO customerDTO) {
                return ResponseEntity.ok(customerService.updateCustomer(id, customerDTO));
        }

        @Operation(summary = "Excluir um cliente", description = "Remove um cliente do sistema pelo seu ID", tags = {
                        "Clientes" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
                        @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "409", description = "Não é possível excluir o cliente porque possui vendas associadas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                        @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<SuccessResponse> deleteCustomer(
                        @Parameter(description = "ID do cliente a ser excluído", required = true) @PathVariable Long id) {
                customerService.deleteCustomer(id);
                return ResponseEntity.ok(new SuccessResponse("Cliente excluído com sucesso"));
        }
}
