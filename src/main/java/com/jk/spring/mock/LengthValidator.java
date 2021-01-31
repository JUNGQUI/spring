package com.jk.spring.mock;

public class LengthValidator implements Validator {

    public Integer limitLength;

    public LengthValidator(Integer length) {
        this.limitLength = length;
    }

    @Override
    public void validator(String targetObject) {
        if (targetObject.length() > limitLength) {
            throw new RuntimeException("길이 준수");
        }
    }
}
