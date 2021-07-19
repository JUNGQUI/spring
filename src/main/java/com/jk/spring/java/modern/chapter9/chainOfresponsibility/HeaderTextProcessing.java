package com.jk.spring.java.modern.chapter9.chainOfresponsibility;

public class HeaderTextProcessing extends ProcessingObject<String> {

  @Override
  protected String handleWork(String text) {
    return "From Raoul, Mario and Alan: " + text;
  }
}
