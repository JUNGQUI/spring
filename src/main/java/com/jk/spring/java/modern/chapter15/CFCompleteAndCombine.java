package com.jk.spring.java.modern.chapter15;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CFCompleteAndCombine {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    int x = 1337;
    int y = 1338;
    int z = 1339;

    CompletableFuture<Integer> a = new CompletableFuture<>();
    CompletableFuture<Integer> b = new CompletableFuture<>();
    CompletableFuture<Integer> c = a.thenCombine(b, Integer::sum);

    executorService.submit(() -> a.complete(x));
    executorService.submit(() -> b.complete(y));

    System.out.println(c.get());
  }

}
