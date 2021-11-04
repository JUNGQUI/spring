package com.jk.spring.java.modern.chapter16;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Shop {
  private String shopName;
  private static final Random randomGenerator = new Random();

  public double getPrice(String product) {
    return calculatePrice(product);
  }

  public Future<Double> getPriceAsync(String product) {
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();

    new Thread(() -> {
      double price = calculatePrice(product);
      futurePrice.complete(price);
    }).start();

    return futurePrice;
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

  public String getName() {
    return shopName;
  }
}
