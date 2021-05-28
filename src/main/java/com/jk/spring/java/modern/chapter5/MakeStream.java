package com.jk.spring.java.modern.chapter5;

import java.util.Arrays;
import java.util.stream.Stream;

public class MakeStream {
  public void valueStream() {
    Stream<String> stringStream = Stream.of("Mordern", "Java", "In", "Action");
    stringStream.forEach(System.out::println);
  }

  public int arrayStreamSum() {
    int[] numbers = {2, 3, 5, 7, 11, 13};
    return Arrays.stream(numbers).sum();
  }

  public void iterateStream() {
    Stream.iterate(0, n -> n+2)
        .limit(10)
        .forEach(System.out::println);
  }

  public void generateStream() {
    Stream.generate(Math::random)
        .limit(5)
        .forEach(System.out::println);
  }
}
