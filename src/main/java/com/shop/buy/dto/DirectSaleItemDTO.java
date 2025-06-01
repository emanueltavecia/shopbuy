package com.shop.buy.dto;

import com.shop.buy.validation.ValidationGroups;
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
public class DirectSaleItemDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Schema(
      description = "ID da venda. Obrigatório apenas ao criar/editar um item diretamente.",
      accessMode = Schema.AccessMode.AUTO,
      requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(
      groups = ValidationGroups.DirectSaleItemOperation.class,
      message = "ID da venda é obrigatório ao criar/editar um item diretamente.")
  private Long saleId;

  @NotNull(message = "Produto é obrigatório")
  private Long productId;

  @NotNull(message = "Quantidade é obrigatória")
  @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
  @Positive(message = "Quantidade deve ser um valor positivo")
  private Integer quantity;

  @NotNull(message = "Preço unitário é obrigatório")
  @Positive(message = "Preço unitário deve ser um valor positivo")
  private BigDecimal unitPrice;
}
