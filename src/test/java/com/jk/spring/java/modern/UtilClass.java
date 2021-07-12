package com.jk.spring.java.modern;

import com.google.common.collect.ImmutableList;
import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter4.Dish.Type;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UtilClass {
  public final List<Dish> immutableMenu = ImmutableList.of(
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
}
