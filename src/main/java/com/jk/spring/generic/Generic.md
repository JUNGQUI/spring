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

Generic 을 통해 구현하는 방법에는 크게 3가지 용법이 있다.

- Class

```java
public class JKGeneric<T> {
  private T t;
  
  public void setT(T t) {
    this.t = t;
  }
  
  public T getT() {
    return this.t;
  }
}
```

- Interface
  
```java
public interface JKGenericInterface<T> {
  T someMethod(T t);
}

public interface JKGenericBiInterface <T1, T2> {
  T1 firstMethod(T1 t1);
  T2 secondMethod(T2 t2);
}

public class JKGenericClassString implements JKGenericInterface<String> {
  @Override
  public String someMethod(String string) {
    return "called by method " + string;
  }
}

public class JKGenericClassInteger implements JKGenericInterface<Integer> {
  @Override
  public Integer someMethod(Integer integer) {
    return integer + 1;
  }
}
```

- method

```java
public class JKGenericMethod {
  public <T> int genericMethod(T[] list, T item) {
    int count = 0;
    
    for (T t : list) {
      if (item == t) {
        count++;
      }
    }
    return count;
  }
}
```

- wildcard

```java
class WildcardGenericType {
  
  public List<? extends Number> method1() {
    return new ArrayList<Long>();
  }
  
  public <T> List<? extends String> method2(T param) {
    return new ArrayList<String>();
  }
  
  public List<?> method3() {
    return new ArrayList<>();
  }
}
```

> 참고
> ```text
> E - Element
> K - Key
> N - Number
> T - Type
> V - Value
> ```