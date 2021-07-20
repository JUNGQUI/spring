package com.jk.spring.java.modern.chapter9.chainOfresponsibility;

public class SpellCheckerProcessing extends ProcessingObject<String> {

  @Override
  protected String handleWork(String text) {
    return text.replace("labda", "lambda");
  }
}
