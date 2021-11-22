package com.jk.spring.reflection;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CarReflectionTest {
  @Test
  void reflectionTest() {
    CarReflection carReflection = new CarReflection();
    Car car = new Car();
    String someString = "";
    int integer = 1;
    Integer wrapperInteger = 1;

    assertEquals("com.jk.spring.reflection.Car", carReflection.convertByReflection(car));
    assertEquals("java.lang.String", carReflection.convertByReflection(someString));
    assertEquals("java.lang.Integer", carReflection.convertByReflection(integer));
    assertEquals("java.lang.Integer", carReflection.convertByReflection(wrapperInteger));
  }
}