package com.jk.spring.java.modern.chapter9.factory;

public class Stock extends Product {
  public Stock() {
    this.setCode("C_STOCK");
    this.setProductName("스톡");
    this.setPortion(0.33);
  }
}
