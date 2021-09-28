package com.jk.spring.java.modern.chapter15;

import lombok.Data;

@Data
public class Result {
  private int left;
  private int right;

  public static int f(int x) {
    return x;
  }

  public static int g(int x) {
    return x;
  }
}
