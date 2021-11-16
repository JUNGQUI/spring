package com.jk.spring.java.modern.chapter16;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShopMethod {
  public static List<String> findPrices(List<Shop> shops, String product) {
    return shops.stream()
        .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
        .collect(toList());
  }

  public static List<String> findPricesParallels(List<Shop> shops, String product) {
//    List<CompletableFuture<String>> completableFutureList = shops.stream()
//        .map(shop -> CompletableFuture.supplyAsync(
//            () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
//        ))
//        .collect(toList());
//
//    return completableFutureList.stream()
//        .map(CompletableFuture::join)
//        .collect(toList());
    return shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(
            () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
        ))
        .collect(toList())
        .stream()
        .map(CompletableFuture::join)
        .collect(toList());
  }

  public static List<String> findPricesStream(List<Shop> shops, String product) {
    return shops.stream()
        .map(shop -> shop.getPrice(product))
        .map(Quote::parse)
        .map(Discount::applyDiscount)
        .collect(toList());
  }

  public static List<String> findPricesCombine(List<Shop> shops, String product) {
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
        runnable -> {
          Thread t = new Thread(runnable);
          t.setDaemon(true);
          return t;
        });

    List<CompletableFuture<String>> priceFutures = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
        .map(future -> future.thenApply(Quote::parse))
        .map(future -> future.thenCompose(
            quote -> CompletableFuture.supplyAsync(
                () -> Discount.applyDiscount(quote), executor)
        ))
        .collect(toList());

    return priceFutures.stream()
        .map(CompletableFuture::join)
        .collect(toList());
  }
}
