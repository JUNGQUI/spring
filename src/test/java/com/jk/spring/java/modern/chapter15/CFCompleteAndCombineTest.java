package com.jk.spring.java.modern.chapter15;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

class CFCompleteAndCombineTest {

  @Test
  void combineTest() throws ExecutionException, InterruptedException {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    int x = 1337, y = 1339, z = x+y;

    CompletableFuture<Integer> a = new CompletableFuture<>();
    CompletableFuture<Integer> b = new CompletableFuture<>();
    CompletableFuture<Integer> c = a.thenCombine(b, Integer::sum);

    executorService.submit(() -> a.complete(x));
    executorService.submit(() -> b.complete(y));

    assertEquals(z, c.get());
  }
}