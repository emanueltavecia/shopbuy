package com.shop.buy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    
    private Long id;
    
    @NotBlank(message = "Customer name cannot be blank")
    private String name;
    
    @NotBlank(message = "CPF cannot be blank")
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "CPF must be in the format XXX.XXX.XXX-XX")
    private String cpf;
    
    private String phone;
    
    @Email(message = "Email should be valid")
    private String email;
}
