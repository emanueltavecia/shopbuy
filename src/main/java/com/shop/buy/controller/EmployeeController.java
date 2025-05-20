package com.shop.buy.controller;

import com.shop.buy.dto.EmployeeDTO;
import com.shop.buy.dto.SuccessResponse;
import com.shop.buy.exception.ErrorResponse;
import com.shop.buy.service.EmployeeService;
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
@RequestMapping("/api/employees")
@Tag(name = "Funcionários", description = "Endpoints para gerenciamento de funcionários")
public class EmployeeController {

  private final EmployeeService employeeService;

  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @Operation(
      summary = "Obter todos os funcionários",
      description = "Retorna uma lista de todos os funcionários registrados",
      tags = {"Funcionários"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Funcionários recuperados com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Erro interno do servidor",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping
  public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
    return ResponseEntity.ok(employeeService.getAllEmployees());
  }

  @Operation(
      summary = "Obter funcionário por ID",
      description = "Retorna um funcionário específico pelo seu ID",
      tags = {"Funcionários"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Funcionário recuperado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Funcionário não encontrado",
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
  public ResponseEntity<EmployeeDTO> getEmployeeById(
      @Parameter(description = "ID do funcionário a ser recuperado", required = true) @PathVariable
          Long id) {
    return ResponseEntity.ok(employeeService.getEmployeeById(id));
  }

  @Operation(
      summary = "Criar um novo funcionário",
      description = "Cria um novo funcionário",
      tags = {"Funcionários"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Funcionário criado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados de entrada inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Funcionário com o mesmo email já existe",
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
  public ResponseEntity<EmployeeDTO> createEmployee(
      @Parameter(description = "Detalhes do funcionário para criação", required = true)
          @Valid
          @RequestBody
          EmployeeDTO employeeDTO) {
    return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);
  }

  @Operation(
      summary = "Atualizar um funcionário existente",
      description = "Atualiza as informações de um funcionário existente com base no ID fornecido",
      tags = {"Funcionários"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Funcionário atualizado com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados de entrada inválidos",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Funcionário não encontrado",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Email do funcionário conflita com um funcionário existente",
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
  public ResponseEntity<EmployeeDTO> updateEmployee(
      @Parameter(description = "ID do funcionário a ser atualizado", required = true) @PathVariable
          Long id,
      @Parameter(description = "Informações atualizadas do funcionário", required = true)
          @Valid
          @RequestBody
          EmployeeDTO employeeDTO) {
    return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
  }

  @Operation(
      summary = "Excluir um funcionário",
      description = "Remove um funcionário do sistema pelo seu ID",
      tags = {"Funcionários"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Funcionário excluído com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = SuccessResponse.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Funcionário não encontrado",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409",
            description = "Não é possível excluir o funcionário porque está associado a vendas",
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
  public ResponseEntity<SuccessResponse> deleteEmployee(
      @Parameter(description = "ID do funcionário a ser excluído", required = true) @PathVariable
          Long id) {
    employeeService.deleteEmployee(id);
    return ResponseEntity.ok(new SuccessResponse("Funcionário excluído com sucesso"));
  }
}
