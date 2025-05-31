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

  @NotNull(message = "Cliente é obrigatório")
  private Long customerId;

  @NotNull(message = "Funcionário é obrigatório")
  private Long employeeId;

  @NotNull(message = "Data da venda é obrigatória")
  @PastOrPresent(message = "Data da venda não pode estar no futuro")
  private LocalDateTime saleDate;

  @NotNull(message = "Valor total é obrigatório")
  @Positive(message = "Valor total deve ser um valor positivo")
  private BigDecimal totalValue;
}
