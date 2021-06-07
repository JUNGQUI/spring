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
    
    friends.set(0, "SOME THING WRONG"); // Exception occurred!
    friends.add("Richard");
    
    return friends;
  }
}
```