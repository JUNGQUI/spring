package com.jk.spring.java.modern.chapter9.observer;

import org.apache.commons.lang3.StringUtils;

public class Guardian implements Observer {

  @Override
  public void notify(String tweet) {
    if (StringUtils.contains(tweet, "queen")) {
      System.out.println("Yet more new from London... " + tweet);
    }
  }
}
