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

### 에러 핸들링

비동기라고 크게 다르지 않다. 에러가 날만한 부분을 감싸서 처리해주면 되기에 간단하다.

```java
import java.util.concurrent.CompletableFuture;

public class Shop {

  public Future<Double> getPriceAsync(String product) {
    CompletableFuture<Double> futurePrice = new CompletableFuture<>();

    new Thread(() -> {
      try {
        double price = calculatePrice(product);
        futurePrice.complete(price);
      } catch (Exception ex) {
        futurePrice.completeExceptionally(ex);
      }
    }).start();

    return futurePrice;
  }
}
```

다만 조금 다르다면 future 자체를 반환하기 때문에 해당 future 를 이용한 에러 핸들링이 필요하다. 이 클라이언트에서 에러가 발생하게 되면
일반적인 RuntimeException 이 발생하게 된다. 동기식에서 throw 와 동일한 느낌으로 클라이언트에게 값이 전달되기에 문법(?)이 다르다고
이상하게 생각하지 말자.

위의 `CompletableFuture` 의 경우 생성자를 통해 새로운 값을 할당했지만 좀 더 스마트하게 하는 방법이 있다.

```java
public class Shop {
  public Future<Double> getPriceAsync(String product) {
    return CompletableFuture.supplyAsync(() -> calculatePrice(product));
  }
}
```

`supplyAsync` 의 경우 첫 인자로 supplier, 두번째 인자로 스레드를 받는데 스레드는 옵셔널이다. supplier 로 처리하면 내부에
새로운 함수를 람다식으로 표현할 수 있는데 이를 통해 굳이 스레드를 새로 생성하고 내부에 구현을 하는것 보다 훨씬 쉽게 처리가 가능하다.

또한 안에 함수형으로 구현을 하기 때문에 당연하게도 내부에서 에러 핸들링도 가능하고 위에서 작성한 에러 핸들링 방법을 그대로 사용 할 수 있다.

### 비블록 코드

`Shop` 생성자를 통해 여러 상점을 생성했다고 가정하고 이를 통해 상점이름과 해당 상점의 상품 가격을 반환하는 메서드를 구현한다고 해보자.

```java
public class ShopMethod {
  public List<String> findPrices(String product) {
    return shops.stream()
        .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
        .collect(toList());
  }
}
```

해당 코드를 이용해 로직을 수행하면 대략 4초 이후가 나오는데, 이는 `getPrice` 내에 있는 delay 1초로 인해서다.

우리가 원했던 그림은 모든 상점에 대해 각각의 스레드가 병렬로 처리가 되면서 동시에 결과가 나오는 (동일한 로직을 동시에 돌렸으니) 멋진 그림인데
결과가 우리의 예상과는 맞지 않는데, 이를 병렬화 하는 작업을 진행 할 것이다.

```java
public class ShopMethod {
  public static List<String> findPricesParallels(List<Shop> shops, String product) {
    List<CompletableFuture<String>> completableFutureList = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(
            () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
        ))
        .collect(toList());

    return completableFutureList.stream()
        .map(CompletableFuture::join)
        .collect(toList());
  }
}
```

> 주의
> 
> 코드를 한번에 작성하기 위해 중간에 한번 끊고 진행하지 않을 경우 하나의 파이프라인으로 연결된 것으로 간주하여 여전히 동기식 처리하게 된다.
> 
> ```java
> public class FaultMethod {
> public static List<String> findPricesParallels(List<Shop> shops, String product) {
>     return shops.stream()
>                 .map(shop -> CompletableFuture.supplyAsync(
>                   () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
>                 ))  // 여기에서 Stream<CompletableFuture<String>> 상태이지만
>                 .map(CompletableFuture::join) // 추가로 stream 으로 파이프라인이 새로 구축되지 않았기에 비동기 처리
>                 .collect(toList());
>   }
> }
> ```
> 
> 이는 break point 를 걸고 확인해보면 더 쉽게 볼 수 있는데, CompletableFuture 로 감싼 부분에서 join 으로 넘어가는 순간
> 딜레이에 순차로 빠지게 되지만 별도로 Future List 를 생성 한 뒤 join 을 통해 수행 할 경우 비동기 작업이 진행 된 후 join 으로
> 완료되기 전까지 대기하게 된다.

