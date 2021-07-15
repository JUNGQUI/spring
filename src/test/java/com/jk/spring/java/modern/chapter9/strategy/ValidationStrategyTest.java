package com.jk.spring.java.modern.chapter9.strategy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

class ValidationStrategyTest {

  @Test
  @Description("전략패턴 테스트")
  void strategyValidationTest() {
    Validator numericValidator = new Validator(new IsNumeric());
    Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
    Validator numericValidatorWithLambda = new Validator((String s) -> s.matches("\\d+"));
    Validator lowerCaseValidatorWithLambda = new Validator((String s) -> s.matches("[a-z]+"));

    String testCaseNumeric = "jklee1";
    String testCaseNumericFail = "jklee";
    String testCaseLowerCase = "jklee";
    String testCaseLowerCaseFail = "JKLEE";

    assertEquals(numericValidator.validate(testCaseNumeric), numericValidatorWithLambda.validate(testCaseNumeric));
    assertEquals(numericValidator.validate(testCaseNumericFail), numericValidatorWithLambda.validate(testCaseNumericFail));
    assertEquals(lowerCaseValidator.validate(testCaseLowerCase), lowerCaseValidatorWithLambda.validate(testCaseLowerCase));
    assertEquals(lowerCaseValidator.validate(testCaseLowerCaseFail), lowerCaseValidatorWithLambda.validate(testCaseLowerCaseFail));
  }
}