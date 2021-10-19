package com.jk.spring.java.modern.chapter15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class SimpleCell implements Publisher<Integer>, Subscriber<Integer> {
  private int value = 0;
  private String name;
  private List<Subscriber> subscribers = new ArrayList<>();

  @Override
  public void subscribe(Subscriber<? super Integer> subscriber) {

  }

  @Override
  public void onSubscribe(Subscription subscription) {

  }

  @Override
  public void onNext(Integer integer) {

  }

  @Override
  public void onError(Throwable throwable) {

  }

  @Override
  public void onComplete() {

  }
}
