package com.jk.spring.java.modern.chapter7;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;

class CustomCountWordTest {

  private final String testTarget = "jklee test code with class 1 2 3 4 5 6 7 8 9 0 10 11 12";

  @Test
  void testMethod() {
    int result = CustomCountWord.countWordsIteratively(testTarget);

    assertEquals(testTarget.split(" ").length, result);
  }

  @Test
  void testMethodWithClass() {
    Stream<Character> characterStream = testTarget.chars().mapToObj(c -> (char) c);
    int result = characterStream.reduce(new WordCounter(0, true)
        , WordCounter::accumulate
        , WordCounter::combine)
        .getCounter();

    assertEquals(testTarget.split(" ").length, result);
  }

  @Test
  void testMethodWithSpliterator() {
    Spliterator<Character> spliterator = new WordCounterSpliterator(testTarget);
    Stream<Character> stream = StreamSupport.stream(spliterator, true);

    int result = CustomCountWord.countWordsByStream(stream);

    assertEquals(testTarget.split(" ").length, result);
  }
}