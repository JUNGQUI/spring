package com.jk.spring.jkinterface;

public class OverNLengthValidator implements NameValidator {

  private final Integer nameLength;

  public OverNLengthValidator(Integer nameLength) {
    this.nameLength = nameLength;
  }

  @Override
  public void nameValidator(String name) {
    if (name.length() < nameLength) {
      throw new NameValidatorException("최소 " + nameLength + " 자 이상이여야 합니다.");
    }
  }
}
