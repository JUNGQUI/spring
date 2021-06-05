package com.jk.spring.java.modern.chapter7;

import java.util.Spliterator;
import java.util.function.Consumer;
import lombok.Getter;

@Getter
public class WordCounterSpliterator implements Spliterator<Character> {

  private final String string;
  private int currentChar = 0;

  public WordCounterSpliterator(String s) {
    this.string = s;
  }

  @Override
  public boolean tryAdvance(Consumer<? super Character> consumer) {
    consumer.accept(string.charAt(currentChar++));
    return currentChar < string.length();
  }

  @Override
  public Spliterator<Character> trySplit() {
    int currentSize = string.length() - currentChar;

    if (currentSize < 10) {
      return null;
    }

    for (int splitPos = currentSize / 2 + currentChar; splitPos < string.length(); splitPos++) {
      if (Character.isWhitespace(string.charAt(splitPos))) {
        Spliterator<Character> spliterator = new WordCounterSpliterator(string.substring(currentChar, splitPos));
        currentChar = splitPos;
        return spliterator;
      }
    }
    return null;
  }

  @Override
  public long estimateSize() {
    return (long) string.length() - currentChar;
  }

  @Override
  public int characteristics() {
    return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
  }
}
