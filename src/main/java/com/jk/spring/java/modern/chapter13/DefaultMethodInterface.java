package com.jk.spring.java.modern.chapter13;

public interface DefaultMethodInterface {
  public int size();

  default boolean isEmpty() {
    return size() == 0;
  }
}
