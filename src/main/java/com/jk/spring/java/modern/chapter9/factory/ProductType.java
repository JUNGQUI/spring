package com.jk.spring.java.modern.chapter9.factory;

public enum ProductType {
  LOAN("대출"),
  STOCK("스톡"),
  BOND("채권");

  private final String name;

  ProductType(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
