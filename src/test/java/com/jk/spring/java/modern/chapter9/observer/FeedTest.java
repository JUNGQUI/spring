package com.jk.spring.java.modern.chapter9.observer;

import org.junit.jupiter.api.Test;
import org.apache.commons.lang3.StringUtils;

class FeedTest {
  @Test
  void feedTest() {
    String testTweet = "The queen said her favourite book is Modern Java in Action!";

    Feed f = new Feed();

    f.registerObserver(new NYTimes());
    f.registerObserver(new Guardian());
    f.registerObserver(new LeMonde());
    f.notifyObservers(testTweet);

    Feed forLambda = new Feed();
    forLambda.registerObserver((String tweet) -> {
      if (StringUtils.contains(tweet, "money")) {
        System.out.println("Breaking news in NY! " + tweet);
      }
    });
    forLambda.registerObserver((String tweet) -> {
      if (StringUtils.contains(tweet, "queen")) {
        System.out.println("Yet more new from London... " + tweet);
      }
    });
    forLambda.registerObserver((String tweet) -> {
      if (StringUtils.contains(tweet, "wine")) {
        System.out.println("Today chees, wine and news! " + tweet);
      }
    });
    forLambda.notifyObservers(testTweet);
  }
}