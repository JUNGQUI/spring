### Reactive Programming

자바 8 에서부터 병렬 스트림 형식으로 스레드에 비해 단순하고 효과적으로 병렬처리를 실행 할 수 있게 변경되었다.

바로 Stream API 중 Parallel Stream API 가 그것인데, 단순히 메서드 하나를 통해 병렬성을 이전과 다르게 쉽게 확보가 가능하다.

이러한 특성과 애플리케이션의 생태계의 변화에 힘입어 Reactive Programming 개념이 대두되기 시작했다.

> #### 변화된 애플리케이션 생태계
> 최근 애플리케이션에서는 단독 애플리케이션 형태로 모든 작업을 하는 경우는 별로 없다. 내부 서비스라 하더라도 해당하는 API 를 여러 서비스가
> 제공해주고 이런 결과를 합쳐서 다시 하나의 단일 서비스로써 제공해주는 경우가 많은데, 이러한 부분이 마이크로 아키텍쳐에 영향을 받았다고
> 볼 수 있다.
> 
> 이처럼 최근 애플리케이션의 생태계가 좀 더 작고 모듈화된, 쪼개져있는 형태를 띄고 있고 지향하고 있다고 볼 수 있다.

변화된 애플리케이션 생태계에 맞춰 여러 웹서비스에서 결과를 받기 전까지 요청한 클라이언트 서비스의 경우 작업이 없어 놀게 되는데 이 때
병렬로써 다른 데이터를 처리하게 되면 CPU 가 낭비되지 않고 애플리케이션의 생산성을 극대화 할 수 있다.

이런 환경에서 자바는 `CompletableFuture` 를 통해 병렬성 처리를 원할히 할 수 있게 제시하고 있다.

### Executor 와 스레드 풀

스레드풀에서 풀은 pool 이라는 의미로 말 그대로 스레드가 모여있는 개념이라고 생각하면 되는데, 쉽게 생각해서 특정 개수만큼의 스레드가
항상 만들어져있는 상태에서 대기하고 큐에 들어오는 순서에 따라 스레드가 태스크를 하나씩 할당받는 것이라고 볼 수 있다.

이렇게 하는 것이 좋은 이유는 스레드를 만드는 비용이 만만치 않기 때문에 요청이 올때마다 스레드를 만드는 것은 비효율적이며 다 사용한
스레드를 없애는게 아닌 풀에 유지한 상태로 다음 태스크를 받아서 수행할 수 있게 하는 것이 재사용성 측면에서 우수하기 때문이다.

Executor 는 이러한 스레드풀을 이용한 프레임워크로 간단한 메서드를 통해 join 등의 연산으로 스레드 결과를 합치는 등 더 유용하게 사용이
가능하다.

이렇게 좋은 스레드 풀에도 단점이 있는데

1. 개수 제한

스레드풀을 설정 할 때 초기에 스레드 개수를 정하게 된다. 이는 크게 이상이 없는데 문제는 스레드 풀을 넘는 요청이 들어왔을 때 수행할 경우
이전 태스크가 끝나기 전까지 다른 태스크는 대기상태로 들어간다. 그런데 인터넷 상에서 타임아웃이 정해져있는 요청이나 I/O 를 기다리는 태스크가
있을 경우 진행되지도 않고 타임아웃 예외가 발생하거나 I/O 를 기다리기 위해 많은 시간을 소비 할 수 있다.

2. 프로세스 유지

스레드로 돌리기 시작하면 가장 깜빡하는 부분이 스레드 완료 전 메인 스레드의 종료이다. 병렬로 돌아가는 작업을 진행하는 도중 join 으로 기다리지
않거나 무시하고 메인 스레드가 종료하게 되면 작업을 진행하던 다른 스레드는 결과를 주지 못하고 종료되게 된다.

위와 같은 부분에 대해서 주의해야 한다.

스레드를 이용한 작업은 다음과 같이 만들 수 있다. `f(x)` 와 `g(x)` 가 엄청난 시간을 잡아먹는 함수라고 가정 할 때

```java
class ThreadExample {
  public static void main(String[] args) throws InterruptedException {
    int x = 1337;
    Result result = new Result();
    
    Thread t1 = new Thread(() -> result.left = f(x));
    Thread t2 = new Thread(() -> result.left = g(x));
    t1.start();
    t2.start();
    t1.join();
    t2.join();
    System.out.println(result.left + result.right);
  }
  
  private static class Result {
    private int left;
    private int right;
  }
}
```

