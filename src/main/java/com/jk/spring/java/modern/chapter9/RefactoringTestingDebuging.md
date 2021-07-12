# 리팩터링, 테스팅, 디버깅

### 가독성 개선

사실 리팩터링에서 가장 중요한 부분이자 지금까지 강조했던 부분이 가독성 개선이다. 개인적인 생각으로 CPU 등 컴퓨팅 리소스는 날이 가면 갈수록
발전하는지라, 일반적인 개발에서 무지막지한 낭비만 하지 않으면 퍼포먼스 측면에서 큰 이슈는 없을거라 본다.

물론 아직까지도 멀티 스레드 환경에서 동시성 제어와 같은 부분은 분명히 순서와 기타 로직을 깨뜨리지 않게 하다 퍼포먼스가 떨어지는 등의 이슈가
발생 할 수 있다. 하지만 그런 부분 외적으로는 큰 이슈는 없다.

하지만 개발자의 일반적인 스펙트럼은 큰 변경이 없다. 개발하는 사람들은 모두 보편적인 사람이며 시각 또한 보편적이기에 베베 꼬여있는 코드 보단
딱 봐도 이해가 가능한 명쾌한 코드를 가지고 노는 편이 개발 퍼포먼스에서도, 유지보수 측면에서도 우위에 있다.

그래서 이번 챕터는 리팩터링, 테스팅, 디버깅과 관련된 부분을 진행하게 되는데 그 중 첫 단락이 바로 가독성 개선이다.

```java
public class SomeClass {

  public void noNameMethod() {
    Runnable r1 = new Runnable() {
      @Override
      public void run() {
        System.out.println("Hello");
      }
    };
    
    Runnable r2 = () -> System.out.println("Hello");
  }
}
```

r1, r2 는 동일한 효과를 가지고 있다. 그러나 길이에서부터 일단 차이가 난다. 그리고 사실 Runnable 을 잘 알고 잘 몰라도 안의 로직을 보면 무척 간단하기에 쉽게 파악이 가능하다.

하지만 안의 내용이 복잡해지거나 할 경우 불필요하게 오버라이드, 런 등의 문자들이 코드를 장식하게 되고 집중도가 떨어지게 된다.

이런 좋은 람다식의 리팩토링도 마냥 좋은 것은 아닌게, 여러 제약이 있다.

```java
public class SomeClass {

  public void noNameMethod() {
    int a = 10;
    Runnable r1 = new Runnable() {
      @Override
      public void run() {
        int a = 2;
        System.out.println(this); // <- r1
        System.out.println(a);
      }
    };
    
    Runnable r2 = () -> {
      int a = 2;  // <- error
      System.out.println(this); // <- class `SomeClass`
      System.out.println("Hello");
    };
  }
}
```

1. 익명 클래스의 `this` 와 람다의 `this` 는 다르다.


2. `shadow parameter`
   
람다식에서 외부의 변수를 오버라이드 할 경우 에러가 발생하지만 익명 클래스에서는 새로운 변수로 취급하여 사용이 가능하다.


3. 함수형 개발 방식에서 람다는 변수의 모호함을 초래한다.

파라미터로 함수형 인터페이스와 Runnable 을 받는 경우 둘 다 함수형 인터페이스 이므로 람다로 두 메서드 호출이 모두 가능해진다.

이런 제약 조건들 때문에 람다로 리팩터링을 할 때 주의해서 사용해야 한다.

---

- 람다 표현식 메서드 참조로 리팩토링

앞선 예제에서 람다를 이용해 음식의 칼로리 종류를 기준으로 그룹핑을 하는 방식이 있었다.

```java
import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter6.GroupingDish.CaloricLevel;

public class CaloricDish {

  public Map<CaloricLevel, List<Dish>> dishesByCaloricLevel(List<Dish> menu) {
    return menu.stream()
        .collect(groupingBy(dish -> {
          if (dish.getCalories() <= 400) return CaloricLevel.DIET;
          if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
          return CaloricLevel.FAT;
        }));
  }
}
```

이 부분에서 `groupingBy` 부분의 인자로 넘겨지는 부분은 람다 표현식인데, 파라미터로 넘겨주기에 인자로써 작동하는 이 표현식이 너무
장대해서 가독성에 이슈가 있다.

이 부분을 하나의 메서드로 뽑아내서 메서드 참조 형식으로 구현하는 방식이 있다.

```java
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Dish {
  private final String name;
  private final boolean vegetarian;
  private final int calories;
  private final Type type;

  @JsonCreator
  public Dish of(
      @JsonProperty("name") String name
      , @JsonProperty("vegitarian") boolean vegetarian
      , @JsonProperty("calories") int calories
      , @JsonProperty("type") Type type) {
    return new Dish(name, vegetarian, calories, type);
  }

  public enum Type {
    MEAT, FISH, OTHER
  }
  
  public CaloricLevel getCaloricLevel() {
    if (this.getCalories() <= 400) return CaloricLevel.DIET;
    if (this.getCalories() <= 700) return CaloricLevel.NORMAL;
    return CaloricLevel.FAT;
  }
}
```

