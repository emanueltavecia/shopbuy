package com.shop.buy.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    
    private Long id;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Sale date is required")
    @PastOrPresent(message = "Sale date cannot be in the future")
    private LocalDateTime saleDate;
    
    @NotNull(message = "Total value is required")
    @Positive(message = "Total value must be positive")
    private BigDecimal totalValue;
}
