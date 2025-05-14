package com.shop.buy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    
    private Long id;
    
    @NotBlank(message = "Employee name cannot be blank")
    private String name;
    
    private String role;
    
    @Email(message = "Email should be valid")
    private String email;
    
    @PastOrPresent(message = "Hire date cannot be in the future")
    private LocalDate hireDate;
}
