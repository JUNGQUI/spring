### 동적 파라미터화

SW 에서 흔히 유지보수성 측면 이라 함은 변화에 유연하게 대처 가능한 성질을 말하곤 한다.

그런데 이 코드라는게 사실 어떤 한 목적에 의해서 만들어진 class 가 그때는 맞고 지금은 아니다 식으로 표현이 되기 쉽상이기에
유지보수성 측면에서 효율적으로 만들기가 여간 어려운것이 아니다.

그 조건을 충족하기 위해 많은 기법들이 있는데 그중 하나가 동적 파라미터화 라고 할 수 있다.

이전에 작성한 Apple 필터를 한번 보자.

```java
import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;

public class AppleFilter {
  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (Color.GREEN.equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }
}
```

여기에서 빨간색에 대한 필터도 추가되었다고 가정해보자.

```java
import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;

public class AppleFilter {

  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (Color.GREEN.equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }

  public static List<Apple> filterRedApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (Color.RED.equals(apple.getColor())){
        result.add(apple);
      }
    }
    return result;
  }
}
```
딱봐도 단점이 있는데

1. 모든 코드가 동일하고 색상을 구분하는 부분만 다르다.
2. 색상이 추가될 때 마다 이러한 작업이 반복적으로 필요해진다.

이 부분을 수정하기 위해선 사실 간단하다. 필터링을 할 색상을 파라미터로 주면 해결이 된다.

```java
import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;

public class AppleFilter {

  public static List<Apple> filterColorApples(List<Apple> inventory, Color color) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (color.equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }
}
```

그런데, 한번 더 외부의 요청이 들어왔다. 알다시피 Apple 에는 색상 뿐 아니라 무게도 속성값으로 가지고 있고, 이 무게에 대한 필터도
필요해졌다고 가정해보자.

```java
import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;

public class AppleFilter {

  public static List<Apple> filterApple(
      List<Apple> inventory
      , Color color
      , int weight
      , boolean flag) {
    List<Apple> result = new ArrayList<>();

    if (flag) {
      for (Apple apple : inventory) {
        if (color.equals(apple.getColor())) {
          result.add(apple);
        }
      }
    } else {
      for (Apple apple : inventory) {
        if (apple.getWeight() > weight) {
          result.add(apple);
        }
      }
    }

    return result;
  }
}
```

너무나도 불편한 코드다. 비교하는 값이 조금 다를 뿐 모든 부분이 동일한데 반복적으로 수행되며, 이를 사용하기 위한 부분에서도
불필요하게 `flag` 를 파라미터로 넣어줘야 하고, 무게를 필터로 할때 쓸데없이 색상을 넣어줘야하며, 색상을 필터로 할때 무게를 넣어줘야 한다.

이러한 부분을 탈피하게 predicate 를 사용 할 수 있다.

```java
import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;

public interface ApplePredicate {

  boolean test(Apple apple);
}

public class AppleHeavyWeightPredicate implements ApplePredicate {

  public boolean test(Apple apple) {
    return apple.getWeight() > 150;
  }
}

public class AppleGreenColorPredicate implements ApplePredicate {

  public boolean test(Apple apple) {
    return Color.GREEN.equals();
  }
}
```

위와 같이 Predicate 인터페이스를 만들고 사용할 경우

```java
import com.jk.spring.java.modern.chapter1.Apple;
import com.jk.spring.java.modern.chapter1.Color;
import com.jk.spring.java.modern.chapter2.ApplePredicate;

public class AppleFilter {

  public static List<Apple> filterApple(List<Apple> inventory, ApplePredicate applePredicate) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (applePredicate.test(apple)) {
        result.add(apple);
      }
    }
    return result;
  }
}
```

실제 사용시에는 이와 같이 runtime 에서 필터를 정해서 사용하면 된다.

```java
class ApplePredicateTest {

  @Test
  void predicateTest() {

    List<Apple> inventory = Arrays.asList(
        new Apple(125, Color.GREEN),
        new Apple(155, Color.GREEN),
        new Apple(170, Color.RED)
    );

    List<Apple> heavyApples = FilterApple.filterApple(inventory, new FilterHeavy());
    List<Apple> greenApples = FilterApple.filterApple(inventory, new FilterGreenColor());

    heavyApples.forEach(heavyApple -> Assertions.assertTrue(heavyApple.getWeight() > 150));
    greenApples.forEach(greenApple -> Assertions.assertEquals(Color.GREEN, greenApple.getColor()));
  }
}
```

이와 같이 필터 자체도 동적 파라미터화 시키면 상황에 따라 유연한 대처가 가능하고 추가되는 것들 또한 같은 interface 를 상속받아 구현하기에
메인 코드에서는 변경될 필요가 적어진다.

- 람다식 사용

사실 더 간단하고도 편리하게 사용하는 방법이 있는데, 익명 클래스를 람다식으로 구현하는 것이다.

익명 클래스의 경우 위와 같이 필터를 하나씩 하나씩 만드는것이 아니라 런타임에서 바로 구현하여 사용하는 것이다.

장점은 따로 클래스 구현이 필요 없이 그대로 사용이 가능하다는 것이다.
 
```java
import com.jk.spring.java.modern.chapter1.Color;
import com.jk.spring.java.modern.chapter2.FilterApple;
import java.util.ArrayList; 

public class FilterLambdaUse {
 public void test() {
   List<Apple> inventory = new ArrayList<>();
   // ...
   List<apple> result = FilterApple.filterApple(inventory, (Apple apple) -> Color.RED.equal(apple.getColor()));
 }
} 
```

이처럼 구현 할 경우 훨씬 깔끔해진다.

- 리스트 추상화

마찬가지로 위의 항목을 T 형식으로 추상화 할 경우 필요한 필터를 그때 그때 추가하여 한번에 중첩 필터를 만들 수 있다.

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

이와 같이 T type 으로 추상화하고 사용 할 경우 Apple 뿐 아니라 다양한 class 에 대해서 predicate 를 사용해서
필터링이 가능하다. T 또한 동적 파라미터화한것이고 predicate 또한 lambda 형식으로 매 필터 지정 시 구현을 하면 동적으로 그때 그때
구현이 가능해진다.

- Runnable, Callable

위와 마찬가지로 Runnable Callable 을 상속받아서 다양한 동작을 동적으로 할당이 가능하다.

```java
public class JKRunnable {

  public void runnableTest() {
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        // SOME LOGIC
      }
    });

    Thread lambdaT = new Thread(() -> {
      // SOME LOGIC
    });
  }
}
```

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public interface Callable<V> {

  V call();
}

public class JKCallable {

  public void callableTest() {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    Future<String> threadName = executorService.submit(new Callable<String>() {
      @Override
      public String call() throws Exception {
        return Thread.currentThread().getName();
      }
    });

    Future<String> lambdaThreadName = executorService
        .submit((Callable<String>) () -> Thread.currentThread().getName());
  }
}
```