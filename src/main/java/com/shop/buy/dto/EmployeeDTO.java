package com.shop.buy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotBlank(message = "Nome do funcionário é obrigatório")
  private String name;

  private String role;

  @Email(message = "Email deve ser válido")
  private String email;

  @PastOrPresent(message = "Data de contratação não pode estar no futuro")
  private LocalDate hireDate;
}
