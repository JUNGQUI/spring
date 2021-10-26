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

