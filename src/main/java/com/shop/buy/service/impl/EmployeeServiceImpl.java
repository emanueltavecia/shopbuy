package com.shop.buy.service.impl;

import com.shop.buy.dto.EmployeeDTO;
import com.shop.buy.model.Employee;
import com.shop.buy.repository.EmployeeRepository;
import com.shop.buy.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAllEmployees().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee employee = employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = convertToEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.saveEmployee(employee);
        return convertToDTO(savedEmployee);
    }

    @Override
    @Transactional
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        // Verify employee exists
        employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        
        Employee employee = convertToEntity(employeeDTO);
        Employee updatedEmployee = employeeRepository.updateEmployee(id, employee);
        return convertToDTO(updatedEmployee);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        // Verify employee exists
        employeeRepository.findEmployeeById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id));
        
        employeeRepository.deleteEmployee(id);
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setRole(employee.getRole());
        dto.setEmail(employee.getEmail());
        dto.setHireDate(employee.getHireDate());
        return dto;
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setName(dto.getName());
        employee.setRole(dto.getRole());
        employee.setEmail(dto.getEmail());
        employee.setHireDate(dto.getHireDate());
        return employee;
    }
}
