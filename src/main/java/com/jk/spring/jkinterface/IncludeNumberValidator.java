package com.jk.spring.jkinterface;

import java.util.regex.Pattern;

public class IncludeNumberValidator implements NameValidator {

  @Override
  public void nameValidator(String name) {
    if (!Pattern.compile("[0-9]").matcher(name).find()) {
      throw new NameValidatorException("숫자가 포함되어야 합니다.");
    }
  }
}
