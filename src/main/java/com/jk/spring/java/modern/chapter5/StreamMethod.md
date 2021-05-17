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

