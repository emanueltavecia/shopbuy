package com.shop.buy.dto;

import com.shop.buy.model.Customer;
import com.shop.buy.model.Employee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Customer customer;

  @NotNull(message = "Cliente é obrigatório")
  private Long customerId;

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Employee employee;

  @NotNull(message = "Funcionário é obrigatório")
  private Long employeeId;

  @NotNull(message = "Data da venda é obrigatória")
  @PastOrPresent(message = "Data da venda não pode estar no futuro")
  private LocalDateTime saleDate;

  @Schema(
      accessMode = Schema.AccessMode.READ_ONLY,
      description = "Valor total calculado a partir dos itens da venda")
  private BigDecimal totalValue;

  @Positive(message = "Desconto deve ser um valor positivo")
  private BigDecimal discount;

  @Schema(description = "Método de pagamento (CREDIT_CARD, BANK_SLIP, PIX).")
  @NotNull(message = "Método de pagamento é obrigatório")
  private String paymentMethod;

  @NotNull(message = "Itens da venda são obrigatórios")
  @NotEmpty(message = "A venda deve conter pelo menos um item")
  @Valid
  @Schema(description = "Lista de itens da venda.")
  private List<NestedSaleItemDTO> items;
}
