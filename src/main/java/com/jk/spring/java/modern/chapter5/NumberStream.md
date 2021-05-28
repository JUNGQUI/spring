### 숫자형 스트림

앞서 Dish 에서 칼로리를 계산하는 것 처럼 칼로리 자체로만 이루어진 스트림을 숫자형 스트림이라고 한다.

이 부분이 중요한 이유는 이러한 특성을 이용해 기본형 특화 스트림이 제공되기 떄문이다.

- IntStream
- DoubleStream
- LongStream

위 3가지 특화형 스트림의 경우 sum, max, min 등과 같은 기본 연산을 제공한다.

```java
import com.jk.spring.java.modern.chapter4.Dish;

public class ChangeStream {

  public void changeTypeStream() {
    List<Dish> dishList = new ArrayList<>();

    int totalCalorie = dishList.stream()
        .mapToInt(Dish::getCalories)  // 기본형 특화 스트림으로 변경
        .sum(); // sum 사용
  }
}
```

### 스트림 생성

```java
import java.util.Arrays;

public class MakeStream {

  public void valueStream() {
    Stream<String> stringStream = Stream.of("Mordern", "Java", "In", "Action");
  }

  public void arrayStream() {
    int[] numbers = {2, 3, 5, 7, 11, 13};
    int sum = Arrays.stream(numbers).sum();
  }

  public void iterateStream() {
    Stream.iterate(0, n -> n+2)
        .limit(10)
        .forEach(System.out::println);
  }
  
  public void generateStream() {
    Stream.generate(Math::random)
        .limit(5)
        .forEach(System.out::println);
  }
}
```

`iterate()` 같은 경우가 초기값, 이후 로직을 입력해주면 무한으로 생성이 가능하다. 이와 같은 stream 을 언바운드 스트림이라고
한다.
반면 `generate()` 의 경우도 동일하게 수행이 가능한데, `iterate()` 와 달리 연속적인 값 연산을 수행하지 않는다.