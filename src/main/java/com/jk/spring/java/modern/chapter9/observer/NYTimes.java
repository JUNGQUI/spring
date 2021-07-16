package com.jk.spring.java.modern.chapter9.observer;

import org.apache.commons.lang3.StringUtils;

public class NYTimes implements Observer {

  @Override
  public void notify(String tweet) {
    if (StringUtils.contains(tweet, "money")) {
      System.out.println("Breaking new in NY! " + tweet);
    }
  }
}
