package com.jk.spring.java.modern.chapter9;

import com.jk.spring.java.modern.UtilClass;
import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter6.GroupingDish.CaloricLevel;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StreamLazyEvaluationTest {

  @Test
  void lazyEvaluationTest() {
    List<Dish> dishByLazy = StreamLazyEvaluation.lazyEvaluation(UtilClass.immutableMenu);
    List<Dish> dishByCommand = new ArrayList<>();

    for (Dish dish : UtilClass.immutableMenu) {
      if (dish.getCaloricLevel().equals(CaloricLevel.NORMAL)) {
        if (dish.getCalories() >= 550) {
          dishByCommand.add(dish);
        }
      }
    }

    Assertions.assertEquals(dishByCommand.size(), dishByLazy.size());

    for (int i = 0; i < dishByCommand.size(); i++) {
      Assertions.assertEquals(dishByCommand.get(i), dishByLazy.get(i));
    }

  }
}