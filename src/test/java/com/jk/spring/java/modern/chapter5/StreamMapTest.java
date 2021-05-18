package com.jk.spring.java.modern.chapter5;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class StreamMapTest {
  private final List<String> testCase = ImmutableList.of(
      "Hello", "World"
  );

  @Test
  void test1() {
    List<String> first = StreamMap.splitAndDistinctArraysStream(testCase);
    List<String> second = StreamMap.splitAndDistinctArraysStreamWithFlatMap(testCase);

    for (int i = 0; i < first.size(); i++) {
      System.out.println(first.get(i) + " | " + second.get(i));
      assertEquals(first.get(i), second.get(i));
    }
  }

  @Test
  void flatTest() {
    List<List<String>> listOfStringList = Collections.singletonList(Arrays.asList("string1", "string2", "string3"));
    List<String> result = Collections.emptyList();

    result = listOfStringList.stream()
        .flatMap(strings -> strings.stream()
            .peek(s -> {
              System.out.println(strings);
              System.out.println(s);
            })
        )
        .collect(Collectors.toList());
  }
}