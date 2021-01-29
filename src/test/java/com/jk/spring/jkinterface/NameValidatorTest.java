package com.jk.spring.jkinterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.context.annotation.Description;

public class NameValidatorTest {

  static IncludeBigAlphabetValidator bigValidator = new IncludeBigAlphabetValidator();
  static IncludeSmallAlphabetValidator smallValidator = new IncludeSmallAlphabetValidator();
  static IncludeNumberValidator numberValidator = new IncludeNumberValidator();
  static OverNLengthValidator lengthValidator;
  static List<NameValidator> validatorList = new ArrayList<>();

  @Before
  public void setup() {
    lengthValidator = new OverNLengthValidator(5);

    validatorList.addAll(
        Arrays.asList(
            bigValidator
            , smallValidator
            , numberValidator
            , lengthValidator
        )
    );
  }

  @Test
  public void validatorTest() {
    String name = "jkleeJKLEE12345";
    validatorList.forEach(nameValidator -> nameValidator.nameValidator(name));
  }

  @Test
  @Description("이름에 소문자가 있는지 확인 정상")
  public void smallValidatorSuccess() {
    String name = "jklee";
    smallValidator.nameValidator(name);
  }

  @Test
  @Description("이름에 소문자가 있는지 확인 비정상")
  public void smallValidatorFailure() {
    String name = "JKLEE";
    Assertions.assertThrows(NameValidatorException.class, () -> smallValidator.nameValidator(name));
  }

  @Test
  @Description("이름에 대문자가 있는지 확인 정상")
  public void bigValidatorSuccess() {
    String name = "jkleeJKLEE12345";
    bigValidator.nameValidator(name);
  }

  @Test
  @Description("이름에 대문자가 있는지 확인 비정상")
  public void bigValidatorFailure() {
    String name = "jklee";
    Assertions.assertThrows(NameValidatorException.class, () -> bigValidator.nameValidator(name));
  }

  @Test
  @Description("이름에 숫자가 있는지 확인 정상")
  public void numberValidatorSuccess() {
    String name = "jkleeJKLEE12345";
    numberValidator.nameValidator(name);
  }

  @Test
  @Description("이름에 숫자가 있는지 확인 비정상")
  public void numberValidatorFailure() {
    String name = "ljk_BORISU";
    Assertions.assertThrows(NameValidatorException.class, () -> numberValidator.nameValidator(name));
  }

  @Test
  @Description("이름이 NLength 만큼 있는지 확인 정상")
  public void lengthValidatorSuccess() {
    String name = "jkleeJKLEE12345";
    lengthValidator.nameValidator(name);
  }

  @Test
  @Description("이름이 NLength 만큼 있는지 확인 비정상")
  public void lengthValidatorFailure() {
    String name = "5글자내";
    Assertions.assertThrows(NameValidatorException.class, () -> lengthValidator.nameValidator(name));
  }

}