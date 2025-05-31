package com.shop.buy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotBlank(message = "Nome do cliente é obrigatório")
  private String name;

  @NotBlank(message = "CPF do cliente é obrigatório")
  @Pattern(
      regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$",
      message = "CPF deve estar no formato XXX.XXX.XXX-XX")
  private String cpf;

  private String phone;

  @Email(message = "Email deve ser válido")
  private String email;
}
