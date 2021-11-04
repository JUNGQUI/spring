package com.jk.spring.java.modern.chapter16;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;
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
    double syncPrice = shop.getPrice("my favorite product");
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
}