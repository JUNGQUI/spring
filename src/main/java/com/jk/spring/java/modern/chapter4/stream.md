### stream API

stream 은 연속적인 이란 뜻을 가진 영단어로 collections 제어와 관련된 java 8 에서 추가된 API 이다. 

별도의 멀티스레드 코드 없이 병렬 처리가 가능하게 해주며 이러한 편리성과 성능 상 이점, 가독성등을 이유로 많은 사랑을 받고 있는 API 이다.

stream 의 비교를 위해 하나의 예시로 전/후 코드를 작성해보자.

```java
@UtilityClass
public class BeforeAndAfterStream {
  public List<String> beforeSorting(List<Dish> menu) {
    List<Dish> lowCalories = new ArrayList<>();

    for (Dish dish : menu) {
      if (dish.getCalories() < 400) {
        lowCalories.add(dish);
      }
    }

    // default method + lambda + method reference
    lowCalories.sort(Comparator.comparingInt(Dish::getCalories));
    List<String> lowCaloriesDishesName = new ArrayList<>();
    for (Dish dish : lowCalories) {
      lowCaloriesDishesName.add(dish.getName());
    }

    return lowCaloriesDishesName;
  }

  public List<String> afterSorting(List<Dish> menu) {
    List<Dish> lowCalories = menu.stream()
        .filter(dish -> dish.getCalories() < 400)
        // default method + lambda + method reference
        .sorted(Comparator.comparingInt(Dish::getCalories))
        .collect(Collectors.toList());

    return lowCalories.stream().map(Dish::getName).collect(Collectors.toList());
  }
}
```

`beforeSorting` 을 보면 메뉴를 받아와서 메뉴에서 400 칼로리 미만인 것들만을 조건으로 낮은 칼로리의 dish 를 만들고
만들어진 낮은 칼로리의 음식들에서 이름을 뽑아서 반환해준다.

이를 stream API 를 이용해서 구현하면 `afterSorting` 과 같이 간단하게 구현이 된다.

사실 `afterSorting` 도 더 간단하게 구현이 가능한데

```java
import com.jk.spring.java.modern.chapter4.Dish;

public class SimpleStreamDish {

  public List<String> afterSorting(List<Dish> menu) {
    return menu.stream()
        .filter(dish -> dish.getCalories() < 400)
        // default method + lambda + method reference
        .sorted(Comparator.comparingInt(Dish::getCalories))
        .map(Dish::getName)
        .collect(Collectors.toList());
  }
}
```

이름을 추출하고 새로 만드는 부분도 stream API 로 구현이 가능하다.

> parallelStream
> 
> 병렬 stream API 이다. 별도의 멀티스레드 환경이나 기타 설정이 필요 없이 바로 병렬 형식으로 진행이 가능해진다.

- 스트림, 컬렉션

가장 큰 차이점은 반복을 통한 연산에 있다.

컬렉션의 경우 사용자가 직접 요소를 반복시켜야 한다.

```java
import com.jk.spring.java.modern.chapter4.Dish;

public class CollectionStreamIteration {

  public void external(List<Dish> menu) {
    for (Dish dish : menu) {
      // SOME LOGIC
    }
  }
  
  public void internal(List<Dish> menu) {
    menu.forEach(d -> {
      /*SOME LOGIC*/
    });
  }
}
```

컬렉션의 반복을 `외부 반복`, 스트림의 반복을 `내부 반복` 이라고 한다. 이름이나 코드에서 알 수 있듯이 외부 반복의 경우
외부에서 반복할 요소를 지정해서 반복 작업을 진행한다. 그에 반해 내부 반복의 경우 스트림 내에 반복 요소가 자동으로 다음 요소를 가져오는 등
사용자 입장에서 불필요하게 코드를 추가하거나 값을 지정할 필요가 없다.

이러한 부분이 가독성 측면에서 장점으로 다가오고, 무엇보다 모듈처럼 반복문 또한 사용이 가능하기에 쉽게 구현도 가능하다.

- 스트림 연산

스트림은 .을 통해 중간 중간에 연산을 하나씩 붙여 나갈 수 있다. 이를 중간 연산 이라 하며 최종적으로 결과값을 리턴하거나 닫는 연산을 최종 연산
이라고 한다.

```java
import com.jk.spring.java.modern.chapter4.Dish;

public class CollectionStreamIteration {
  // ...
  
  public void internal(List<Dish> menu) {
    menu.stream()
        .filter(d -> {/*SOME FILTER LOGIC*/})
        .forEach(d -> {
          /*SOME LOGIC*/
        });
  }
}
```

위 코드에서 filter 부분이 바로 중간 연산인데, 코드에서 보이듯이 연산의 결과를 스트림으로써 다음 연산자에게 넘겨준다.

`filter` 의 경우 현재 stream 에서 조건에 맞지 않은 요소들은 배제한 뒤 남은 요소를 stream 으로 만들어서 다음 연산자에 넘겨 주는 것이다.
이를 통해 전달 받은 stream 에서 forEach 반복을 통해 추가 연산을 수행하고 결과를 사용했던 menu 에 돌려준다.

당연하게도 연속적인 중간 연산도 수행이 가능하다. 이전의 스트림에서 중간 연산을 거치고 이후의 연산도 중간 연산을 거쳐 결과로 반환이 가능하다.

