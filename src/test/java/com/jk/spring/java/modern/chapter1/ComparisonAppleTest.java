package com.jk.spring.java.modern.chapter1;

import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;
import com.jk.spring.java.modern.chapter1.ComparisonApple;
import com.jk.spring.java.modern.chapter1.JKLambda;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class ComparisonAppleTest {

  @Autowired
  private ComparisonApple comparisonApple;

  @Autowired
  private JKLambda jkLambda;

  @Test
  void comparison() {
    Apple apple1 = new Apple(1, Color.GREEN);
    Apple apple2 = new Apple(2, Color.GREEN);
    Apple apple3 = new Apple(3, Color.GREEN);

    List<Apple> a1 = new ArrayList<>();
    List<Apple> a2 = new ArrayList<>();

    a1.add(apple3);
    a1.add(apple2);
    a1.add(apple1);

    a2.add(apple3);
    a2.add(apple2);
    a2.add(apple1);

    a1.forEach(System.out::println);
    a2.forEach(System.out::println);

    comparisonApple.compareBeforeLambda(a1);
    comparisonApple.compareAfterLambda(a2);

    for (int i = 0; i < a1.size(); i++) {
      Assertions.assertEquals(a1.get(i), a2.get(i));
    }

    a1.forEach(System.out::println);
    a2.forEach(System.out::println);
  }

  @Test
  public void resultTest() {
    System.out.println(test("a", (a) -> a += "function Processing"));
  }

  private String test(String a, Function<String, String> appendingFunction) {
    return appendingFunction.apply(a + " test processing ");
  }

  @Test
  void lambdaTest() {
    List<Apple> inventory = Arrays.asList(
        new Apple(150, Color.GREEN),
        new Apple(155, Color.GREEN),
        new Apple(160, Color.RED)
    );

    List<Apple> filteredInventoryWeight = new ArrayList<>();
    List<Apple> filteredInventoryColor = new ArrayList<>();

    filteredInventoryWeight = jkLambda.filterApples(inventory, Apple::isHeavyApple);
    filteredInventoryColor = jkLambda.filterApples(inventory, Apple::isGreenApple);

    filteredInventoryWeight.forEach(apple -> {
      Assertions.assertTrue((apple.getWeight() > 150));
      System.out.println(apple.getColor() + " " + apple.getWeight());
    });

    filteredInventoryColor.forEach(apple -> {
      Assertions.assertEquals(Color.GREEN, apple.getColor());
      System.out.println(apple.getColor() + " " + apple.getWeight());
    });
  }

  @Test
  void collectionsAPITest() {
    List<String> test = Arrays.asList("a", "b", "c");

    for(String t : test) {
      if (t.equals("a")) {
        t += "1";
      } else {
        t += "2";
      }
    }

    test.forEach(System.out::println);
  }
}