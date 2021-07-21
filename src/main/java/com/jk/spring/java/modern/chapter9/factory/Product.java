package com.jk.spring.java.modern.chapter9.factory;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Product {
  private String code;
  private String productName;
  private double portion;

  private Product(String code, String productName, double portion) {
    this.code = code;
    this.productName = productName;
    this.portion = portion;
  }

  public static Product of(String code, String productName, double portion) {
    return new Product(code, productName, portion);
  }
}
