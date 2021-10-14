package com.jk.spring.java.modern.chapter15;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExecutorServiceTest {

  ExecutorService executorService = Executors.newSingleThreadExecutor();

  @Test
  void partialReactiveTest() throws Exception {
    int x = 5;
    int t = p(x);
    Future<Integer> firstFuture = executorService.submit(() -> q1(t));
    Future<Integer> secondFuture = executorService.submit(() -> q2(t));

    Assertions.assertEquals(17, r(firstFuture.get(), secondFuture.get()));
  }

  private int p(int x) {
    return x + 1;
  }

  private int q1(int x) {
    return x + 2;
  }

  private int q2(int x) {
    return x + 3;
  }

  private int r(int a1, int a2) {
    return a1 + a2;
  }
}
