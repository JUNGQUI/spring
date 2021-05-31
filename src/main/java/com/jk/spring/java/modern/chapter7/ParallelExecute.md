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