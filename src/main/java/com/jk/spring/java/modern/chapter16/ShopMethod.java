package com.jk.spring.java.modern.chapter16;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
}