이와 같이 표현이 가능하며 `ExecutorService` 를 이용하면

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceExample {

  public static void main(String[] args) throws ExecutionException, InterruptedException {
    int x = 1337;

    ExecutorService executorService = Executors.newFixedThreadPool(2);
    Future<Integer> left = executorService.submit(() -> f(x));
    Future<Integer> right = executorService.submit(() -> g(x));
    System.out.println(left.get() + right.get());
    
    executorService.shutdown();
  }
}
```

이와 같이 `Future` 를 이용해서 비동기 형식으로 돌리고 `get()` 을 이용해서 join 을 진행하면 간편하게 비동기 API 로 진행할 수 있다.

### 리액티브 형식 API

쉽게 생각하면 리액티브한 형식의 API 는 파라미터로 콜백을 받는다. 이렇게 받은 콜백 형식의 함수는 내부에서 실행을 하던 중 결과가 준비되면 람다로
이 콜백 파라미터를 실행해서 join 을 통해 값을 합친다.

```java
public class CallbackStyleExample {
  public static void main(String args[]) {
    int x = 1337;
    Result result = new Result();

    f(x, (int y) -> {
      result.setLeft(y);
      System.out.println(result.getLeft() + result.getRight());
    });

    g(x, (int y) -> {
      result.setRight(y);
      System.out.println(result.getLeft() + result.getRight());
    });
  }

  static void f(int x, IntConsumer dealWithResult) {

  }

  static void g(int x, IntConsumer dealWithResult) {

  }
}
```

위와 같은 형식으로 진행을 할 수 있다. 그러나 문제점이 있는데 결과가 수시로 변경된다.
그 이유로는 락이 걸려있지 않기 때문에 result 에 left 만 할당된 상태로 출력될수도, right 만 할당된 상태로 출력될수도,
둘 다 할당되지 않은 상태에서 출력이 될 수도 있다.

이러한 부분은

1. if-then-else 형식으로 확인 작업(락) 을 진행한 후 호출
2. Future 를 이용해서 처리

와 같은 방법으로 해결 할 수 있다.

### Thread Sleep

락과 관련되서 가장 간단하게 순서를 정할 수 있는게 스레드를 임의로 멈추는 thread.sleep() 이 바로 그것이다.

그렇다고 이 방법이 좋은가 에 대해서는 단연코 아니다 라고 할 수 있다.

```java
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DoSomething {

  public void someMethod() {
    work1();
    Thread.sleep(10000);
    work2();
  }
}

public class DoSomethingWithExecutor {

  public void someMethodWithExecutor() {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    work1();
    scheduledExecutorService.schedule(DoSomethingWithExecutor::work2, 10, TimeUnit.SECONDS);
  }
  
  public static void work1() {
    System.out.println("work 1");
  }
  
