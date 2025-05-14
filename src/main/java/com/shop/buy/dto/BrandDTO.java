package com.shop.buy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    
    private Long id;
    
    @NotBlank(message = "Brand name cannot be blank")
    private String name;
    
    private String country;
    
    private String description;
}
