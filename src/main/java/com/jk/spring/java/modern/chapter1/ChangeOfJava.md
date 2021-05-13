# 자바 8, 9, 10, 11 의 변화점

- java 8, lambda

자바 8에서부터 람다 표현식이 등장했다.

```java
public class Comparison {
  
  public void beforeLambda() {
    Collections.sort(invertory, new Comparator<Apple>() {
      public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
      }
    });
  }
  
  public void afterLambda() {
    invetory.sort(comparing(Apple::getWeight));
  }
}
```

전형적인 타입은 위와 같다.

Collections 내 내장되어 있는 sort 는 파라미터로 정렬할 Collection, 비교 function 으로 되어 있다.
비교 function 을 이용해서 Apple 객체 내의 weight 라는 프로퍼티로 정렬 비교를 하게 된다.

그러나 람다를 이용하면 함수형을 이용해서 간단하게 구현이 가능하다.

- stream processing

한 번에 한 개씩 만들어지는 연속적인 데이터 항목들의 모임을 스트림 이라고 한다. 우리가 흔히 볼 수 있는 연속적인 데이터 항목의 모음은
Collections 가 있다.

```java
public class StreamTest() {
  List<String> stringStream = Arrays.asList("a", "b", "c");
  
  public void test() {
    stringStream.forEach(s -> {
          // SOME LOGIC
        });
  }
}
```

가장 큰 이득은

1. 고수준으로 추상화하여 손쉽게 사용 가능
2. 스레드라는 복잡한 작업을 사용하지 않으면서 병렬성 작업 처리 가능

조금 더 자세한 내용은 4장~7장에 걸쳐 진행한다.

- 동작 파라미터화

쉽게 말해, 함수를 파라미터처럼 만들어서 동작하는 코드 자체를 메서드에 전달 할 수 있는 것이다.

```java
public class BehaviorParameterization {

  public void resultTest() {
    System.out.println(test("a", (a) -> a += "function Processing"));
  }

  private String test(String a, Function<String, String> appendingFunction) {
    return appendingFunction.apply(a + " test processing ");
  }
}
```

사실 이러한 부분들이 추후 나오는 함수형 프로그래밍의 기본이 되는데, 이 부분 또한 추후 보완하려 한다.

- 병렬성, 공유 가변 데이터

사실 위에서 언급했던 것만 보면 아주 훌륭한 변화인데, 얻는게 있으면 잃는 것도 있는 법이다. synchronized 를 이용하지 않고 병렬성을 얻기
위해 함수들을 순수 함수로 작성해야 하는 단점이 생겼다.

> 순수 함수?
> 
> 외부에서 유입된 값에 대한 변화가 전혀 없이 로직을 수행하는 함수라 할 수 있다. 단적으로 덧셈 함수를 만든다고 가정할 때 두 값을
> 내부에서 특수하게 변경 후 두 합을 합치는 로직이라면 이미 특수하게 변경하는 로직은 외부에서 처리가 끝난 후 유입이 되어야 한다.
> 
> 쉽게 말해 side-effect 가 없어야 한다는 것이고 그렇기에 내부에서 사용되는 객체들은 불변객체로서 작동해야 한다.
> 
> ```java
> import java.util.Arrays;
> 
> public class StreamMapSum {
>   public void test() {
>     List<Integer> streams = Arrays.asList(1, 2, 3, 4, 5);
>     // int resultOfSum = 0;
>     // streams.forEach(i -> resultOfSum+=i); // 이 부분에서 resultOfSum 은 외부이고, 해당 내역은 불변으로써 진행되어야 함
>     int resultOfSum = streams.stream().mapToInt(i -> i).sum();
>     // IntStream 으로 변환하고 전체의 합이기에 sum() 이라는 메서드를 통해 합친다.
>   }
> }
> ```

이렇게 된 이유는 당연하게도, 안정성 이슈가 있다. stream 으로 병렬 처리를 하려면 외부의 값을 변경시키는 순간 그 값은 어떠한
side-effect 로 인해 변경이 될지 장담 할 수 없게 된다.

이렇기에 lambda 식 뿐 아니라 stream API 를 사용할 때도 순수 함수로써 작동해야 한다.