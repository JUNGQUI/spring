package com.jk.spring.java.modern.chapter1;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ComparisonApple {
  public void compareBeforeLambda(List<Apple> needCompare) {
    Collections.sort(needCompare, new Comparator<Apple>() {
      @Override
      public int compare(Apple o1, Apple o2) {
        return Integer.compare(o1.getWeight(), o2.getWeight());
      }
    });
  }

  public void compareAfterLambda(List<Apple> needCompare) {
    needCompare.sort(Comparator.comparingInt(Apple::getWeight));
  }
}
