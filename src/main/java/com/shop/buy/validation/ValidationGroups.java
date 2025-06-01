package com.shop.buy.validation;

/** Interface para definir grupos de validação. */
public interface ValidationGroups {
  /** Grupo de validação para quando um SaleItem é criado ou atualizado diretamente. */
  interface DirectSaleItemOperation {}

  /** Grupo de validação para quando um SaleItem é criado ou atualizado como parte de uma Sale. */
  interface NestedSaleItemOperation {}
}
