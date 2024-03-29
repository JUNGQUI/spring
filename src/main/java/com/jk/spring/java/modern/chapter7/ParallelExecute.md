### 병렬 데이터 처리와 성능

자바7 이전의 병렬 처리의 경우 우리가 흔히 아는 문제점들이 발생했다.

1. 작업을 적절한 단위로 분리
2. 분리된 작업에 대해 각 스레드 할당(및 처리)
3. race condition 이 일어나지 않게 동기화
4. 최종 결과 합산

자바7에서부터는 포크/조인 프레임워크를 통해 이러한 병렬 처리 작업에 대해 훨씬 편리해졌고, 이를 이용해서 스트림에서도 간단한 메서드 추가를
통해 병렬 처리가 더욱 쉬워졌다.

### 병렬 스트림

1~n 까지의 합을 스트림을 통해 구현한다고 가정해보자.

```java
import java.util.stream.Stream;

@UtilityClass
public class ParallelStream {

  public long nonParallelSum(long n) {
    long start = System.currentTimeMillis();
    long result = Stream.iterate(1L, n)
        .limit(n)
        .reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - start);
    return result;
  }

  public long parallelsSum(long n) {
    long start = System.currentTimeMillis();
    long result = Stream.iterate(1L, n)
        .limit(n)
        .parallel()
        .reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - start);
    return result;
  }
}
```

그럼 위와 같이 구현이 가능할 것이다. 그런데 n 이 하염없이 큰 수가 된다면 당연히 병렬로 처리하는것이 더 빠를것이고
병렬처리를 위해 `parallel()` 메서드를 통해 스트림을 병렬로 처리할 수 있다.

하지만 실제 처리 시 병렬 처리쪽이 더 느리게 작동하게 된다.

그 이유는 사실 별게 아니고, 로직 자체가 병렬로 처리하기가 난해한 점이 있다는 것이다.

`iterate()` 메서드는 기본적으로 순차적으로 처리되게 되어있다. 결과적으로 1부터 n 까지 스트림을 연속적으로 생성하게 되는데 이를 병렬로
처리하기가 어렵기 때문이다.

하지만 우리는 `parallel()` 메서드로 병렬을 요청했으니 스트림 연산을 수행할 때 불필요하게 스레드만 할당해주는 오버헤드가 발생하고
이때문에 `parallel()` 을 붙히지 않은 순차 메서드가 더 빠르게 실행되는 것이다.

또한 부가적인 이슈로 전체를 병렬로 처리를 하더라도 박싱/언박싱의 문제가 발생한다. 전체를 순차로 처리 할 경우 전체 스트림에 대해 언박싱 후
작업을 진행하면 수월한 반면 각 스레드별로 할당되는 병렬처리의 경우 각 병렬처리별로 박싱/언박싱이 필요하게 되어 이 또한 오버헤드를 증가시키는 이슈가 된다.

따라서 아래와 같이 수행하게 변경해야 한다.

```java
@UtilityClass
public class ParallelStream {
  public long nonParallelSum(long n) {
    long start = System.currentTimeMillis();
    long result = LongStream.rangeClosed(1, n)
        .reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - start);
    return result;
  }

  public long parallelsSum(long n) {
    long start = System.currentTimeMillis();
    long result = LongStream.rangeClosed(1, n)
        .parallel()
        .reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - start);
    return result;
  }
}
```

`LongStream()` 을 통해 언박싱을 수행하지 않게 변경하였으며, `rangeClosed()` 를 통해 값 제공을 boundary 로 제공하게 하였다.

`rangeClosed()` 의 경우 1 ~ n 까지 1~5, 6~10 등과 같이 범위로 제공이 되기에 병렬적으로 처리할 수 있다.

이처럼 병렬처리 과정에서도 생각을 해야 하는 부분이 많고 잘못 사용할 경우 위와 같이 성능에서 큰 이슈가 발생 할 수 있다.

주의해야 할 점에 대해 정리를 하자면,

- 언박싱 주의
- 비즈니스 로직이 순서와 상관 없는지 확인
- 소량 데이터의 경우 순차가 우월
- 병합 과정의 비용까지 비교/계산

### 포크/조인 프레임워크

정렬 알고리즘 중 divide and conquer 와 유사하다. 병렬적으로 처리가 가능한 수준으로 로직을 분리하고 작은 작업으로 진행 후 
결과를 합치는 식으로 진행하게 된다.

스레드 풀의 작업자 스레드에 분산 할당하는 `ExecutorService` 를 구현해서 사용한다.

- RecursiveTask

이름 그대로 반복적인 작업 방식으로 진행하는 것인데, 더 이상 나눌 수 없는 작업 단위까지 작업을 나누고 이후에 순차 진행하며 결과를
조인하는 방식으로 진행된다.

```java
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

  private final long[] numbers;
  private final int start;
  private final int end;

  public static final long THRESHOLD = 10_000;

  public ForkJoinSumCalculator(long[] numbers) {
    this(numbers, 0, numbers.length);
  }

  @Override
  protected Long compute() {
    int length = end - start;

    if (length <= THRESHOLD) {
      return computeSequentially();
    }
    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length/2);
    ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length/2, end);
    Long leftResult = leftTask.join();
    Long rightResult = rightTask.join();
    return leftResult + rightResult;
  }

  private long computeSequentially() {
    long sum = 0;

    for (int i = start; i < end; i++) {
      sum += numbers[i];
    }

    return sum;
  }

}
```

