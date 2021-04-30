# 자바 8, 9, 10, 11 의 변화점

- java 8, lambda

자바 8에서부터 람다 표현식이 등장했다.

```java
public class Comparison {
  
  public void beforeLambda() {
    Collections.sort(invertory, new Comparator<Apple>() {
      public int compare(Apple a1, Apple a2) {
        return a1.getWeight().compareTo(a2.getWeight());
      }
    });
  }
  
  public void afterLambda() {
    invetory.sort(comparing(Apple::getWeight));
  }
}
```

전형적인 타입은 위와 같다.

Collections 내 내장되어 있는 sort 는 파라미터로 정렬할 Collection, 비교 function 으로 되어 있다.
비교 function 을 이용해서 Apple 객체 내의 weight 라는 프로퍼티로 정렬 비교를 하게 된다.

그러나 람다를 이용하면 함수형을 이용해서 간단하게 구현이 가능하다.