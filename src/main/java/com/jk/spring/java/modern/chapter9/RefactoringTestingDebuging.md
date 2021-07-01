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
  - 람다식에서 외부의 변수를 오버라이드 할 경우 에러가 발생하지만 익명 클래스에서는 새로운 변수로 취급하여 사용이 가능하다.
3. 함수형 개발 방식에서 람다는 변수의 모호함을 초래한다.
  - 파라미터로 함수형 인터페이스와 Runnable 을 받는 경우 둘 다 함수형 인터페이스 이므로 람다로 두 메서드 호출이 모두 가능해진다.

이런 제약 조건들 때문에 람다로 리팩터링을 할 때 주의해서 사용해야 한다.