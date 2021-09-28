package com.jk.spring.java.modern.chapter15;

import static com.jk.spring.java.modern.chapter15.Result.f;
import static com.jk.spring.java.modern.chapter15.Result.g;

class ThreadExample {
  public static void main(String[] args) throws InterruptedException {
    int x = 1337;
    Result result = new Result();

    Thread t1 = new Thread(() -> result.setLeft(f(x)));
    Thread t2 = new Thread(() -> result.setRight(g(x)));
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(result.getLeft() + result.getRight());
  }
}