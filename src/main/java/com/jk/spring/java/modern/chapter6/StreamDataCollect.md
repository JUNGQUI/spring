### 스트림으로 데이터 수집

이전 장에서 스트림을 소모하지 않고 변경하는 중간 연산, 값을 소모해서 최종적으로 하나의 결과로 도출하는 최종 연산 등을 학습하였는데
최종 연산 중 `.collect(Collectors.toList())` 을 통해 컬렉션으로 결과를 도출하는 것을 진행하였다.

사실 누적 방식에는 Collection, Collector, Collect 로 가지가 나뉘어지게 된다.

### Collector?

Collector 는 인터페이스로, 스트림의 요소를 어떤 방식으로 도출할지 지정한다.

- 리듀싱 연산

이전에 스트림 내의 리듀서 연산을 통해 forEach 말고도 sum 등의 연산을 처리 할 수 있는걸 알 수 있었는데, `collect` 의 경우도
내부에서 Collector 가 스트림 전체 요소에 대해 리듀싱 연산을 수행 할 수 있다.

```java
import com.jk.spring.java.modern.chapter5.Transaction;
import java.util.Currency;

public class CollectorReducing {

  public void collectGrouping() {
    Map<Currency, List<Transaction>> transactionByCurrencies = transactions.stream()
        .collect(groupingBy(Transaction::getValue));
  }
}
```

이와 같이 collect 내에서는 toList 로 리스트형식 반환 뿐만 아니라 연산 또한 수행이 가능하다.

이중 분류 연산이 있는데 `groupingBy()` 라는 메서드를 써서 구현이 된다.

```java
@UtilityClass
public class GroupingDish {
  
  public enum CaloricLevel {
    DIET, NORMAL, FAT
  }
  
  public Map<Type, List<Dish>> groupingByDishType(List<Dish> menus) {
    return menus.stream()
        .collect(groupingBy(Dish::getType));
  }
  
  public Map<CaloricLevel, List<Dish>> groupingByDishCalories(List<Dish> menus) {
    return menus.stream()
        .collect(groupingBy(dish -> {
          if (dish.getCalories() <= 400) {
            return CaloricLevel.DIET;
          } else if (dish.getCalories() <= 700) {
            return CaloricLevel.NORMAL;
          }
          
          return CaloricLevel.FAT;
        }));
  }
  
  public Map<Type, List<Dish>> groupingByTypeWithDishCalories(List<Dish> menus) {
    // 이렇게 하면 500 칼로리 이상의 타입이 없는 경우 타입 자체 (key) 가 사라지게 된다.
//    return menus.stream()
//        .filter(m -> m.getCalories() > 500)
//        .collect(groupingBy(Dish::getType));
    // filter 를 상속받은 filtering 을 사용 할 경우 타입에 따라 grouping 이 된 후 필터로 재그룹화하기에 비어있는 타입이 완성된다.
    return menus.stream()
        .collect(
            groupingBy(
                Dish::getType
                , filtering(dish -> dish.getCalories() > 500, toList())
            )
        );
  }
}
```

`groupingBy()` 의 경우 내부에서 어떤 키값으로 그룹화를 시킬지를 명시시켜주면 현재 그룹화에 정의한 로직에 맞게 스트림을 재구성해준다.

두번째 메서드인 `groupingByDishCalories()` 의 경우 람다 표현식으로 내부에 어떤 방식으로 그룹화를 할지 정의해주었고
이 정의에 맞춰 스트림을 구룹화한다.

세번째 메서드인 `groupingByTypeWithDishCalories()` 의 경우 필터를 통해 500칼로리 이상의 음식만 그룹화하는 로직을 수행한건데,
주석처리된 첫번째 로직을 보면 filter 이후 groupingBy 로 그룹화를 시도했다.

하지만 주석에도 적혀있듯이 이렇게 수행하게 되면 fish 에서 500칼로리 이상이 없을 경우 (굳이 fish 뿐만 아니라 다른 타입에서도) 해당
타입의 경우 키에서 아예 제외되어 버린다.

우리가 원한 로직은 500칼로리 이상의 음식을 수집하되 타입은 불변으로 남아있기를 원했기에 오류이다.
이러한 부분을 `groupingBy`의 두번째 파라미터로 filtering 을 전달하여 해결이 가능하다.

우선 필터 적용이 안된 스트림에서 타입별로 나누고 이후 filter 를 상속받은 filtering 을 통해서 그룹화를 **다시** 시도한다.

이 **다시** 라는 것이 중요한데, 타입은 이미 정의가 되어 있기에 그 상태에서 filtering 에 전달된 로직에 맞춰 