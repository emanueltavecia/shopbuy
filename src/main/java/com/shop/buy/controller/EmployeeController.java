package com.shop.buy.controller;

import com.shop.buy.dto.EmployeeDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Employee management endpoints")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(
        summary = "Get all employees",
        description = "Retrieves a list of all employees in the system",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved all employees",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Operation(
        summary = "Get employee by ID",
        description = "Retrieves a specific employee by their unique identifier",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the employee",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Employee not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(
            @Parameter(description = "ID of the employee to retrieve", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(
        summary = "Search employees by name",
        description = "Retrieves all employees that contain the specified name string",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved matching employees",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/name")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByName(
            @Parameter(description = "Name or partial name to search for", required = true)
            @RequestParam String name) {
        return ResponseEntity.ok(employeeService.getEmployeesByName(name));
    }

    @Operation(
        summary = "Search employees by role",
        description = "Retrieves all employees with the specified role",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved employees by role",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/role")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByRole(
            @Parameter(description = "Role title to search for", required = true, 
                example = "Manager, Sales Associate, Cashier")
            @RequestParam String role) {
        return ResponseEntity.ok(employeeService.getEmployeesByRole(role));
    }

    @Operation(
        summary = "Get employee by email",
        description = "Retrieves an employee by their email address",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved the employee",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Employee with specified email not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/email")
    public ResponseEntity<EmployeeDTO> getEmployeeByEmail(
            @Parameter(description = "Email address to search for", required = true, example = "employee@clothingstore.com")
            @RequestParam String email) {
        return ResponseEntity.ok(employeeService.getEmployeeByEmail(email));
    }

    @Operation(
        summary = "Search employees by hire date range",
        description = "Retrieves all employees hired within the specified date range",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Successfully retrieved employees by hire date range",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid date range (e.g., startDate after endDate)",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/search/hire-date")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByHireDateRange(
            @Parameter(description = "Start date of the range (inclusive)", required = true, example = "2023-01-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date of the range (inclusive)", required = true, example = "2023-12-31")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(employeeService.getEmployeesByHireDateRange(startDate, endDate));
    }

    @Operation(
        summary = "Create a new employee",
        description = "Creates a new employee in the system",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Employee successfully created",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Employee with the same email already exists",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(
            @Parameter(description = "Employee details for creation", required = true)
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);
    }

    @Operation(
        summary = "Update an existing employee",
        description = "Updates an existing employee's information based on the given ID",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Employee successfully updated",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = EmployeeDTO.class))),
        @ApiResponse(
            responseCode = "400", 
            description = "Invalid input data",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "404", 
            description = "Employee not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Employee email conflicts with an existing employee",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @Parameter(description = "ID of the employee to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated employee information", required = true)
            @Valid @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @Operation(
        summary = "Delete an employee",
        description = "Removes an employee from the system by their ID",
        tags = {"Employees"})
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204", 
            description = "Employee successfully deleted",
            content = @Content),
        @ApiResponse(
            responseCode = "404", 
            description = "Employee not found",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "409", 
            description = "Cannot delete employee because they are associated with sales",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = "500", 
            description = "Internal server error",
            content = @Content(mediaType = "application/json", 
                schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @Parameter(description = "ID of the employee to delete", required = true)
            @PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
