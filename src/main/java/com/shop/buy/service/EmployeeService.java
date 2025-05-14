package com.shop.buy.service;

import com.shop.buy.dto.EmployeeDTO;
import java.time.LocalDate;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    List<EmployeeDTO> getEmployeesByName(String name);
    List<EmployeeDTO> getEmployeesByRole(String role);
    EmployeeDTO getEmployeeByEmail(String email);
    List<EmployeeDTO> getEmployeesByHireDateRange(LocalDate startDate, LocalDate endDate);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
}
