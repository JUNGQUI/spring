package com.jk.spring.jkinterface;

import java.util.regex.Pattern;

public class IncludeSmallAlphabetValidator implements NameValidator {

  @Override
  public void nameValidator(String name) {
    if (!Pattern.compile("[a-z]").matcher(name).find()) {
      throw new NameValidatorException("소문자가 포함되어야 합니다.");
    }
  }
}
