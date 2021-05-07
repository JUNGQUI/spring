package com.jk.spring.generic;

public class JKGenericBiClass implements JKGenericBiInterface<String, Integer> {

  @Override
  public String firstMethod(String s) {
    return "called by firstMethod " + s;
  }

  @Override
  public Integer secondMethod(Integer integer) {
    return integer + 2;
  }
}
