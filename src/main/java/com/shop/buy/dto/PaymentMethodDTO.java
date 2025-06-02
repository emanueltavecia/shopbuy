package com.shop.buy.dto;

import com.shop.buy.model.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {

  @Schema(description = "Código do método de pagamento", example = "CREDIT_CARD")
  private String code;

  @Schema(description = "Descrição do método de pagamento", example = "Cartão de Crédito")
  private String description;

  public static PaymentMethodDTO fromEnum(PaymentMethod paymentMethod) {
    if (paymentMethod == null) {
      return null;
    }
    return new PaymentMethodDTO(paymentMethod.name(), paymentMethod.getDescription());
  }

  public static List<PaymentMethodDTO> getAllPaymentMethods() {
    return Arrays.stream(PaymentMethod.values()).map(PaymentMethodDTO::fromEnum).toList();
  }
}
