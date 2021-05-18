### 스트림 활용

- predicate

앞서 봤던 filter 를 살펴 보면 조건식에 참인 것만 새로운 스트림으로 존재한다. 이 부분에 대해서도 여러가지 메서드가 존재한다.

- distinct
  
유니크 값을 가져온다. 고유 여부 판단은 hashCode, equals 로 판단한다.

> lombok @EqualsAndHashCode
> 
> 보통 구현해야 하는 Equals 나 hashCode 의 경우 롬복에서 간단하게 제공해준다. 다만 간단하게 제공해주는 것에 의의가 있어서
> 복잡한 로직을 가진 클래스의 Equals, HashCode 연산을 수행 할 경우 성능이 잘 안 나올 수 있다.

### 스트림 슬라이싱

- takeWhile

slice 역할을 한다. 이름에서 알 수 있듯이 조건문에 만족하기 전까지 while 을 통해 반복한는 메서드이다.

```java
public class JKTakeWhile {
  private List<Dish> mutableMenu = Arrays.asList(
      new Dish("season", true, 120, Type.OTHER),
      new Dish("prawns", false, 300, Type.FISH),
      new Dish("rice", true, 350, Type.OTHER),
      new Dish("chicken", false, 400, Type.MEAT),
      new Dish("salmon", false, 450, Type.FISH),
      new Dish("french fries", true, 530, Type.OTHER),
      new Dish("pizza", true, 550, Type.OTHER),
      new Dish("beef", false, 700, Type.MEAT),
      new Dish("pork", false, 800, Type.MEAT),
      new Dish("pork", false, 800, Type.MEAT)
  );
  
  // ...
  void takeWhile() {
    List<Dish> nonTakeWhile = mutableMenu.stream()
        .filter(d -> d.getCalories() < 500)
        .collect(Collectors.toList());

    List<Dish> takeWhile = mutableMenu.stream()
        .takeWhile(d -> d.getCalories() < 500)
        .collect(Collectors.toList());

    Assertions.assertEquals(nonTakeWhile.size(), takeWhile.size());
    for (int i = 0; i < nonTakeWhile.size(); i++) {
      Assertions.assertEquals(nonTakeWhile, takeWhile);
    }
  }
}
```

위와 같은 케이스의 경우 칼로리 순으로 정렬이 되어 있다. 이 부분에서 조건으로 갈라치기 해서 조건이 어긋나는 순간 이전까지 탐색했던 값을
다음 stream 으로 넘겨준다.

비슷한 예시로 dropWhile 이 있다. dropWhile 은 takeWhile 과 반대로 처음으로 거짓이 나오는 값 이전의 요소를 모두 버린다.

> 주의
> 
> 해당 기능은 java 9 에서 추가되었기에 java 8 에서 무턱대고 사용하면 에러가 발생한다.

이외에도 요소의 개수를 제한하거나 

limit : 결과값을 전달받은 인자 (Number) 의 값만큼을 요소에서 가져와 스트림으로 만든다.

skip : 결과값을 전달받은 인자 (Number) 의 값만큼을 요소에서 건너뛰고 이후 요소부터 스트림으로 만든다.

- Mapping

스트림을 전달 받아 스트림에 대해 연산을 진행한다. 이 떄 진행되는 연산의 경우 고친다 보단 새롭게 만든다 라는 개념이므로 사용에 주의해야 한다.

```java
import java.util.ArrayList;
import java.util.stream.Collectors;

public class JKMapping {

  public void mapTest() {
    List<String> someStrings = new ArrayList<>();

    List<String> editedNewStrings = someStrings.stream()
        .map(s -> s + " edited")
        .collect(Collectors.toList());
  }
}
```

- flatMap

다음과 같은 list 가 있다고 가정해보자.

```java
public class StreamMap {
  private final List<String> testCase = ImmutableList.of(
      "Hello", "World"
  );
}
```

이 컬렉션에서 철자가 유니크하게 뽑혀져나와 새로운 한 글자 짜리 컬렉션으로 만들어야 하는 로직이 있다고 가정해보자.

```java
public class StreamMap {
  private final List<String> testCase = ImmutableList.of(
      "Hello", "World"
  );
  
  public List<String> splitDistinct() {
    List<String> result = new ArrayList<>();
    someStrings.stream()
        .map(s -> s.split(""))
        .forEach(s -> Arrays.stream(s).distinct().forEach(inner -> {
          if (!result.contains(inner)) {
            result.add(inner);
          }
        }));
    return result;
  }
  
}
```

기존의 아는 방식을 사용한다면, 각 string 을 스트림으로 변환하고 거기에서 유니크하게 스트림을 걸러낸 다음 결과값에 없는 요소만을
결과값에 추가하여 만들 수 있다.

하지만 굉장히 비효율적이고 코드도 이리저리 꼬여있어 가독성 측면에서 부담스러운 코드다. 이럴 때 flatMap 을 이용하면 편하게 로직을 수행 할 수 있다.

```java
public class StreamMap {
  private final List<String> testCase = ImmutableList.of(
      "Hello", "World"
  );

  public List<String> splitAndDistinctArraysStreamWithFlatMap(List<String> someStrings) {
    return someStrings.stream()
        .map(s -> s.split(""))
        .flatMap(Arrays::stream)
        .distinct()
        .collect(Collectors.toList());
  }

}
```

위 코드와 같이 스트림으로 만들어서 리턴 할 때 flatMap 으로 하면 한 단계 (depth) 안까지 들어가서 안의 내용들을 stream 으로 만들어서
다음 체이닝된 메서드에 전달하게 된다.

즉 해석을 해보면,
```java
public class StreamMap {
  private final List<String> testCase = ImmutableList.of(
      "Hello", "World"
  );

  public List<String> splitAndDistinctArraysStreamWithFlatMap(List<String> someStrings) {
    return someStrings.stream()
        .map(s -> s.split(""))          // Hello, World 를 "H", "e" ... 로 만듬, List<String[]> 형태
        .flatMap(Arrays::stream)        // flatMap 을 통해 평탄화 작업, Stream<String[]> 으로 변경 (기존에 있던 겉으로 싸인 컬렉션 붕괴)
        .distinct()                     // String[] 안의 요소들에 대해 distinct 작업 진행
        .collect(Collectors.toList());  // distinct 된 stream 을 List<String> 으로 가져옴
  }

}
```

이렇게 작업이 된다. 한마디로 단순히 1depth 안에 들어가서 언박싱해주는 수준이 아니고 depth 자체를 없애버려서 스트림화 하는게 주 내용이다.

- search and matching

- anyMatch : 조건이 맞는 요소가 하나라도 있는지 확인한다.
- allMatch : 조건이 해당 요소 전체에 대해 성립하는지 확인한다.
- noneMatch : 조건에 맞는게 있는지 확인한다. 없을 때 true.

> 쇼트서킷
> 
> short-circuit 이라는 의미에서 알 수 있듯이 앞의 조건이 선결되면 후 조건은 무시하고 return 하는 조건들을 의미한다.
> 
> `if (CONDITION1 || CONDITION2) {...} ` 이러한 코드가 있을 때 `CONDITION1` 을 충족하면 뒤의 `CONDITION2` 의 경우
> 무시하고 앞의 조건 연산만 수행 후 결과를 반영한다.
> 
> 이처럼 위의 match 와 관련된 메서드의 경우는 모두 쇼트 서킷 기법을 사용했다.

