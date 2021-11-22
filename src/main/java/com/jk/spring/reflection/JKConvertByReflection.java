package com.jk.spring.reflection;

public class JKConvertByReflection {
  public String convertByReflection(Object someObject) {
    return someObject.getClass().getName();
  }
}
