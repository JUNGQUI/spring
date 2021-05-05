package com.jk.spring.java.modern.chapter2;

import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;

public class FilterGreenColor implements ApplePredicate {

  @Override
  public boolean test(Apple apple) {
    return Color.GREEN.equals(apple.getColor());
  }
}
