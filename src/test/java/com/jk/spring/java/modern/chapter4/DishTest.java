package com.jk.spring.java.modern.chapter4;

import com.google.common.collect.ImmutableList;
import com.jk.spring.java.modern.chapter4.Dish.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class DishTest {

//  private final List<Dish> menu = Arrays.asList(
//      new Dish("pork", false, 800, Type.MEAT),
//      new Dish("beef", false, 700, Type.MEAT),
//      new Dish("chicken", false, 400, Type.MEAT),
//      new Dish("french fries", true, 530, Type.OTHER),
//      new Dish("rice", true, 350, Type.OTHER),
//      new Dish("season", true, 120, Type.OTHER),
//      new Dish("pizza", true, 550, Type.OTHER),
//      new Dish("prawns", false, 300, Type.FISH),
//      new Dish("salmon", false, 450, Type.FISH)
//  );

  private final List<Dish> menu = ImmutableList.of(
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

  @Test
  void immutableListFilter() {
    List<Dish> over500CalDish = menu.stream()
        .filter(d -> d.getCalories() > 500)
        .collect(Collectors.toList());

    System.out.println("Over 500 calories");
    over500CalDish.forEach(d -> System.out.println(d.getName()));
    System.out.println("Every dishes");
    menu.forEach(d -> System.out.println(d.getName()));
  }
}