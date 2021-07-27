package com.jk.spring.java.modern.chapter11;

import java.util.Optional;
import lombok.Data;

@Data
public class Person {
  private Optional<Car> car;
}
