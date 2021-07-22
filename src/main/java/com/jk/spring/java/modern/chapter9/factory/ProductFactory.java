package com.jk.spring.java.modern.chapter9.factory;

import java.util.Map;
import java.util.function.Supplier;

public class ProductFactory {
  public static Product createProduct(String name) {

    if (name.equals(ProductType.LOAN.getName())) return new Loan();
    if (name.equals(ProductType.STOCK.getName())) return new Stock();
    if (name.equals(ProductType.BOND.getName())) return new Bond();

    throw new RuntimeException("No Such product " + name);
  }

  public static final Map<String, Supplier<Product>> productInitMap = Map.of(
      ProductType.LOAN.getName(), Loan::new,
      ProductType.STOCK.getName(), Stock::new,
      ProductType.BOND.getName(), Bond::new
  );

  public static Product createProductWithLambda(String name) {
    Supplier<Product> p = productInitMap.get(name);
    if (p != null) return p.get();
    throw new RuntimeException("No Such product " + name);
  }
}
