### 컬렉션 API 개선

#### collection factory

list 를 만드는 가장 기본적인 방법은 new 를 통해 선언 후 add 하는 방식일것이다.

```java
import java.util.ArrayList;

public class CollectionTest {

  public List<String> makeCollection() {
    List<String> friends = new ArrayList<>();
    friends.add("Raphael");
    friends.add("Olivia");
    friends.add("Thibaut");
    
    return friends;
  }
}
```

하지만 너무 번거롭다. 요소 하나를 추가해 줄 때마다 라인이 하나씩 더 생긴다.

```java
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionTest {

  public List<String> makeCollection() {
    return Arrays.asList("Raphael", "Olivia", "Thibaut");
  }
}
```

이렇게 하면 ~~뭘 만드는건지 모르니 가독성은 안좋지만~~ 한번에 컬렉션을 만들 수 있다.

그러나 이슈는 하나 더 있는데, 타입에 대한 validation 이 되지 않는다는 것이다.

```java
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionTest {

  public List<String> makeCollection() {
    List<String> friends = Arrays.asList("Raphael", "Olivia", "Thibaut");
    
    friends.set(0, "SOME THING WRONG"); // Exception occurred!
    friends.add("Richard");
    
    return friends;
  }
}
```

위와 같이 set 의 경우 연산이 불가능한 작업이기 때문에 `UnsupportedOperationException` 이 발생한다.

이러한 부분을 방지하고자 Collection Factory 가 존재한다.

```java
import java.util.ArrayList;
import java.util.Arrays;

public class CollectionTest {

  public List<String> makeCollection() {
    List<String> friends = List.of("Raphael", "Olivia", "Thibaut");
    
    friends.add("Richard"); // Exception occurred!
    
    return friends;
  }
}
```

뭔가 이상하다. 정상적인 컬렉션을 생성했고 추후에 추가 등의 연산을 수행하려 했는데 수행이 되지 않는다.

팩토리 메서드를 잘 생각해보면 답이 나온다. 팩토리 메서드의 경우 정형화되어 있는 `틀`이 존재해서 공장에서 찍어내듯이 재사용이 가능하다.
이러한 특성으로 인해 생성되는 결과도 초기에 주어진 값에서 벗어나지 못하는, 불변 객체가 된다.

분명 불가능한 연산에 대한 해법이라고 생각했겠지만 로직에서 보면 그 무엇보다 예상하지 못한 방식으로 작성된 코드가 에러가 될 확률이 높다.

팩토리 메서드를 통해 구현한 값의 경우 변경이 불가능하기 때문에 NullPointerException, UnsupportedOperationException 등
상정하지 못한 예외가 발생하지 않게 된다.

추가적으로 값의 변경이 필요하게 된다면 새 요소를 포함하는 컬렉션을 팩토리 메서드로 생성하면 된다.

비슷한 팩토리 메서드로 Set.of, Map.of 가 있다.

> Map.of
> 
> Map 은 구현이 조금은 특이하다.
> 
> ```java
> public class MapOfTest {
>   public void mapOfTest() {
>     Map<String, Integer> ageOfFriends = Map.of("Raphael", 30, "Olivia", 25, "Thibaut", 26);
>   }
> }
> ```
> 이와 같이 요소의 (K1, V1, K2, V2, K3, V3, K4, V4...) 와 같은 구조로 구현하게 된다.

#### removeIf, replaceAll, sort

```java
import com.jk.spring.java.modern.chapter5.Trader;
import com.jk.spring.java.modern.chapter5.Transaction;

public class RemoveIterate {

  public void removeIterate() {
    List<Transaction> transactions = List.of(
        new Transaction(new Trader("a1", "c1"), 2020, 5000)
        , new Transaction(new Trader("a2", "c2"), 2020, 5000)
        , new Transaction(new Trader("a3", "c3"), 2020, 5000)
        , new Transaction(new Trader("a4", "c2"), 2020, 5000)
        , new Transaction(new Trader("a5", "c1"), 2020, 5000)
        , new Transaction(new Trader("a6", "c3"), 2020, 5000));
    
    for (Transaction transaction : transactions) {
      if("c2".equals(transaction.getTrader().getCity())) {
        transactions.remove(transaction);
      }
    }
  }
}
```

위와 같이 반복을 하면서 해당 객체에서 요소를 제거하는 경우 에러가 발생하게 된다.

발생하는 예외는 `ConcurrentModificationException` 즉, 반복 수행중인 요소에서 해당 요소 삭제 시 `.next()` 호출로 다음으로 진행할 요소와 삭제가 되어야 할 현재 요소 두 요소가 동일한데,
각자 상이한 연산을 적용하려 하기 때문에 발생하게 된다.

이러한 부분을 해결해주는 것이 `removeIf` 이다.

`transactions.removeIf(transaction -> "c2".equals(transaction.getTrader().getCity()));`

방법은 매우 간단하다. 원리적으로는 iterator 를 가져와서 요소를 만들지 않고 현재 iterator 에 대해 직접 remove 연산을 수행하는 것이다.

`removeIf` 가 그와 같은 역할을 수행해주는데, Predicate 를 제공하면 해당 predicate 에 맞는 부분을 iterator 내에서 찾아 remove
연산을 수행한다.

비슷하게 값을 변경하는 `replaceAll()` 연산이 있다.

```java
import com.jk.spring.java.modern.chapter5.Trader;
import com.jk.spring.java.modern.chapter5.Transaction;

public class ReplaceIterate {

  public void replaceIterate() {
    List<Transaction> transactions = List.of(
        new Transaction(new Trader("a1", "c1"), 2020, 5000)
        , new Transaction(new Trader("a2", "c2"), 2020, 5000)
        , new Transaction(new Trader("a3", "c3"), 2020, 5000)
        , new Transaction(new Trader("a4", "c2"), 2020, 5000)
        , new Transaction(new Trader("a5", "c1"), 2020, 5000)
        , new Transaction(new Trader("a6", "c3"), 2020, 5000));
    
    transactions.replaceAll(transaction -> transaction.setYear(2021));
  }
}
```