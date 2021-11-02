### 안정적 비동기 프로그래밍

우선 Future 를 다시 살펴 보자

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class JKFuture {

  public void futureTest() {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Future<Double> future = executorService.submit(new Callable<Double>() {
      public Double call() {
        return doSomeLongComputation();
      }
    });
    doSomethingElse();
    try {
      Double result = future.get(1, TimeUnit.SECONDS);
    } catch (ExecutionException ee) {
      // 계산 중 예외 발생
    } catch (InterruptedException ie) {
      // 인터럽트 예외
    } catch (TimeoutException te) {
      // 타임아웃
    }
  }

  private void doSomethingElse() {
    // ...
    // do something other 
    // ...
  }

  private Double doSomeLongComputation() {
    // ...
    // long task
    // ...
  }
}
```

이 부분을 보면 저수준의 스레드에 비해 직관적으로 이해하기 쉽고 구현하기도 쉽다. executors 를 통해 스레드를 제공받고 해당 스레드에 대해
get() 메서드를 통해 결과를 받을수도 있고 비동기로 실행 시키며 너무 오래 걸리면 타임아웃을 통해 튕겨낼 수 있다.

### Future 의 한계

위 함수에서는 겉보기에 큰 문제는 없는데 가장 큰 문제가 있다. 그것은 바로 get 을 통해 언제 끝나는지 장담을 할 수 없다는 것이다.
예를 들어 `doSomeLongComputation` 이 워낙 오래 걸려 작업이 끝나지 않을 수 있다.

이러한 부분을 보완하기 위해 future 인터페이스는 아래와 같이 구현되어 있다.

```java
public interface Future<V> {
  boolean cancel(boolean var1);

  boolean isCancelled();

  boolean isDone();

  V get() throws InterruptedException, ExecutionException;

  V get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException;
}
```

isDone 메서드를 통해 계산이 끝났는지 확인 할 수 있고 cancel 을 통해 취소 시킬수 있다. 그리고 위에서 사용한 것과 같이 get()에 
시간을 전달해서 정해진 시간이 지나도록 결과를 받지 못하면 타임아웃을 발생시킬 수도 있다.

하지만 이것만으로 간결하게 동시 실행 코드를 구현하기 힘들다. 예를 들어 '오래 걸리는 A 가 끝나면 그 결과를 오래 걸리는 B 에 파라미터로 전달
하시오. 그리고 그 결과가 나오면 다른 값이 파라미터로 전달된 또다른 B 의 결과와 조합하시오.' 와 같이 의존적인 구현방식은 사용하기 어렵다.

이런 유동적인 상황에 맞으려면 다음과 같은 조건이 필요하다.

- 서로 다른 Future 를 조합 할 수 있어야 한다.
- Future 가 진행되는 동안 모든 태스크의 완료를 기다린다.
- 가장 빨리 완료되는 태스크가 있다면 그 결과를 얻는다.
- 프로그램적으로 Future 를 완료시킨다.
- Future 완료 동작에 반응한다.

앞 챕터에서도 이야기 했지만 이러한 기능을 보완, 제공해주는게 CompletableFuture 다.

### CompletableFuture 로 비동기 구현

비동기 애플리케이션을 구현할텐데, 최저가 검색 애플리케이션을 구현할 것이다.

```java
public class Shop {
  private static final Random randomGenerator = new Random();
  
  public double getPrice(String product) {
    return calculatePrice(product);
  }

  public static void delay() {
    try {
      Thread.sleep(1000L);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }

  private double calculatePrice(String product) {
    delay();
    return randomGenerator.nextDouble() * product.charAt(0) + product.charAt(1);
  }
}
```

상점에서 코드를 통해 가격을 가져오는 부분은 이와 같이 구현했는데, 이해를 쉽게 하기 위해 delay 메서드를 써서 일정 시간이 소모 된다고
가정했고, 동기 방식이기 때문에 sleep 을 통해 블록시켰다.

이를 비동기로 바꾸려면 우선 이렇게 구현이 되야 할 것이다.

```java
import java.util.concurrent.CompletableFuture;

public class Shop {

  public Future<Double> getPriceAsync(String product) {
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();
    
    new Thread(() -> {
      double price = calculatePrice(product);
      futurePrice.complete(price);
    }).start();
    
    return futurePrice;
  }
}
```

달라진 점은 스레드를 새로 만들어서 동기 작업이 필요한 부분을 감싸고 그 결과를 CompletableFuture 내에 심어놨다는 것이다.

이렇게 구현 할 경우 스레드를 실행 하기 전 별도의 작업을 돌리는 로직을 수행 할 수 있고 추후에 해당 값이 진짜 필요하거나 상대적으로
크게 바쁘지 않은 시기에 실제 값을 가져오는 로직을 수행해 비동기적으로 처리 할 수 있다.

물론 이렇게 쓰는것이 아니라 더 완벽하게 비동기로 블록을 피하며 사용하는 방법 또한 있지만 현재는 '이런 방식' 이다 라는 것만 인지하고
넘어가고 추후에 해당 내용에 대해 자세하게 파볼것이다.