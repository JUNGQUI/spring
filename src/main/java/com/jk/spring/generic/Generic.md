### Generic

일반적인, 범용적인 이란 뜻을 가진 단어로 주로 Collection 에서 타입을 정의하지 않고 범용으로 받을 수 있게 할 때 사용한다.

```java
@UtilityClass
public class FilterT {
  public <T> List<T> filter(List<T> list, Predicate<T> p) {
    List<T> result = new ArrayList<>();
    list.forEach(t -> {
      if (p.test(t)) {
        result.add(t);
      }
    });

    return result;
  }
}
```

Predicate 인터페이스로 필터 조건을 받게 하되 특정형에서만이 아니라 범용적으로 사용 할 수 있게 `T` 로 받아낸다.

```java
public class JKGeneric {
  public void test() {
    List<String> startWithALambda = FilterT.filter(stringInventory, (s -> s.startsWith("a")));
    List<Apple> heavyApples = FilterT.filter(inventory, apple -> apple.getWeight() > 150);
    List<Apple> greenApples = FilterT.filter(inventory, apple -> Color.GREEN.equals(apple.getColor()));
  }
}
```

이처럼 `FilterT.filter` 를 이용해서 접근하고 Predicate 도 람다형식으로 동일하게 제공하지만 `startWithALambda` 의 경우 
`String`, 나머지는 `Apple` 객체를 필터링하는데 사용하고 있다.