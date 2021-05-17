package com.jk.spring.java.modern.chapter5;

import com.google.common.collect.ImmutableList;
import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter4.Dish.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DishTestChapter5 {

  private final List<Dish> immutableMenu = ImmutableList.of(
      new Dish("pork", false, 800, Type.MEAT),
      new Dish("beef", false, 700, Type.MEAT),
      new Dish("chicken", false, 400, Type.MEAT),
      new Dish("french fries", true, 530, Type.OTHER),
      new Dish("rice", true, 350, Type.OTHER),
      new Dish("season", true, 120, Type.OTHER),
      new Dish("pizza", true, 550, Type.OTHER),
      new Dish("prawns", false, 300, Type.FISH),
      new Dish("salmon", false, 450, Type.FISH)
  );

  private List<Dish> mutableMenu = Arrays.asList(
      new Dish("season", true, 120, Type.OTHER),
      new Dish("prawns", false, 300, Type.FISH),
      new Dish("rice", true, 350, Type.OTHER),
      new Dish("chicken", false, 400, Type.MEAT),
      new Dish("salmon", false, 450, Type.FISH),
      new Dish("french fries", true, 530, Type.OTHER),
      new Dish("pizza", true, 550, Type.OTHER),
      new Dish("beef", false, 700, Type.MEAT),
      new Dish("pork", false, 800, Type.MEAT),
      new Dish("pork", false, 800, Type.MEAT)
  );

  @Test
  void distinctTest() {
    mutableMenu.forEach(d -> System.out.println(d.getName()));

    mutableMenu.stream()
        .distinct()
        .forEach(d -> System.out.println(d.getName()));
  }

  @Test
  void takeWhile() {
    List<Dish> nonTakeWhile = mutableMenu.stream()
        .filter(d -> d.getCalories() < 500)
        .collect(Collectors.toList());

    List<Dish> takeWhile = mutableMenu.stream()
        .takeWhile(d -> d.getCalories() < 500)
        .collect(Collectors.toList());

    Assertions.assertEquals(nonTakeWhile.size(), takeWhile.size());
    for (int i = 0; i < nonTakeWhile.size(); i++) {
      Assertions.assertEquals(nonTakeWhile, takeWhile);
    }
  }

}
