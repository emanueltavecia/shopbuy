package com.shop.buy.model;

public enum PaymentMethod {
  CREDIT_CARD("Cartão de Crédito"),
  BANK_SLIP("Boleto"),
  PIX("PIX");

  private final String description;

  PaymentMethod(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
