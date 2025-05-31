package com.shop.buy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleItemDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotNull(message = "ID da venda é obrigatório")
  private Long saleId;

  @NotNull(message = "Produto é obrigatório")
  private Long productId;

  @NotNull(message = "Quantidade é obrigatória")
  @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
  private Integer quantity;

  @NotNull(message = "Preço unitário é obrigatório")
  @Positive(message = "Preço unitário deve ser um valor positivo")
  private BigDecimal unitPrice;
}