`getCaloricLevel()` 메서드는 `Dish` 클래스 안에 존재하며 클래스 안의 요소인 `getCalories()` 를 이용해서 칼로리를 연산, 적당한
enum 으로 반환하는 메서드이다. 이 메서드를 참조하여 위 람다 표현식을 리팩토링 하면 아래와 같이 구현이 가능하다.

```java
import com.jk.spring.java.modern.chapter4.Dish;
import com.jk.spring.java.modern.chapter6.GroupingDish.CaloricLevel;

public class CaloricDish {

  public Map<CaloricLevel, List<Dish>> dishesByCaloricLevel(List<Dish> menu) {
    return menu.stream()
        .collect(groupingBy(Dish::getCaloricLevel));
  }
}
```

어차피 `getCaloricLevel` 메서드의 경우 파라미터가 없고 클래스 내장 메서드이기에 인자로 넘겨주는 `dish` 를 통해 접근이 가능하며
`groupingBy` 의 인자로써 함수로 사용되어 결과적으로 동일한 결과를 도출한다.

또한 `Collections.sort(PARAM)` 에서 PARAM 으로 전달시 `comparing` 등 정적 메서드를 이용해서 조합하여 사용도 가능하다.

---

- 명령어 데이터 처리의 스트림화

스트림 API 는 앞서 이야기 했듯이 쇼트 서킷과 `lazy evaluation` 으로 무장했다. 그렇기에 명령형 반복문에 대한 처리가 훨씬 더 빠르고
이 뿐만 아니라 멀티 프로세스 베이스의 병렬형 처리도 별도의 수정 없이 간단하게 진행이 가능하다.

> short circuit
> 결과를 가져오기 위해 전체를 순회하지 않고 조건에 해당하며 구문에 대한 값을 얻을 수 있을 때 해당 값만을 추출해서 반환하는 형식을 의미한다.
> 
> ```java
> public class ShortCircuit {
>   void shortCircuitTest(List<Dish> menu) {
>     System.out.println(menu.stream().findAny(SOME_FILTERING_CONDITION));
>   }
> }
> ```
> 
> 위와 같이 `findAny` 를 이용 할 경우 조건에 맞는 하나의 값이 발견되면 바로 반환이 가능하기에 전체를 순회 할 필요가 없다. 이를 쇼트 서킷이라 부른다. 


> lazy evaluation
> 
> 쇼트 서킷과 비슷한 개념으로 전체 연산에 대한 개념으로 짧게 접근하는 형식이다. 예컨데 다중 if문의 순회를 돌되 전체 if 가 통과 되어야 한다고 가정을 하면
> 전체 if 에 대해 true 여야 통과하기에 모든 연산이 true 인지를 확인하기 위해 && 연산자를 사용하거나 중첩된 if 를 구현해야 한다.
> 
> 하지만 스트림 API 를 이용해서 구현하면 하나 하나가 파이프라인 형식으로 제공되기에 가독성에도 좋고 중간에 하나의 filter 라도 걸릴 경우 바로 다음
> 요소에 대한 순회로 넘어가기 때문에 훨씬 효율적이다.
> 
> 계산, 연산에 대해 lazy 하게 그때 그때 파악하고 넘어가기에 lazy evaluation 이라고 표현한다.
> ```java
> class StreamLazyEvaluationTest {
> 
>   void lazyEvaluationTest() {
>     List<Dish> dishByLazy = StreamLazyEvaluation.lazyEvaluation(UtilClass.immutableMenu);
>     List<Dish> dishByCommand = new ArrayList<>();
> 
>     for (Dish dish : UtilClass.immutableMenu) {
>       if (dish.getCaloricLevel().equals(CaloricLevel.NORMAL)) {
>         if (dish.getCalories() >= 550) {
>           dishByCommand.add(dish);
>         }
>       }
>     }
>   }
>   
>   public List<Dish> lazyEvaluation(List<Dish> menu) {
>     return menu.stream()
>       .filter(d -> d.getCaloricLevel().equals(CaloricLevel.NORMAL))
>       .filter(d -> d.getCalories() >= 550)
>       .collect(Collectors.toList());
>   }
> }
> ```
> 
> 위 연산에서 `dishByLazy` 부분과 `dishByCommand` 를 구현하는 부분은 결과적으로 동일하다. 
> 하지만 for 문으로 구현 시 각 단계별(1. 칼로리 타입이 NORMAL 인지 2. 그것들 중에 칼로리가 550 이상인지)로 한 스텝씩 구현을 한다고 하면
> 결과적으로 n^2 의 복잡도를 가지게 된다.
> 
> 그렇게 구현하지 않을 경우 위와 같이 구현이 가능한데 보다시피 if 중첩으로 인해 가독성이 떨어진다.
> 
> 반면에 스트림 API 를 이용할 경우 파이프라인 형식으로 구현해 각 스텝별로 쉽게 구분이 가능하고 lazy 연산을 통해 조건 불충족 시 다음 단계로 넘어가는 대신
> 해당 요소에 대한 연산을 중지하고 다음 요소에 대한 연산을 진행하여 효율적으로 순회가 가능하다.