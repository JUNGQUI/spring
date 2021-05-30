package com.jk.spring.java.modern.chapter7;

import java.util.stream.LongStream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ParallelStream {
  public long nonParallelSum(long n) {
    long start = System.currentTimeMillis();
    long result = LongStream.rangeClosed(1, n)
        .reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - start);
    return result;
  }

  public long parallelsSum(long n) {
    long start = System.currentTimeMillis();
    long result = LongStream.rangeClosed(1, n)
        .parallel()
        .reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - start);
    return result;
  }
}
