package com.jk.spring.java.modern.chapter7;

import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CustomCountWord {
  public int countWordsIteratively(String s) {
    int counter = 0;
    boolean lastSpace = true;

    for (char c : s.toCharArray()) {
      if (Character.isWhitespace(c)) {
        lastSpace = true;
      } else {
        if (lastSpace) counter++;
        lastSpace = false;
      }
    }

    return counter;
  }

  public int countWordsByStream(Stream<Character> stream) {
    WordCounter wordCounter = stream.reduce(new WordCounter(0, true),
        WordCounter::accumulate, WordCounter::combine);

    return wordCounter.getCounter();
  }
}
