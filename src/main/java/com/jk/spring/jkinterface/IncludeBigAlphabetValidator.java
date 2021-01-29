package com.jk.spring.jkinterface;

import java.util.regex.Pattern;

public class IncludeBigAlphabetValidator implements NameValidator {

  @Override
  public void nameValidator(String name) {
    if (!Pattern.compile("[A-Z]").matcher(name).find()) {
      throw new NameValidatorException("대문자가 포함되어야 합니다.");
    }
  }
}
