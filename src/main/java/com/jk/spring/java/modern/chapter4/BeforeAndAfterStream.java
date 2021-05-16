package com.jk.spring.java.modern.chapter4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BeforeAndAfterStream {
  List<String> beforeSorting(List<Dish> menu) {
    List<Dish> lowCalories = new ArrayList<>();

    for (Dish dish : menu) {
      if (dish.getCalories() < 400) {
        lowCalories.add(dish);
      }
    }

    // default method + lambda + method reference
    lowCalories.sort(Comparator.comparingInt(Dish::getCalories));
    List<String> lowCaloriesDishesName = new ArrayList<>();
    for (Dish dish : lowCalories) {
      lowCaloriesDishesName.add(dish.getName());
    }

    return lowCaloriesDishesName;
  }

  List<String> afterSorting(List<Dish> menu) {
    List<Dish> lowCalories = menu.stream()
        .filter(dish -> dish.getCalories() < 400)
        // default method + lambda + method reference
        .sorted(Comparator.comparingInt(Dish::getCalories))
        .collect(Collectors.toList());

    return lowCalories.stream().map(Dish::getName).collect(Collectors.toList());
  }
}
