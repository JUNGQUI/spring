package com.jk.spring.java.modern.chapter3;

import java.util.List;
import java.util.stream.Collectors;

public class JKConsumerAndFunction {
  public void useConsumer(List<String> someStrings) {
    someStrings.forEach(s -> System.out.println(s));
    // 또는
    someStrings.forEach(System.out::println);
  }

  public void useFunction(List<String> someStrings) {
    List<String> editedSomeStrings = someStrings.stream()
        .map(s -> s + " edited by function in map method")
        .collect(Collectors.toList());
  }
}
