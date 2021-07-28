package com.jk.spring.java.modern.chapter11;


import java.util.Optional;
import org.junit.jupiter.api.Test;

class NullCaseTest {

  @Test
  void ifPresentTest() {
    Optional<String> stringOptional = Optional.of("is not null");
    String after = stringOptional.isPresent() ? stringOptional.get() : "is null";

    System.out.println(after);
    stringOptional.ifPresent(System.out::println);
  }
}