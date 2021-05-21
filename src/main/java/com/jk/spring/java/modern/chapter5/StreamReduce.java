package com.jk.spring.java.modern.chapter5;

import java.util.List;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamReduce {
  public int addWithReduce(List<Integer> target) {
    return target.stream()
        //.reduce(0, (a, b) -> a+b);
        .reduce(0, Integer::sum);
  }

  public Optional<Integer> addWithReduceWithoutInitValue(List<Integer> target) {
    return target.stream().reduce(Integer::sum);
  }
}
