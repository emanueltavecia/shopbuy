package com.shop.buy.controller;

import com.shop.buy.dto.PaymentMethodDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-methods")
@Tag(
    name = "Métodos de Pagamento",
    description = "Endpoints para obter métodos de pagamento disponíveis")
public class PaymentMethodController {

  @Operation(
      summary = "Obter todos os métodos de pagamento",
      description = "Retorna uma lista de todos os métodos de pagamento disponíveis",
      tags = {"Métodos de Pagamento"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Métodos de pagamento retornados com sucesso",
            content =
                @Content(
                    mediaType = "application/json",
                    array =
                        @ArraySchema(schema = @Schema(implementation = PaymentMethodDTO.class))))
      })
  @GetMapping
  public ResponseEntity<List<PaymentMethodDTO>> getAllPaymentMethods() {
    return ResponseEntity.ok(PaymentMethodDTO.getAllPaymentMethods());
  }
}
