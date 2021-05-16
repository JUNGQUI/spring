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

- getting start Stream API

