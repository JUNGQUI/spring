package com.jk.spring.java.modern.chapter3;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MethodReference {
  public void printMethod(List<String> someStrings) {
    // 원래라면 이렇게 했어야 할 것을
    // someStrings.forEach(s -> log.info(s));
    // 이렇게 구현이 가능해진다.
    someStrings.forEach(log::info);
  }
}
