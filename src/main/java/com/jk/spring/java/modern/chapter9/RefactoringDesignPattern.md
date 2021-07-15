# 리팩터링, 테스팅, 디버깅

### 람다로 객체지향 디자인 패턴 리팩토링

디자인 패턴이란 어느정도 정형화 되어있는 개발 방식을 하나의 패턴으로 정립한 것이다. 예를 들자면 전략 패턴의 경우 로직의 상황에 따라
다르게 '전략' 을 선택해서 적용한다. 여기서 전략이란 앞서 이야기가 나왔듯이 함수형 인터페이스처럼 인자로 전달이 될 수 도 있고,
단순하게 if 문을 이용해서 구분을 두고 그때 그때 들어오는 인자 값에 따라 다르게 로직을 수행하는 경우도 일종의 전략 패턴이라고 볼 수 있다.

이러한 디자인 패턴은 매우 다양하고 많고 아 다르고 어 다른 부분도 있다. 다만 구현하는 방식에 대해 설명할 뿐 정확한 방법 에 대해서는 명시되어
있지 않기 때문에 이 구현체에 람다를 적용하여 리팩토링이 가능하다.

총 5가지의 패턴을 적용해볼건데,

- 전략
- 템플릿 메서드
- 옵저버
- 의무 체인
- 팩토리

---

- 전략 패턴

앞서 설명했듯이 전략 패턴은 유사한 알고리즘 (비즈니스 로직) 이 여럿 존재하고 상황에 따라 선택하여 수행하는 디자인 패턴을 의미한다.

```java
public interface ValidationStrategy {
  boolean execute(String s);
}

public class Validator {
  private final ValidationStrategy strategy;


  public Validator(ValidationStrategy strategy) {
    this.strategy = strategy;
  }

  public boolean validate(String s) {
    return strategy.execute(s);
  }
}

public class IsNumeric implements ValidationStrategy {

  @Override
  public boolean execute(String s) {
    return s.matches("\\d+");
  }
}

public class IsAllLowerCase implements ValidationStrategy {

  @Override
  public boolean execute(String s) {
    return s.matches("[a-z]+");
  }
}
```

위 케이스의 경우 파라미터로 받는 String s 에 대해 문자열 검증을 하는 인터페이스를 구현한 구현체가 두 개가 있는데
전략에 따라, 아래와 같이 `IsAllLowerCase`, `IsNumeric` 두 가지 구현체를 각각 상속받아 사용이 가능하다.

```java
public class StrategyTest {
  @Test
  @Description("전략패턴 테스트")
  void strategyValidationTest() {
    Validator numericValidator = new Validator(new IsNumeric());
    Validator lowerCaseValidator = new Validator(new IsAllLowerCase());
    Validator numericValidatorWithLambda = new Validator((String s) -> s.matches("\\d+"));
    Validator lowerCaseValidatorWithLambda = new Validator((String s) -> s.matches("[a-z]+"));

    String testCaseNumeric = "jklee1";
    String testCaseNumericFail = "jklee";
    String testCaseLowerCase = "jklee";
    String testCaseLowerCaseFail = "JKLEE";

    assertEquals(numericValidator.validate(testCaseNumeric), numericValidatorWithLambda.validate(testCaseNumeric));
    assertEquals(numericValidator.validate(testCaseNumericFail), numericValidatorWithLambda.validate(testCaseNumericFail));
    assertEquals(lowerCaseValidator.validate(testCaseLowerCase), lowerCaseValidatorWithLambda.validate(testCaseLowerCase));
    assertEquals(lowerCaseValidator.validate(testCaseLowerCaseFail), lowerCaseValidatorWithLambda.validate(testCaseLowerCaseFail));
  }
}
```

그런데, 내부 로직이 람다를 통해 표현이 가능한 수준이면 충분히 추가 클래스를 생성하지 않고도 동일한 로직을 수행하는 `validator` 를 만들 수 있다.
물론 이와 같은 경우에서 람다로 표현하기 위해선 당연하게도 함수형 인터페이스를 가지고 있어야 하고 너무 복잡한 로직일 경우 별도의 클래스로 분리해서 사용하는게 맞지만
위와 같이 간단한 로직을 가지고 있을 때 추가 클래스 없이 로직 구현이 가능해지기에 코드가 깔끔해지고 유지보수에도 유리하다.

(유지보수를 위해 내부 클래스를 들락날락하기보다 해당 인터페이스를 사용하는 클라이언트 단에서 유지보수가 가능하기 때문)

---

- 템플릿 메서드

동일한 알고리즘을 그대로 사용하진 않지만 아주 조금 변경이 필요하고 나머지를 그대로 사용해야 하는 상황이 발생했을 때 쓰는 디자인 패턴이
템플릿 메서드 패턴이다.

(말 그대로 기존의 전체적인 틀은 유지하고 조금만 바꿔서 사용하기에 template 이 붙었다.)

은행의 입금을 하나의 클래스로 만들어서 예시를 들어보자. 은행에 입금이 되는 행위는 동일하지만 (잔액 = 기존 잔액 + 입금액) 넣게 되는 과정은
온라인 뱅킹, 전화, ATM 을 통한 입금 등 방법은 다양하기에 템플릿 메서드로 입금 과정은 대체로 사용하되 세부 요소만 조정이 필요하다.

```java
abstract class OnlineBanking {
  public void processCustomer(int id) {
    Customer c = Database.getCustomerWithId(id);
    makeCustomerHappy(c);
  }
  abstract void makeCustomerHappy(Customer c);
}
```

위의 `processCustomer()` 에서 주요 포인트는 `makeCustomerHappy()` 메서드 이다. 이 부분이 각 다른 환경마다 변화가 구현하기에 따라
다른 로직을 수행 할 수 있다.

람다식을 써서 이 부분을 수정하면 이렇게 동작 추가가 가능하다.

```java
abstract class OnlineBanking {
  public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
    Customer c = Database.getCustomerWithId(id);
    makeCustomerHappy.accept(c);
  }
}
```