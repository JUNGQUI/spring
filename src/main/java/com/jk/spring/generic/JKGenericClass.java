package com.jk.spring.generic;

public class JKGenericClass implements JKGenericInterface<Integer> {

  @Override
  public Integer someMethod(Integer integer) {
    return integer + 1;
  }
}
