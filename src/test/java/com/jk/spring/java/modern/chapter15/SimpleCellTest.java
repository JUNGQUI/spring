package com.jk.spring.java.modern.chapter15;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import org.junit.jupiter.api.Test;

class SimpleCellTest {

  @Test
  void cellAddTest() {
    int x = 10, y = 20;

    SimpleCell c1 = new SimpleCell("C1");
    SimpleCell c2 = new SimpleCell("C2");
    SimpleCell c3 = new SimpleCell("C3");
    SimpleCell c4 = new SimpleCell("C4");

    c1.subscribe(c3);
    c2.subscribe(c4);

    c1.onNext(x);
    c2.onNext(y);

    assertEquals(x + y, c3.getValue() + c4.getValue());
  }

  @Test
  void cellAddTestWithArithmeticCell() {
    int x = 10, y = 20;


    SimpleCell c1 = new SimpleCell("C1");
    SimpleCell c2 = new SimpleCell("C2");
    ArithmeticCell c3 = new ArithmeticCell("C3");

    c1.subscribe(new Subscriber<Integer>() {
      @Override
      public void onSubscribe(Subscription subscription) {

      }

      @Override
      public void onNext(Integer integer) {
        c3.setLeft(integer);
      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onComplete() {

      }
    });

    c2.subscribe(new Subscriber<Integer>() {
      @Override
      public void onSubscribe(Subscription subscription) {

      }

      @Override
      public void onNext(Integer integer) {
        c3.setRight(integer);
      }

      @Override
      public void onError(Throwable throwable) {

      }

      @Override
      public void onComplete() {

      }
    });

//    c1.subscribe(c3);
//    c2.subscribe(c3);

//    c1.onNext(x);
//    c3.setLeft(c3.getValue());
//    assertEquals(x, c3.getValue());
//    c2.onNext(y);
//    c3.setRight(c3.getValue());
//    assertEquals(x + y, c3.getValue());
//    c2.onNext(y);
//    c3.setLeft(c3.getValue());
//    assertEquals(y + y, c3.getValue());

    c1.onNext(x);
    assertEquals(x, c3.getValue());
    c2.onNext(y);
    assertEquals(x + y, c3.getValue());
    c2.onNext(y);
    assertEquals(x + y, c3.getValue());
  }
}