package com.jk.spring.java.modern.chapter5;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Trader {
  private final String name;
  private final String city;

  public String toString() {
    return "Trader:" + this.name + " in " + this.city;
  }
}