위 코드를 보면 `RecursiveTask<T>` 를 상속받아서 `compute()` 메서드를 구현하는데 이 때 메서드 안에서 divide 작업을 진행한다.

후에 join 을 통해서 결과를 합치고 마지막으로 최종 결과를 리턴하게 된다.

> RecursiveTask
> 
> `join()` 메서드를 보아 알겠지만 RecursiveTask 는 ForkJoinTask 를, ForkJoinTask 는 Future 를 상위 클래스로 둔다.
> 이렇게 비동기 요소를 이용해 구현을 했기에 각각의 task 를 임의로 분리하여 스레드 할당 후 병렬로 작업을 진행하게 할 수 있다.

```java
class ForkJoinSumCalculatorTest {

  @Test
  void computeTest() {
    long[] targets = LongStream.rangeClosed(1, 10_000).toArray();
    ForkJoinSumCalculator forkJoinSumCalculator = new ForkJoinSumCalculator(targets);

    long startTime = System.currentTimeMillis();
    Long result = new ForkJoinPool().invoke(forkJoinSumCalculator);
    System.out.println(System.currentTimeMillis() - startTime);

    startTime = System.currentTimeMillis();
    Long resultByReduce = Arrays.stream(targets).reduce(0L, Long::sum);
    System.out.println(System.currentTimeMillis() - startTime);

    assertEquals(resultByReduce, result);
  }
}
```

실제로 사용을 해보면 생각보다 성능이 나오지 않는 이슈가 있는데 그 이유는 앞서 상황과 유사하게 long[] 을 이용하여 언박싱이 발생하였기 떄문이다.

이 `RecursiveTask` 에서 주의해야 할 점이 몇 가지 있다.

- 로직 : 앞서 말했듯이 병렬로 처리해도 되는지, 내부 비즈니스 로직이 과연 나누어서 진행하는게 옳은지에 대해 판단해야 한다.
- fork + compute : 위를 예시로 들면 오른쪽, 왼쪽에 모두 fork/fork 를 하는게 더 이득으로 보이지만 한쪽에선 compute 를 호출하는데
그 이유는 한쪽에서는 compute 를 호출 할 경우 해당 스레드에서는 같은 스레드를 재사용할 수 있게 되어 스레드 타스크 할당시 발생하는 오버헤드를 줄일 수 있다.
- stack trace : 무릇 대다수의 병렬 작업의 디버깅이 그러하듯 ForkJoin 도 스택트레이스를 통해 디버깅하기 어렵다.

마지막으로 누차 이야기 하지만 '무조건 병렬이 빠르다' 는 생각은 하면 안된다. 비즈니스 로직에 따라 순차 진행이 더 빠를 수 도 있고
로직적으로 어쩔 수 없이 순차를 진행할 수 밖에 없는 경우도 생기기 마련이기에 작업에 대해 잘 가늠하고 진행해야 한다.

### 작업 훔치기(work stealing)

개발자가 아무리 태스크를 분리하고 적절하게 나눈다 하더라도 필시 어느 한 부분에선 스레드가 노는 현상이 발생 할 수 있다. 이러한 현상은
컴퓨팅 환경에 따라 더욱 극명하게 나타난다. 예컨데 DB I/O 에 대해 잘 분리해서 작업을 하더라도 갑자기 예기치 못한 트래픽이 발생하여 대량의
IO 가 발생 할 경우 연관되어 있는 작업에 대해서는 극단적으로 속도가 늦어 질 수 있다.

이러한 부분을 개선하고자 포크/조인 프레임워크에서 `작업 훔치기` 라는 기법을 사용해서 처리한다.

쉽게 이야기하면 할당된 태스크가 끝났을 경우 태스크가 쌓여있는 큐의 꼬리에서 작업을 훔쳐온다.

큐라는 자료구조 상 뒤에 있는 작업은 현재 작업중인 스레드에서 관여할만한 포인트가 아니기에 다른 유휴 스레드에서 해당 태스크를 가져와서
작업을 진행해도 무방하다.


### Spliterator 인터페이스

자바 8에서 나온 새로운 인터페이스로, split + iterator 즉, 분할할 수 있는 반복자 라는 의미이다.

Iterator 가 붙어 있듯이 유사한 기능을 제공하는데, 병렬작업에 특화되어 있는 것이 특징이다.

```java
public interface Spliterator<T> {
  boolean tryAdvance(Consumer<? super T> action);
  Spliterator<T> trySplit();
  long estimateSize();
  int characteristics();
}
```

Spliterator 인터페이스는 위와 같이 구성되어 있다.

- tryAdvance : iterator 와 동작이 같음
- trySplit : 앞서 이야기한 fork/join 과 유사하게 일부 요소를 분할 후 Spliterator 로 다시 생성
- estimateSize : 계산할 요소의 개수
- characteristics : 분할된 spliterator 의 특성에 대해 반환

trySplit 의 경우 최초 spliterator 가 시도 시 1회 분할하여 총 2개의 spliterator 가 남게되지만, 이후부터 실행 시
분할된 모든 spliterator 에서 다시 한번 split 을 시도한다.

즉 최초 1 -> 2 -> 4 -> 8 ... 와 같은 형식으로 분할이 된다. 다만 더 이상 분할 할 수 없으면 trySplit 은 null, 즉 더 이상 분할되지 않는다.

한마디로 spliterator 는 stream 을 만들때 그 특성에 대해 정의해주어 해당 stream 의 연산 시 이러한 방식으로 연산하라 라는 메타값 혹은 설정 값이라고 볼 수 있다.

