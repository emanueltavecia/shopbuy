package com.shop.buy.service.impl;

import com.shop.buy.dto.EmployeeDTO;
import com.shop.buy.exception.ResourceAlreadyExistsException;
import com.shop.buy.exception.ResourceNotFoundException;
import com.shop.buy.model.Employee;
import com.shop.buy.repository.EmployeeRepository;
import com.shop.buy.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<EmployeeDTO> findAll() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDTO findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        return convertToDTO(employee);
    }

    @Override
    public EmployeeDTO findByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with email: " + email));
        return convertToDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO create(EmployeeDTO employeeDTO) {
        if (employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Employee already exists with email: " + employeeDTO.getEmail());
        }
        
        Employee employee = convertToEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return convertToDTO(employee);
    }

    @Override
    @Transactional
    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
        
        // Check if email is being changed and if the new email already exists
        if (!existingEmployee.getEmail().equals(employeeDTO.getEmail()) &&
                employeeRepository.existsByEmail(employeeDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Employee already exists with email: " + employeeDTO.getEmail());
        }
        
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setRole(employeeDTO.getRole());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setHireDate(employeeDTO.getHireDate());
        
        existingEmployee = employeeRepository.save(existingEmployee);
        return convertToDTO(existingEmployee);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeRepository.deleteById(id);
    }
    
    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }
    
    private Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}
