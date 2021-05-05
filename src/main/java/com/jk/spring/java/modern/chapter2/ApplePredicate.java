package com.jk.spring.java.modern.chapter2;

import com.jk.spring.java.modern.chapter1.Apple;
import org.springframework.stereotype.Component;

@Component
public interface ApplePredicate {
  boolean test(Apple apple);
}
