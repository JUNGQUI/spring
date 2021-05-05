package com.jk.spring.java.modern.chapter2;

import com.jk.spring.java.modern.chapter1.Apple;

public class FilterHeavy implements ApplePredicate{

  @Override
  public boolean test(Apple apple) {
    return apple.getWeight() > 150;
  }
}
