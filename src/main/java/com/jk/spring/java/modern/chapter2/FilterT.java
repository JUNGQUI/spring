package com.jk.spring.java.modern.chapter2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FilterT {
  public <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    list.forEach(t -> {
      if (p.test(t)) {
        result.add(t);
      }
    });

    return result;
  }
}
