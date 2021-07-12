package com.jk.spring.java.modern.chapter4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jk.spring.java.modern.chapter6.GroupingDish.CaloricLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Dish {
  private final String name;
  private final boolean vegetarian;
  private final int calories;
  private final Type type;

  @JsonCreator
  public Dish of(
      @JsonProperty("name") String name
      , @JsonProperty("vegitarian") boolean vegetarian
      , @JsonProperty("calories") int calories
      , @JsonProperty("type") Type type) {
    return new Dish(name, vegetarian, calories, type);
  }

  public enum Type {
    MEAT, FISH, OTHER
  }

  public CaloricLevel getCaloricLevel() {
    if (this.getCalories() <= 400) return CaloricLevel.DIET;
    if (this.getCalories() <= 700) return CaloricLevel.NORMAL;
    return CaloricLevel.FAT;
  }
}
