package com.shop.buy.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    
    private Long id;
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    private LocalDateTime saleDate;
    
    private BigDecimal totalValue;
    
    @NotEmpty(message = "Sale must have at least one item")
    @Valid
    private List<SaleItemDTO> items = new ArrayList<>();
}
