package com.shop.buy.controller;

import com.shop.buy.dto.ApiResponse;
import com.shop.buy.dto.EmployeeDTO;
import com.shop.buy.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee", description = "Employee management endpoints")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieves a list of all employees")
    public ResponseEntity<ApiResponse<List<EmployeeDTO>>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.findAll();
        return ResponseEntity.ok(ApiResponse.success(employees));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by ID", description = "Retrieves an employee by its ID")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeById(@PathVariable Long id) {
        EmployeeDTO employee = employeeService.findById(id);
        return ResponseEntity.ok(ApiResponse.success(employee));
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get employee by email", description = "Retrieves an employee by email")
    public ResponseEntity<ApiResponse<EmployeeDTO>> getEmployeeByEmail(@PathVariable String email) {
        EmployeeDTO employee = employeeService.findByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(employee));
    }

    @PostMapping
    @Operation(summary = "Create a new employee", description = "Creates a new employee")
    public ResponseEntity<ApiResponse<EmployeeDTO>> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO createdEmployee = employeeService.create(employeeDTO);
        return new ResponseEntity<>(ApiResponse.success("Employee created successfully", createdEmployee), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an employee", description = "Updates an existing employee")
    public ResponseEntity<ApiResponse<EmployeeDTO>> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.update(id, employeeDTO);
        return ResponseEntity.ok(ApiResponse.success("Employee updated successfully", updatedEmployee));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an employee", description = "Deletes an employee by its ID")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Employee deleted successfully", null));
    }
}
