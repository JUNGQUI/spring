package com.jk.spring.java.modern.chapter6;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.flatMapping;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter4.Dish.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GroupingDish {

  public static final Map<String, List<String>> dishTags = new HashMap<>();

  public enum CaloricLevel {
    DIET, NORMAL, FAT
  }

  private void init() {
    dishTags.put("pork", Arrays.asList("greasy", "salty"));
    dishTags.put("beef", Arrays.asList("salty", "roasted"));
    dishTags.put("chicken", Arrays.asList("fried", "crisp"));
    dishTags.put("french fries", Arrays.asList("greasy", "fried"));
    dishTags.put("rice", Arrays.asList("light", "natural"));
    dishTags.put("season fruit", Arrays.asList("fresh", "natural"));
    dishTags.put("pizza", Arrays.asList("tasty", "salty"));
    dishTags.put("prawns", Arrays.asList("tasty", "roasted"));
    dishTags.put("salmon", Arrays.asList("delicious", "fresh"));
  }

  public Map<Type, List<Dish>> groupingByDishType(List<Dish> menus) {
    return menus.stream()
        .collect(groupingBy(Dish::getType));
  }

  public Map<CaloricLevel, List<Dish>> groupingByDishCalories(List<Dish> menus) {
    return menus.stream()
        .collect(groupingBy(dish -> {
          if (dish.getCalories() <= 400) {
            return CaloricLevel.DIET;
          } else if (dish.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
          }

          return CaloricLevel.FAT;
        }));
  }

  public Map<Type, List<Dish>> groupingByTypeWithDishCalories(List<Dish> menus) {
    // 이렇게 하면 500 칼로리 이상의 타입이 없는 경우 타입 자체 (key) 가 사라지게 된다.
//    return menus.stream()
//        .filter(m -> m.getCalories() > 500)
//        .collect(groupingBy(Dish::getType));
    // filter 를 상속받은 filtering 을 사용 할 경우 타입에 따라 grouping 이 된 후 필터로 재그룹화하기에 비어있는 타입이 완성된다.
    return menus.stream()
        .collect(
            groupingBy(
                Dish::getType
                , filtering(dish -> dish.getCalories() > 500, toList())
            )
        );
  }

  public Map<Type, Set<String>> groupingByTypeWithName(List<Dish> menus) {
    init();

    return menus.stream()
        .collect(groupingBy(
            Dish::getType
            , flatMapping(dish -> dishTags.get(dish.getName()).stream(), toSet()))
        );
  }
}
