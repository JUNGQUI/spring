package com.jk.spring.java.modern.chapter3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RunnableCallable {
  public void runnable() {
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("J Tag");
      }
    });

    Thread lambdaT = new Thread(() -> System.out.println("J Tag"));
  }

  public void callable() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<String> callable = executorService.submit(new Callable<String>() {
      @Override
      public String call() throws Exception {
        return Thread.currentThread().getName();
      }
    });

    Future<String> lambdaCallable = executorService.submit(() -> Thread.currentThread().getName());
  }
}
