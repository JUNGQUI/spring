package com.jk.spring.java.modern.chapter1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Component;

@Component
public class JKLambda {

  public List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
    List<Apple> result = new ArrayList<>();

    for (Apple apple : inventory) {
      if (p.test(apple)) {
        result.add(apple);
      }
    }

    return result;
  }
}
