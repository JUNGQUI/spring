package com.jk.spring.java.modern.chapter9;

import static java.util.stream.Collectors.groupingBy;

import com.jk.spring.java.modern.UtilClass;
import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter6.GroupingDish.CaloricLevel;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RefactoringTestingDebugging {
  @Test
  void lambdaExpressionTest() {
    Map<CaloricLevel, List<Dish>> caloricLevelListMap = UtilClass.immutableMenu.stream()
        .collect(groupingBy(Dish::getCaloricLevel));

    UtilClass.immutableMenu.forEach(d -> Assertions.assertTrue(caloricLevelListMap.get(d.getCaloricLevel()).contains(d)));
  }
}