  public static void work2() {
    System.out.println("work 2");
  }
}
```
스레드풀에서 작업을 한다고 가정했을 때 `DoSomething` 는 work1 을 할당을 받긴 했지만 Thread.sleep() 으로 인해 멈춘 상태에서
시간이 지난 후 풀리자마자 work2 를 실행하고 종료한다. 이렇게 될 경우 work2 가 먼저 끝난다면 work1 은 정상적으로 처리 되지 않고 종료된다.

하지만 `DoSomethingWithExecutor` 의 경우 work1 이 실행 된 후 스케쥴러에 work2 를 10초 뒤에 실행하게끔 큐에 추가한 뒤
종료한다. 이렇게 할 경우 work1 이 무사히 완료됨은 물론이고 메인 스레드가 종료된 후에도 work2 는 작업을 계속 할 수 있다.

### 예외 처리

Reactive 형식으로 코드를 짜게 되면 에러나 예외를 처리하는 부분도 일반적인 함수들과는 조금 다르다. 결과 자체도 콜백으로 받고 있기 때문에 예외 또한 마찬가지로 콜백 형식으로 받아서 처리하게 된다.

```java
public class ReactiveTest {
  void f(int x, Consumer<Integer> dealWithResult, Consumer<Throwable> dealWithException);
}
```

예시를 보면 초기값 x, 값과 같이 수행할 로직 dealWithResult, 그리고 예외를 받았을때 처리하는 dealWithException 콜백이 존재한다.

이런 식으로 예외에 대해 이벤트를 발생 시켰을 때 실행할 콜백을 넣어주면 자연스럽게 콜백에서 처리를 하게 된다.

### 박스와 채널 모델

동시성 모델을 가장 잘 설계하고 개념화하는 기법이 박스와 채널 모델인데 예시는 다음과 같다.

`x -> r(q1(p(x)), q2(p(x)))`

박스와 채널 모델에선 각 함수들을 하나의 박스로 표현하고 전체 로직 수행을 하나의 채널로 표현하다.
수식만을 본다면 이게 무슨 괴랄한 모양인지 이해하기 어렵지만 x 가 주어지고 먼저 `함수 p` 를 거치고 해당 결과 값을 
각각 함수 `q1` 과 `q2` 의 인자로 주고, 이런 인자를 받아서 최종적으로 함수 `r` 에 줘서 결과값을 리턴하는 케이스다.

가장 간단한 구현식은 다음과 같다.

```java
public class Test {
  public void test1(int x) {
    int t = p(x);
    System.out.println(r(q1(t), q2(t)));
  }
}
```

하지만 병렬적으로 처리하고자 하는 목적에 부합하지 않기에, 각 함수 `q1`, `q2` 를 future 로 감싸면 아래와 같은 구조가 된다.

```java
import java.util.concurrent.ExecutorService;

public class Test {

  ExecutorService executorService;

  public void test1(int x) {
    int t = p(x);
    Future<Integer> a1 = executorService.submit(() -> q1(t));
    Future<Integer> a2 = executorService.submit(() -> q2(t));
    System.out.println(r(a1.get(), a2.get()));
  }
}
```

하지만 이 또한 문제점이 있는데, `r` 의 경우 비동기가 아닌 동기처리이며 병렬성을 극대화 하기 위해서 전체 코드를 모두 future 로 감싸야 한다.

위와 같은 구조도 구동은 되지만 박스와 채널이 점점 많아지고 코드가 방대해질수록 많은 태스크가 get() 메서드를 호출해 future 가 끝나기만을
바라게 될 수 있다.

그래서 사용하는게 `CompletableFuture` 에서의 콤비네이터 기능이다.

### CompletableFuture, 콤비네이터

Future 와 CompletableFuture 의 차이점부터 먼저 알아보자.

우선 Future 는 앞서 봤듯이 콜백을 주고 추후에 `get()` 을 통해 연산을 진행하여 결과를 전달 받는다. 다만 여기서 문제가 생기는데
Future 의 경우 새로운 생성자를 통해 생성을 하면서 연산과정을 나중으로 미룰 수 없다.

하지만 CompletableFuture 의 경우는 조금 다르다. 아래 코드를 살펴보자.

```java
public class CFComplete {
  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    
    int x = 1337;
    
    CompletableFuture<Integer> a = new CompletableFuture<>();
    executorService.submit(() -> a.complete(f(x)));

    // Future 는 구현을 해야 한다는 점과 비교해보면 매우 대조적이다.
//    Future<Integer> b = new Future<Integer>() {
//      @Override
//      public boolean cancel(boolean b) {
//        return false;
//      }
//
//      @Override
//      public boolean isCancelled() {
//        return false;
//      }
//
//      @Override
//      public boolean isDone() {
//        return false;
//      }
//
//      @Override
//      public Integer get() throws InterruptedException, ExecutionException {
//        return null;
//      }
//
//      @Override
//      public Integer get(long l, TimeUnit timeUnit)
//          throws InterruptedException, ExecutionException, TimeoutException {
//        return null;
//      }
//    };
    
    int b = g(x);
    
    System.out.println(a.get() + b);
    
