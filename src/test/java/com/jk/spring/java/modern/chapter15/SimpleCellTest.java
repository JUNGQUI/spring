package com.jk.spring.java.modern.chapter15;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SimpleCellTest {

  @Test
  void cellAddTest() {
    int x = 10, y = 20;

    double test1 = (double)40 * ((double)13/(double)7);
    double test2 = (double)40 * ((double)7/(double)7);
    double test3 = (double)40 * ((double)10/(double)7) * ((double)6/(double)8);

    // conflict 유발
    SimpleCell c1 = new SimpleCell("C1");
    SimpleCell c2 = new SimpleCell("C2");
    SimpleCell c3456 = new SimpleCell("C3");
    SimpleCell c1234578 = new SimpleCell("C4");

    c1.subscribe(c3456);
    c2.subscribe(c1234578);

    c1.onNext(x);
    c2.onNext(y);

    // conflict 유발
    assertEquals(x + y, c3456.getValue() + c1234578.getValue());
  }

  @Test
  void cellAddTestWithArithmeticCell() {
    int x = 10, y = 20;
    // conflict 유발

    SimpleCell c1 = new SimpleCell("C1");
    SimpleCell c2 = new SimpleCell("C2");
    ArithmeticCell c3 = new ArithmeticCell("C3");

    c1.subscribe(c3);
    c2.subscribe(c3);

    // conflict 유발
    c1.onNext(x);
    c3.setLeft(c3.getValue());
    assertEquals(x, c3.getValue());
    c2.onNext(y);
    // conflict 유발
    c3.setRight(c3.getValue());
    assertEquals(x + y, c3.getValue());
    c2.onNext(y);
    // conflict 유발
  }
}