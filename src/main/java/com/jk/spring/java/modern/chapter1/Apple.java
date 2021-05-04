package com.jk.spring.java.modern.chapter1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apple {
  private int weight;
  private Color color;

  public static boolean isGreenApple(Apple apple) {
    return Color.GREEN.equals(apple.getColor());
  }

  public static boolean isHeavyApple(Apple apple) {
    return apple.getWeight() > 150;
  }
}
