package com.jk.spring.java.modern.chapter15;

import static com.jk.spring.java.modern.chapter15.Result.f;
import static com.jk.spring.java.modern.chapter15.Result.g;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    int x = 1337;

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Future<Integer> left = executorService.submit(() -> f(x));
    Future<Integer> right = executorService.submit(() -> g(x));
    System.out.println(left.get() + right.get());

    executorService.shutdown();
  }
}
