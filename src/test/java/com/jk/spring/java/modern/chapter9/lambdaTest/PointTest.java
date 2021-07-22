package com.jk.spring.java.modern.chapter9.lambdaTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PointTest {
  @Test
  void testMoveRightBy() throws Exception {
    Point p1 = new Point(5, 5);
    Point p2 = p1.moveRightBy(10);
    assertEquals(15, p2.getX());
    assertEquals(5, p2.getY());
  }
  @Test
  void testComparingTwoPoints() throws Exception {
    Point p1 = new Point(10, 15);
    Point p2 = new Point(10, 20);
    int result = Point.compareByXAndThenY.compare(p1, p2);
    assertTrue(result < 0); // p1의 x와 p2의 x는 같지만, p1의 y와 p2의 y는 p1이 작기에 음수가 리턴된다.
  }
}