package com.jk.spring.java.modern.chapter2;

import com.jk.spring.java.modern.chapter1.Apple;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FilterApple {
  public List<Apple> filterApple(List<Apple> inventory, ApplePredicate applePredicate) {
    return inventory.stream()
        .filter(applePredicate::test)
        .collect(Collectors.toList());
  }
}
