package com.jk.spring.java.modern.chapter9.factory;

public class Bond extends Product{
  public Bond() {
    this.setCode("C_BOND");
    this.setProductName("채권");
    this.setPortion(0.5);
  }
}
