package com.jk.spring.reflection;

public class CarReflection {
  public String convertByReflection(Object someObject) {
    if (someObject.getClass().getName().equals("com.jk.spring.reflection.Car")) {
      return "com.jk.spring.reflection.Car";
    }

    return someObject.getClass().getName();
  }
}
