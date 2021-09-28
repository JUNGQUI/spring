package com.jk.spring.java.modern.chapter15;

import java.util.function.IntConsumer;

public class CallbackStyleExample {
  public static void main(String args[]) {
    int x = 1337;
    Result result = new Result();

    f(x, (int y) -> {
      result.setLeft(y);
      System.out.println(result.getLeft() + result.getRight());
    });

    g(x, (int y) -> {
      result.setRight(y);
      System.out.println(result.getLeft() + result.getRight());
    });
  }

  static void f(int x, IntConsumer dealWithResult) {

  }

  static void g(int x, IntConsumer dealWithResult) {

  }
}
