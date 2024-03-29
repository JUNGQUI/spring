package com.jk.spring.java.modern.chapter5;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {
  private final Trader trader;
  private final int year;
  private final int value;

  public String toString() {
    return "{" + this.trader + ", "
        + "year:" + this.year + ", "
        + "value: " + this.value + "}";
  }
}
