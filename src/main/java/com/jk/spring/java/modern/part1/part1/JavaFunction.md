### 자바 함수

자바에서 함수라 함은 보통 정적 메서드를 의미한다.

정적 메서드라 함은 곧 고정적인, `수학적인 함수` 처럼 구현이 되어 있으며 이는 곧 일급 클래스 (first-class) 처럼 사용한다는 의미다.

> 일급 클래스?
> 
> 소트웍스 앤솔로지의 객체지향 생활체조에는 이와 같이 작성이 되어있다.
> 
> ```text
> 콜렉션을 포함한 클래스는 반드시 다른 멤버 변수가 없어야 한다.
> 각 콜렉션은 그 자체로 포장돼 있으므로 이제 콜렉션과 관련된 동작은 근거지가 마련된셈이다.
> 필터가 이 새 클래스의 일부가 됨을 알 수 있다.
> 필터는 또한 스스로 함수 객체가 될 수 있다.
> 또한 새 클래스는 두 그룹을 같이 묶는다든가 그룹의 각 원소에 규칙을 적용하는 등의 동작을 처리할 수 있다.
> 이는 인스턴스 변수에 대한 규칙의 확실한 확장이지만 그 자체를 위해서도 중요하다.
> 콜렉션은 실로 매우 유용한 원시 타입이다.
> 많은 동작이 있지만 후임 프로그래머나 유지보수 담당자에 의미적 의도나 단초는 거의 없다.
> ```
> 
> 쉽게 이야기 하면 side-effect 가 일어나지 않게 하되 내부 로직은 오롯이 정해진 로직만을 수행하게끔 해야 하며
> 다른 로직이 수행될 여지조차 없게 파라미터가 다른 멤버 변수가 없게 만들어야 함을 이른다.

- 람다

가장 대표적인 함수형 표현이라고 볼 수 있다.

```java
import java.util.ArrayList;
import lombok.Data;

public enum Color {
  RED, GREEN, BLUE
}

@Data
public class Apple {

  private int weight;
  private Color color;
}

public class businessLogic {
  public static List<Apple> filterGreenApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    
    for (Apple apple : inventory) {
      if (Color.GREEN.equals(apple.getColor())) {
        result.add(apple);
      }
    }
    return result;
  }
  
  public static List<Apple> filterHeavyApples(List<Apple> inventory) {
    List<Apple> result = new ArrayList<>();
    for (Apple apple : inventory) {
      if (apple.getWeight() > 150) {
        result.add(apple);
      }
    }
    
    return result;
  }
}
```

위와 같은 함수가 있다고 가정해보자. 

위 함수 static 필터 함수들은 실제 필터 역할을 하는 부분과 필터의 결과값을 새로 하나의 결과로 만드는 로직 두가지가 혼합되어 있다.

이러한 것이 (사실 현재는 간단한 코드기에 이해가 되지만) 복잡한 로직을 수행한다면 내부에서 버그를 발견하기 어려울 뿐더러,
위 두 가지 함수에 대해 복사 붙여넣기를 통해 새로운 필터를 만들고자 할 때도 이슈가 발생할 여지가 있다.

```java
import java.util.ArrayList;
import lombok.Data;

public enum Color {
  RED, GREEN, BLUE
}

@Data
public class Apple {

  private int weight;
  private Color color;

  public static boolean isGreenApple(Apple apple) {
    return Color.GREEN.equals(apple.getColor());
  }

  public static boolean isHeavyApple(Apple apple) {
    return apple.getWeight() > 150;
  }
}

public class BusinessLogic {
  public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
    List<Apple> result = new ArrayList<>();
    
    for (Apple apple : inventory) {
      if (p.test(apple)) {
        result.add(apple);
      }
    }
    
    return result;
  }
}

public interface Predicate<T> {
  boolean test(T t);
}

public class Main {
  public void test() {
    List<Apple> inventory = new ArrayList<>();
    
    BusinessLogic.filterApples(inventory, Apple::isGreenApple);
    BusinessLogic.filterApples(inventory, Apple::isHeavyApple);
    // ...
  }
}
```

위 코드를 이와 같이 변경한다면 아주 간단하게 조건만을 만들고 해당 조건을 Predicate 로 전달하여 수행하게 할 수 있고, 코드 가독성과 에러가
발생할 확률도 줄일 수 있다.

> Predicate?
> 
> 수학적으로는 인수로 값을 받아 true 나 false 를 반환하는 함수를 프리디케이트라고 한다.
> 
> 자바에서도 이와 마찬가지로 특정한 조건에 대한 참|거짓 을 반환하는 필터 역할을 수행한다.
> 
> 체크할 점은 Function<Apple, Boolean> 과 같은 형식으로 Predicate 대신 Function 으로 구현해서 전달이 가능하나, boolean, Boolean
> 형 변환도 할필요 없어서 효율적이기도 하며 가장 중요한 점은 이 Predicate 를 이용하는게 표준이다.
> 
> 개념만 알고 넘어가도 되는것이 기본적으로 구현이 다 되어 있기에 자연스럽게 내부 구조에 대해 상세히 몰라도 사용이 가능하다.

- 스트림

스트림에 대해 진행하기 전에 구분을 해야 하는 것이 있는데 stream API 와 collection API 이다.

- Collection API

우리가 흔히 아는 for-each 문을 통한 반복 후 수행하는 작업에 대해 Collection API 라고 할 수 있고 가장 보편적으로 쓰였을 API 가 (아마도)
`Arrays.asList` 일 것이다.

```java
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionApi {

  public void api() {
    List<String> test = Arrays.asList("a", "b", "c");
    List<String> result = new ArrayList<>();

    for (String t : test) {
      if (t.equals("a")) {
        result.add(t + "1");
      } else {
        result.add(t + "2");
      }
    }
  }
}
```

위의 코드의 로직은 a 문자열은 1 을 붙이고 나머지는 2를 붙여서 새로운 결과로 반영하는 것이다. 크게 보자면 a인지 아닌지 구분하는 필터,
실제 변화 로직을 수행하고 결과를 만드는 add 로 나뉘어져 있다.

이러한 방식의 for-each 를 이용해서 구현하는 것을 외부 반복이라고 한다.

반면 Stream API 로 해당 내역을 구현해보자.

```java
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionApi {

  public void api() {
    List<String> test = Arrays.asList("a", "b", "c");
    
    test.forEach(t -> {
      if (t.equal("a")) {
        return t + "1";
      }
      return t + "2";
    });
  }
}
```

test 자체를 수정하기에 이미 result 가 되어버린 셈이고 반복문 시 발생했던 새로운 string 인스턴스가 아닌 가지고 있는 값을 변경하기에
메모리 소모도 적다.

물론 이렇게 하는것이 기존의 원본 데이터를 훼손하는 것이기에 좋은 방법은 아니다. 그에 따라 `map() + collect(Collectors.toList())`
를 이용해서 구현하는 방법도 있다.