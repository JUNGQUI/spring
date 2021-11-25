package com.jk.spring.java.modern.chapter17;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TempSubscription implements Subscription {

  private final Subscriber<? super TempInfo> subscriber;
  private final String town;

  private static final ExecutorService executor = Executors.newSingleThreadExecutor();

  @Override
  public void request(long l) {
    executor.submit(() -> {
      for(long i = 0; i < l; i++) {
        try {
          subscriber.onNext(TempInfo.fetch(town));
        } catch (Exception ex) {
          subscriber.onError(ex);
          break;
        }
      }
    });
  }

  @Override
  public void cancel() {
    subscriber.onComplete();
  }
}
