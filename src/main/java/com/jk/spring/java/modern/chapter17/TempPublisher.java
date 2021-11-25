package com.jk.spring.java.modern.chapter17;

import java.util.concurrent.Flow.Publisher;

public class TempPublisher {

  public static void pub(String town) {
    getTemperatures(town).subscribe(new TempSubscriber());
  }

  private static Publisher<TempInfo> getTemperatures(String town) {
    return subscriber -> subscriber.onSubscribe(new TempSubscription(subscriber, town));
  }
}
