package com.jk.spring.java.modern.chapter5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StreamMap {
  public List<String> splitAndDistinctArraysStream(List<String> someStrings) {
    List<String> result = new ArrayList<>();
    someStrings.stream()
        .map(s -> s.split(""))
        .forEach(s -> Arrays.stream(s).distinct().forEach(inner -> {
          if (!result.contains(inner)) {
            result.add(inner);
          }
        }));
    return result;
  }

  public List<String> splitAndDistinctArraysStreamWithFlatMap(List<String> someStrings) {
    return someStrings.stream()
        .map(s -> s.split(""))
        .flatMap(Arrays::stream)
        .distinct()
        .collect(Collectors.toList());
  }
}
