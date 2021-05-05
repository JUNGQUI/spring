package com.jk.spring.java.modern.chapter2;


import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ApplePredicateTest {

  @Test
  void predicateTest() {

    List<Apple> inventory = Arrays.asList(
        new Apple(125, Color.GREEN),
        new Apple(155, Color.GREEN),
        new Apple(170, Color.RED)
    );

    List<Apple> heavyApples = FilterApple.filterApple(inventory, new FilterHeavy());
    List<Apple> greenApples = FilterApple.filterApple(inventory, new FilterGreenColor());

    heavyApples.forEach(heavyApple -> Assertions.assertTrue(heavyApple.getWeight() > 150));
    greenApples.forEach(greenApple -> Assertions.assertEquals(Color.GREEN, greenApple.getColor()));
  }
}