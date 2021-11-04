package com.jk.spring.java.modern.chapter16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class ShopSupplyAsync {
  private String shopName;
  private static final Random randomGenerator = new Random();

  public Future<Double> getPriceAsync(String product) {
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
  }



  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private double calculatePrice(String product) {
    delay();
    return randomGenerator.nextDouble() * product.charAt(0) + product.charAt(1);
  }
}
