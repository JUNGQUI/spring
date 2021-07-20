package com.jk.spring.java.modern.chapter9.factory;

public class ProductFactory {
  public static Product createProduct(String name) {

    if (name.equals(ProductType.LOAN.getName())) return new Loan();
    if (name.equals(ProductType.STOCK.getName())) return new Stock();
    if (name.equals(ProductType.BOND.getName())) return new Bond();

    throw new RuntimeException("No Such product " + name);
  }
}
