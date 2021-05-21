package com.jk.spring.java.modern.chapter5;

import static org.junit.jupiter.api.Assertions.*;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class StreamMapTest {
  private final List<String> testCase = ImmutableList.of(
      "Hello", "World"
  );
  private final List<Integer> integerTestCases = ImmutableList.of(
      1, 2, 3, 4, 5
  );
  private final List<Integer> integerEmptyTestCases = Collections.emptyList();

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

  @Test
  void reduceTest() {
    int result = 0;
    for (int i : integerTestCases) {
      result += i;
    }

    assertEquals(result, StreamReduce.addWithReduce(integerTestCases));
  }

  @Test
  void reduceWithoutInitValueTest() {
    int forLoopResult = 0;
    for (int i : integerTestCases) {
      forLoopResult += i;
    }

    Optional<Integer> optionalResult = StreamReduce.addWithReduceWithoutInitValue(integerTestCases);

    int result = optionalResult.orElse(20);

    assertEquals(forLoopResult, result);
  }

  @Test
  void reduceWithoutInitValueNullTest() {
    Optional<Integer> optionalResult = StreamReduce.addWithReduceWithoutInitValue(integerEmptyTestCases);

    int result = optionalResult.orElse(20);

    assertEquals(20, result);
  }
}