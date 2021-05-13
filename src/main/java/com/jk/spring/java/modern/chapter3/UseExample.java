package com.jk.spring.java.modern.chapter3;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UseExample {
  private final ExampleClass exampleClass;

  public String withInterface(List<String> added) {
    return added.stream()
        .map(exampleClass::added)
        .collect(Collectors.joining("\n"));
  }

  public String withLambda(List<String> added) {
    return added.stream()
        .map(s -> s + " with lambda added!")
        .collect(Collectors.joining("\n"));
  }
}
