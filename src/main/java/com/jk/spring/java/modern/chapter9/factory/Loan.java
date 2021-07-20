package com.jk.spring.java.modern.chapter9.factory;

public class Loan extends Product {
  public Loan() {
    this.setCode("C_LOAN");
    this.setProductName("대출");
    this.setPortion(0.1);
  }
}
