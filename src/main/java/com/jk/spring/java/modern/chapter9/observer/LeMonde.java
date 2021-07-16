package com.jk.spring.java.modern.chapter9.observer;

import org.apache.commons.lang3.StringUtils;

public class LeMonde implements Observer {

  @Override
  public void notify(String tweet) {
    if (StringUtils.contains(tweet, "wine")) {
      System.out.println("Today chees, wine and news! " + tweet);
    }
  }
}
