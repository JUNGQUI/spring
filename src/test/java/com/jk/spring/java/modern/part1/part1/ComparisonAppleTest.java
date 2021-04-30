package com.jk.spring.java.modern.part1.part1;

import java.util.ArrayList;
import java.util.List;
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

  @Test
  void comparison() {
    Apple apple1 = new Apple(1);
    Apple apple2 = new Apple(2);
    Apple apple3 = new Apple(3);

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
}