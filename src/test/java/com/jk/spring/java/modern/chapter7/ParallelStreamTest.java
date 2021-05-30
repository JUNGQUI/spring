package com.jk.spring.java.modern.chapter7;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ParallelStreamTest {

  @Test
  void parallelsTest() {
    long n = 100000000L;

    long result1 = ParallelStream.nonParallelSum(n);
    long result2 = ParallelStream.parallelsSum(n);

    assertEquals(result1, result2);
  }
}