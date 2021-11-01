package com.jk.spring.java.modern.chapter16;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

class ShopTest {

  @Test
  public void asyncPriceTest() {
    Shop shop = new Shop("BestShop");

    long start = System.nanoTime();

    Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
    long invocationTime = ((System.nanoTime() - start) / 1_000_000);
    System.out.println("async : " + invocationTime);

    doSomethingElse();

    try {
      double price = futurePrice.get();
      System.out.printf("Price is %.2f%n", price);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }

    long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
    System.out.println("sync : " + retrievalTime);
  }

  private void doSomethingElse() {
    System.out.println("Do Something Else");
  }
}