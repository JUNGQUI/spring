package com.jk.spring.java.modern.chapter9;

import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter6.GroupingDish.CaloricLevel;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamLazyEvaluation {
  public List<Dish> lazyEvaluation(List<Dish> menu) {
    return menu.stream()
        .filter(d -> d.getCaloricLevel().equals(CaloricLevel.NORMAL))
        .filter(d -> d.getCalories() >= 550)
        .collect(Collectors.toList());
  }
}
