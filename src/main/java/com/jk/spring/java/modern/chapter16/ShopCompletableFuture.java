package com.jk.spring.java.modern.chapter16;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShopCompletableFuture {

  public List<CompletableFuture<String>> getPriceAsync(List<Shop> shops, String product) {
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
        runnable -> {
          Thread t = new Thread(runnable);
          t.setDaemon(true);
          return t;
        });

    return shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor))
        .collect(Collectors.toList());
  }

  public Stream<CompletableFuture<String>> findPricesStream(List<Shop> shops, String product) {
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
        runnable -> {
          Thread t = new Thread(runnable);
          t.setDaemon(true);
          return t;
        });

    return shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithRandomDelay(product), executor))
        .map(future -> future.thenApply(Quote::parse))
        .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
  }
}
