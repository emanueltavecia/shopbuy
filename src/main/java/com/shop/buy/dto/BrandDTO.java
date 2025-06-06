package com.shop.buy.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotBlank(message = "Nome da marca é obrigatório")
  private String name;

  private String country;

  private String description;
}
