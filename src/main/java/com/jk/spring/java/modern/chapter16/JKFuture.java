package com.jk.spring.java.modern.chapter16;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JKFuture {
  public void futureTest() {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Future<Double> future = executorService.submit(new Callable<Double>() {
      public Double call() {
        return doSomeLongComputation();
      }
    });
    doSomethingElse();
    try {
      Double result = future.get(1, TimeUnit.SECONDS);
    } catch (ExecutionException ee) {
      // 계산 중 예외 발생
    } catch (InterruptedException ie) {
      // 인터럽트 예외
    } catch (TimeoutException te) {
      // 타임아웃
    }
  }

  private void doSomethingElse() {
    // ...
    // do something other
    // ...
  }

  private Double doSomeLongComputation() {
    // ...
    // long task
    // ...

    return Double.MAX_VALUE;
  }
}
