package com.jk.spring.java.modern.chapter3;

import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UseConstructorMethodReference {
  public void test() {
    Supplier<Apple> sample = Apple::new;
    Apple sampleApple = sample.get();

    BiFunction<Integer, Color, Apple> appleConstructor = Apple::new;
    Apple apple = appleConstructor.apply(150, Color.GREEN);
  }
}
