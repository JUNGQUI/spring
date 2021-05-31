package com.jk.spring.java.modern.chapter7;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;

class ForkJoinSumCalculatorTest {

  @Test
  void computeTest() {
    long[] targets = LongStream.rangeClosed(1, 1000).toArray();
    ForkJoinSumCalculator forkJoinSumCalculator = new ForkJoinSumCalculator(targets);
    Long result = forkJoinSumCalculator.compute();

    assertEquals(Arrays.stream(targets).reduce(0L, Long::sum), result);
  }
}