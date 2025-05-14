package com.shop.buy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    
    @NotBlank(message = "Product name cannot be blank")
    private String name;
    
    private String size;
    
    private String color;
    
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private BigDecimal price;
    
    @NotNull(message = "Category ID is required")
    private Long categoryId;
    
    @NotNull(message = "Brand ID is required")
    private Long brandId;
}
