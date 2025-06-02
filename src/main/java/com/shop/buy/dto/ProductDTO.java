package com.shop.buy.dto;

import com.shop.buy.model.Brand;
import com.shop.buy.model.Category;
import com.shop.buy.model.Supplier;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Long id;

  @NotBlank(message = "Nome do produto é obrigatório")
  private String name;

  private String size;

  private String color;

  @NotNull(message = "Preço do produto é obrigatório")
  @Positive(message = "Preço deve ser um valor positivo")
  private BigDecimal price;

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Category category;

  @NotNull(message = "Categoria é obrigatória")
  private Long categoryId;

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Brand brand;

  @NotNull(message = "Marca é obrigatória")
  private Long brandId;

  @Schema(accessMode = Schema.AccessMode.READ_ONLY)
  private Supplier supplier;

  @NotNull(message = "Fornecedor é obrigatório")
  private Long supplierId;
}