    executorService.shutdown();
  }
}
```

위 예시 코드를 보자면 CompletableFuture 의 경우 아무런 callback 을 전달 받지 않고 생성자를 통해 생성해두되, 추후 다른 함수를
직접 CompletableFuture 에 할당해줌으로써 유동적으로 구현할 수 있다.

이로써 좀 더 유동적으로 사용이 가능하고 CompletableFuture 에 어떤 함수를 매핑해주느냐에 따라 결과를 다르게 가져갈 수 있기에 재사용
측면에서 유리하다. 이러한 특성 때문에 이름 또한 'Completable' '완성 가능한' 이라는 이름이 붙게 되었다.

### combination

CompletableFuture (이하 CF) 에 다른 기능 또한 하나 더 있는데 바로 조합이다.

`CompletableFuture<V> thenCombine(CompletableFuture<U> other, BiFunction<T, U, V> fn)` 이 수식을 보면
다른 CF 와 새로운 함수를 조합해서 사용하는 기능이다. 이와 같은 유사한 기능이 stream API 에도 있는데 

```text
myStream.map(...)
        .filter(...)
        .sum()
```

이와 같이 하나의 stream 에 대해 map 을 통해 특정한 연산을 거치고 filter 를 통해 특정 값들은 걸래내며, 나머지 값들의 합을 구하는 연산을
각 연산을 조합해서 하나로 만들어서 표현한 것이다.

그래서 위의 `CFComplete` 클래스에 새로운 CF 인자 c 를 추가하고 thenComplete 를 사용해서 조합을 하면

```java
import java.util.concurrent.CompletableFuture;

public class CFCombine {

  public static void main(String[] args) {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    int x = 1337;

    CompletableFuture<Integer> a = new CompletableFuture<>();
    CompletableFuture<Integer> b = new CompletableFuture<>();
    CompletableFuture<Integer> c = a.thenCombine(b, Integer::sum);
    executorService.submit(() -> a.complete(f(x)));
    executorService.submit(() -> b.complete(g(x)));

    System.out.println(c.get());
    executorService.shutdown();
  }
}
```

이와 같은 구조가 된다. a, b 는 일반적인 CF 니 넘어가고 c 를 살펴보면 a 와 b 를 조합해서 결과를 받을건데, 각 연산의 결과를 sum 하겠다는
의미이다. 즉, 각 future 는 리턴받는 값을 BiFunction 의 인자로 넣어서 결과를 받고 C 로 반환한다는 의미이다.

결과적으로 일반적인 Future 와 다른 점은 c의 경우 a, b 가 둘 다 끝난 상태가 되었을 경우 스레드 풀에서 연산을 만든다. 

Future.get() 과 다른 점이 바로 이 부분인데 c 가 먼저 시작되고 블럭이 되는 것이 아니라 시작은 먼저 할 지라도 블록은 되지 않는다는 것이다.

일반적으로 get() 을 수행해도 그렇게 큰 리소스 낭비가 일어나진 않지만 이러한 방법이 병렬 실행의 효율성은 높이고 데드락은 피하는 최상의 해결책을
자연스럽게 구현할 수 있고 java 에서 이를 제공해준다는 점이 가장 큰 이점이라 할 수 있다.

### publish - subscribe, Reactive

발행 - 구독 모델의 경우 클라이언트가 서버에 값을 요청해서 받아가는 일반적인 상황이 아닌, 특정한 타스크를 등록하고 (발행) 이를 받기를 기다리는
클라이언트가 타스크가 완료되면 결과를 받아가는 모델 (구독) 이라고 볼 수 있다.

이 Future 와 CompletableFuture 를 통해 리액티브 프로그래밍을 할 수 있는데, 이를 살펴볼 예정이다.

```java
private class SimpleCell {
  private int value = 0;
  private  String name;
  public SimpleCell(String name) {
    this.name = name;
  }
}
```

위 코드는 엑셀에서 셀에 값을 부여하는 수식을 표현한 것이다. 심플하고 셀 자체에 값을 부여하는 목적에 부합한다. 그런데 엑셀에서는 수식을
통해 셀의 값을 더하는 등의 로직을 수행 할 수 있다.

문제는 언젠가 발생하는 그 때에 실행될게 아니라 언제일지 모르는 상황에서 '값이 바뀌었다' 는 상황이 되었을때 두 값을 더하는 수식이 발생해야 한다고
가정을 하면 이벤트가 발생했을 때 수식을 실행해야 한다.

```java
interface Publisher<T> {
  void subscribe(Subscriber<? super T> subscriber);
}
```

이럴 때 publisher 와 subscriber 를 이용해서 메서드를 구현 할 수 있다.

```java
private class SimpleCell implements Publisher<Integer>
```