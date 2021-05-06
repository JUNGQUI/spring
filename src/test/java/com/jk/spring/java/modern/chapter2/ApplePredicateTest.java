package com.jk.spring.java.modern.chapter2;


import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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

  @Test
  void predicateT_Test() {
    List<String> stringInventory = Arrays.asList("a", "ab", "cb", "bd", "ce");
    List<String> startWithA = FilterT.filter(stringInventory, new Predicate<String>() {
      @Override
      public boolean test(String s) {
        return s.startsWith("a");
      }
    });

    List<String> startWithALambda = FilterT.filter(stringInventory, (s -> s.startsWith("a")));

    Assertions.assertEquals(startWithA.size(), startWithALambda.size());

    for (int i = 0; i < startWithA.size(); i++) {
      Assertions.assertEquals(startWithA.get(i), startWithALambda.get(i));
    }
  }
}