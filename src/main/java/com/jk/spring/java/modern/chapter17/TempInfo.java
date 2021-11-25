package com.jk.spring.java.modern.chapter17;

import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TempInfo {
  public static final Random random = new Random();

  private final String town;
  private final int temp;

  public static TempInfo fetch(String town) {
    if (random.nextInt(10) == 0) throw new RuntimeException("Error!");

    return new TempInfo(town, random.nextInt(100));
  }

  @Override
  public String toString() {
    return town + " : " + temp;
  }
}
