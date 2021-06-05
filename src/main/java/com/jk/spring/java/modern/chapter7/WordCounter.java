package com.jk.spring.java.modern.chapter7;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WordCounter {
  private final int counter;
  private final boolean lastSpace;

  public WordCounter accumulate(Character c) {
    if (Character.isWhitespace(c)) {
      return lastSpace ? this : new WordCounter(counter, true);
    }

    return lastSpace ? new WordCounter(counter + 1, false) : this;
  }

  public WordCounter combine(WordCounter wordCounter) {
    return new WordCounter(counter + wordCounter.getCounter(), wordCounter.isLastSpace());
  }
}
