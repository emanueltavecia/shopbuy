package com.shop.buy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
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
