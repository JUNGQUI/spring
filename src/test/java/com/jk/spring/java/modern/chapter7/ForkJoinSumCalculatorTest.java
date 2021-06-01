package com.jk.spring.java.modern.chapter7;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;

class ForkJoinSumCalculatorTest {

  @Test
  void computeTest() {
    long[] targets = LongStream.rangeClosed(1, 20_000).toArray();
    ForkJoinSumCalculator forkJoinSumCalculator = new ForkJoinSumCalculator(targets);

    long startTime = System.currentTimeMillis();
    Long resultByReduce = Arrays.stream(targets).reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - startTime);

    startTime = System.currentTimeMillis();
    Long result = new ForkJoinPool().invoke(forkJoinSumCalculator);
    System.out.println(System.currentTimeMillis() - startTime);

    assertEquals(resultByReduce, result);
  }
}