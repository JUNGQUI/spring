package com.jk.spring.java.modern.chapter17;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TempInfoTest {
  @Test
  void mainTest() {
    TempPublisher.pub("New York");
  }
}