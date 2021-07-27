### null 대신 Optional 클래스

```java
public class Person {
  private Car car;
}

public class Car {
  private Insurance insurance;
}

public class Insurance {
  private String name;
}
```

여기 3개의 클래스가 있다. `Person` -> `Car` -> `Insurance` 이렇게 구조가 짜여져 있고 이 상태에서

```java
import com.jk.spring.java.modern.chapter11.Person;

public class NullExceptionCase {

  public String getCarInsuranceName(Person person) {
    return person.getCar().getInsurance().getName();
  }
}
```

이런 구조의 클래스가 있다고 가정해보자. 눈으로 봐도, 컴파일 에러를 체크해봐도 해당 로직에는 이슈가 없는것처럼 보인다.

하지만 주어진 person 이 null 이라면? person은 있지만 해당 person 이 차량이 없다면? 차량이 있지만 보험을 들지 않았다면?

이와 같이 다양하게 해당 객체가 아예 없는 상황이 발생 할 수 있고 그런 없는 객체에서 값을 뽑아내려고 하는 행위를 항다면 nullPointer Exception 을 마주하게 된다.

이런 케이스를 방지하고자 NPE 방어코드가 있다.

---

- 보수적 방어

```java
import com.jk.spring.java.modern.chapter11.Person;

public class NullExceptionCase {

  public String getCarInsuranceName(Person person) {
    if (person != null) {
      Car car = person.getCar();

      if (car != null) {
        Insurance insurance = car.getInsurance();

        if (insurance != null) {
          return insurance.getName();
        }
      }
    }

    return "Unknown";
  }
}
```

아주 간단하고도 확실한 방법은 모든 케이스에 대해 null 체크를 하는 것이다. 딱봐도 쉽게 이해가 되고 모든 요소에 대해 검증을 하기에
빈틈도 없다. 하지만 가독성 문제도 있고, 무엇보다 코드가 늘어지게 되어 변경에 취약하게 된다.

- Optional

null 에 대한 이슈를 해결하기 위해 등장한 클래스가 바로 `Optional` 클래스 이다.

해당 클래스는 다른 클래스들을 감싸는 Optional<T> 형태를 가지고 있는데, 값이 있으면 해당하는 값을, 없으면 빈 값을 가지고 있고
이를 메서드를 통해 안의 결과를 가져올 수 있다.

이러한 부분을 이용해서 stream API 중 flatMap 을 이용 할 수 있다.

flatMap 의 경우 감싸져있는 객체 내부에 대해 작업을 진행 할 수 있게 해주는데 이 때 접근이 가능한지 여부를 자연스럽게 확인 할 수 있다.

```java
public class OptionalPerson {
  public String cannotBeNullOptional(Optional<Person> person) {
    return person.flatMap(Person::getCar)
        .flatMap(Car::getInsurance)
        .map(Insurance::getName)
        .orElse("Unknown");
  }
}
```

위와 동일한 로직을 가진 코드인데, `person` 자체를 optional 로 받아 null 이 아닐 경우 flatMap 을 통해 내부 객체 즉, Car 에 접근할 수 있게 한다.

접근이 가능하다면 Car 를 person 으로부터 가져오고 다시 flatMap 을 이용해서 Car 로부터 Insurance 를 가져오고 이후에 getName 을 통해 이름을 가져오되,
만약 값이 없다면 "Unknown" 을 반환한다.

Stream API 특성상 파이프라인에서 현재 조건에 충족되지 않으면 다음 연산으로 넘어가기 때문에 조건이 하나라도 충족되지 않으면 orElse 쪽으로 빠지게 된다.
orElse 의 경우 Optional 객체에서 null 이 발생했을 때 null 대신 다른 객체를 전달하게 할 수 있다.

> orElse vs orElseGet
> 
> orElse 의 경우 단순하게 객체를 반환한다. 따라서 null 이 발생하지 않음에도 모든 반복문에 대해 실제로 객체는 생성해두되 해당 stream API 가 종료되면
> 메모리 블럭이 날아가는 형식을 취하는 반면, orElseGet의 경우 파라미터 자체를 Supplier 로 받기 때문에 실제로 orElseGet 으로 유입이 되는 순간
> Supplier 를 통해 값을 제공하기에 생성해 두는 등 자원 소모가 발생하지 않는다.

