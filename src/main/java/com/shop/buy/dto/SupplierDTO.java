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
public class SupplierDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotBlank(message = "Nome do fornecedor é obrigatório")
  private String name;

  @NotBlank(message = "CNPJ do fornecedor é obrigatório")
  @Pattern(
      regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}\\-\\d{2}$",
      message = "CNPJ deve estar no formato XX.XXX.XXX/XXXX-XX")
  private String cnpj;

  private String phone;

  @Email(message = "Email deve ser válido")
  private String email;
}
