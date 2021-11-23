package com.jk.spring.java.modern.chapter16;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ShopTest {

  @Test
  void asyncPriceTest() {
    Shop shop = new Shop("BestShop");

    long start = System.nanoTime();

    Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
    long invocationTime = ((System.nanoTime() - start) / 1_000_000);
    System.out.println("async : " + invocationTime);

    doSomethingElse();

    long startGet = System.nanoTime();

    try {
      double price = futurePrice.get();
      System.out.printf("Price is %.2f%n", price);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    long retrievalTime = ((System.nanoTime() - startGet) / 1_000_000);
    System.out.println("async get : " + retrievalTime);

    long startSync = System.nanoTime();
    String syncPrice = shop.getPrice("my favorite product");
    long syncTime = ((System.nanoTime() - startSync) / 1_000_000);

    System.out.println("sync : " + syncTime);
  }

  @Test
  void shopGetPriceTest() {
    List<Shop> shopList = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll")
    );

    long start = System.nanoTime();

    System.out.println(ShopMethod.findPrices(shopList, "myPhone275"));
    System.out.println((System.nanoTime() - start) / 1_000_000);
  }

  @Test
  void shopGetPriceParallelsTest() {
    List<Shop> shopList = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll")
    );

    long start = System.nanoTime();

    System.out.println(ShopMethod.findPricesParallels(shopList, "myPhone275"));
    System.out.println((System.nanoTime() - start) / 1_000_000);
  }

  private void doSomethingElse() {
    System.out.println("Do Something Else");
  }

  @Test
  void shopCompletableFutureTest() {
    List<Shop> shopList = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll")
    );

    ShopCompletableFuture shopCompletableFuture = new ShopCompletableFuture();
    List<CompletableFuture<String>> results = shopCompletableFuture.getPriceAsync(shopList, "myPhone275");

    List<String> result = results.stream()
        .map(c -> {
          try {
            return c.get();
          } catch (Exception ex) {
            return "";
          }
        })
        .collect(Collectors.toList());

    result.forEach(System.out::println);
  }

  @Test
  void shopQuoteTest() {
    Shop bestPrice = new Shop("BestPrice");
    Shop letsSaveBig = new Shop("LetsSaveBig");
    Shop myFavoriteShop = new Shop("MyFavoriteShop");
    Shop buyItAll = new Shop("BuyItAll");

    Quote bestPriceQuote = Quote.parse(bestPrice.getPrice("myPhone275"));
    Quote letsSaveBigQuote = Quote.parse(letsSaveBig.getPrice("myPhone275"));
    Quote myFavoriteShopQuote = Quote.parse(myFavoriteShop.getPrice("myPhone275"));
    Quote buyItAllQuote = Quote.parse(buyItAll.getPrice("myPhone275"));

    System.out.println(bestPriceQuote.getPrice());
    System.out.println(Discount.applyDiscount(bestPriceQuote));
    System.out.println(letsSaveBigQuote.getPrice());
    System.out.println(Discount.applyDiscount(letsSaveBigQuote));
    System.out.println(myFavoriteShopQuote.getPrice());
    System.out.println(Discount.applyDiscount(myFavoriteShopQuote));
    System.out.println(buyItAllQuote.getPrice());
    System.out.println(Discount.applyDiscount(buyItAllQuote));
  }

  @Test
  void shopQuoteStreamTest() {
    // 대략 8초~ 소요
    List<Shop> shopList = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll")
    );

    List<String> prices = ShopMethod.findPricesStream(shopList, "myPhone275");

    prices.forEach(System.out::println);
  }

  @Test
  void combineTest() {
    // 대략 2초~ 소요
    List<Shop> shopList = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll")
    );

    List<String> prices = ShopMethod.findPricesCombine(shopList, "myPhone275");

    prices.forEach(System.out::println);
  }

  @Test
  void randomDelay() {
    List<Shop> shopList = Arrays.asList(
        new Shop("BestPrice"),
        new Shop("LetsSaveBig"),
        new Shop("MyFavoriteShop"),
        new Shop("BuyItAll")
    );

    long start = System.nanoTime();

    ShopCompletableFuture shopCompletableFuture = new ShopCompletableFuture();
    CompletableFuture[] shopCompletableFutures = shopCompletableFuture.findPricesStream(shopList, "myPhone275")
        .map(f -> f.thenAccept(
            s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")
        ))
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(shopCompletableFutures).join();

    System.out.println("All Shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + "m secs");
  }
}