package com.jk.spring.java.modern.chapter9.lambdaTest;

import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Point {
  private int x;
  private int y;

  public Point moveRightBy(int x) {
    return new Point(this.x + x, this.y);
  }

  public final static Comparator<Point> compareByXAndThenY = Comparator.comparing(Point::getX).thenComparing(Point::getY);
}
