- 의무 체인

의무 체인이란 단어 그대로 한 로직이 작업을 처리하고 그 결과를 다음 로직 객체(즉, 클래스나 메서드) 에 인자로써 전달하고 다시 그 로직 객체가
또다른 로직 객체에 전달하고 이와 같은 방식으로 작업이 진행되어야 할 때 의무 체인 패턴을 사용한다.

```java
public abstract class ProcessingObject<T> {
  protected ProcessingObject<T> successor;

  public void setSuccessor(ProcessingObject<T> successor) {
    this.successor = successor;
  }

  public T handle(T input) {
    T r = handleWork(input);
    if(successor != null) {
      return successor.handle(r);
    }

    return r;
  }

  abstract protected T handleWork(T input);
}

```

위 인터페이스를 살펴보면 클래스 자기 자신을 `successor` 로 받고 `handle()` 메서드가 실행된 후 받은 결과를 다시 셋된 클래스의 `handle()` 메서드를 호출한다.
이와 같은 구조가 바로 의무 체인 패턴을 이용했는데, `handleWork()` 메서드의 경우 위에서 언급했던 전략 패턴을 사용해서 추상화 시켰다.

그리고 이러한 부분을 이용해서 클래스를 만들면

```java
public class HeaderTextProcessing extends ProcessingObject<String> {

  @Override
  protected String handleWork(String text) {
    return "From Raoul, Mario and Alan: " + text;
  }
}

public class SpellCheckerProcessing extends ProcessingObject<String> {

  @Override
  protected String handleWork(String text) {
    return text.replace("labda", "lambda");
  }
}
```

이와 같은 구조로 구현이 가능하다.

```java
class ProcessingObjectTest {
  @Test
  void processingObject() {
    ProcessingObject<String> p1 = new HeaderTextProcessing();
    ProcessingObject<String> p2 = new SpellCheckerProcessing();
    p1.setSuccessor(p2);

    String testText = "Aren't labdas really hot?!";
    String result = p1.handle(testText);

    assertEquals(
        "From Raoul, Mario and Alan: " + testText.replace("labda", "lambda")
        , result);

    UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
    UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replace("labda", "lambda");
    Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);
    String resultWithLambda = pipeline.apply(testText);

    assertEquals(result, resultWithLambda);
  }
}
```

위 테스트 코드를 보면 처음에 생성해둔 클래스 `HeaderTextProcessing`, `SpellCheckerProcessing` 의 경우 모두 텍스트를 받아서
변경 후 리턴하는 간단한 클래스이지만, 중요한 부분은 `p1.setSuccessor(p2)` 를 통해 p1 이 성공한 이후 해당하는 결과를 p2의 인자로
다시 전달하여 체이닝을 걸고 로직을 수행했다는 점이다.

이러한 부분을 람다 표현식을 사용하여 다시 구현을 한게 아래에 해당하는 부분인데, 람다식으로 `UnaryOperator` 를 이용해 function 을 만들고
`Function` 을 이용해서 파이프라인을 구축했다.

이러한 과정을 통해 위에서 만든 `setSuccessor`, `successor.handle()` 과 같은 효과를 동일하게 가져갈 수 있다.

---

- 팩토리 패턴

인스턴스화 로직을 노출하지 않고 객체를 만들 때 팩토리 디자인 패턴을 사용한다. 대표적인 팩토리 패턴은 `of` 메서드를 이용한 내용일 것이다.

```java
public class SomeFactoryObject {
  private String someName;
  private String someCode;
  
  public static SomeFactoryObject of(String someName, String someCode) {
    // 만약 여기에서 어떤 로직이 필요하다면 팩토리 패턴으로 생성자는 제공해주지 못하는 로직을 수행 할 수 있다.
    return new SomeFactoryObject(someName, someCode);
  }
  
  // 스스로만 접근 가능
  private SomeFactoryObject(String someName, String someCode) {
    this.someName = someName;
    this.someCode = someCode;
  }
}
```

이와 같은 구조가 팩토리 패턴이라 볼 수 있다.

```java
@Data
public class Product {
  private String code;
  private String productName;
  private double portion;

  private Product(String code, String productName, double portion) {
    this.code = code;
    this.productName = productName;
    this.portion = portion;
  }
}

public class Stock extends Product {
  public Stock() {
    this.setCode("C_STOCK");
    this.setProductName("스톡");
    this.setPortion(0.33);
  }
}

public class Bond extends Product{
  public Bond() {
    this.setCode("C_BOND");
    this.setProductName("채권");
    this.setPortion(0.5);
  }
}

public class Loan extends Product {
  public Loan() {
    this.setCode("C_LOAN");
    this.setProductName("대출");
    this.setPortion(0.1);
  }
}
```

위 코드는 은행의 대출 상품을 클래스로 만드는데 `Product` 라는 클래스를 각 상품군이 상속받아 사용하는 케이스이다.

각 상품군의 경우 이름이나 코드가 변경될 이유가 없다고 가정하고 추후 추가될 내용이 있지만, 아직 완벽히 비즈니스 로직이 정해지지 않아 상속받는 별도 클래스로 구성했다고 가정하자.

이렇게 되어 있을 경우 각 상품군 클래스의 생성자에서 `set` 메서드를 통해 생성을 하는데 클래스를 생성할 때 생성자 호출 부분만 다를 뿐 나머지 로직은 동일하다.
이럴 경우 팩토리 패턴을 통해 하나의 클래스로 3가지 케이스를 나눠서 생성하게 변경 할 수 있다.

```java
public class ProductFactory {
  public static Product createProduct(String name) {

    if (name.equals(ProductType.LOAN.getName())) return new Loan();
    if (name.equals(ProductType.STOCK.getName())) return new Stock();
    if (name.equals(ProductType.BOND.getName())) return new Bond();

    throw new RuntimeException("No Such product " + name);
  }
}
```

이와 같은 구조일 때 `ProductFactory` 내부 로직을 람다로 표현이 가능하다.

```java
import com.google.common.collect.ImmutableMap;
import com.jk.spring.java.modern.chapter9.factory.Bond;
import com.jk.spring.java.modern.chapter9.factory.Loan;
import com.jk.spring.java.modern.chapter9.factory.Product;
import com.jk.spring.java.modern.chapter9.factory.ProductType;
import com.jk.spring.java.modern.chapter9.factory.Stock;
import java.util.HashMap;
import java.util.function.Supplier;

public class ProductFactoryWithLambda {

  public static final Map<String, Supplier<Product>> productInitMap = ImmutableMap.of(
      ProductType.LOAN.getName(), Loan::new,
      ProductType.STOCK.getName(), Stock::new,
      ProductType.BOND.getName(), Bond::new
  );

  public static Product createProduct(String name) {
    Supplier<Product> p = productInitMap.get(name);
    if (p != null) return p.get();
    throw new RuntimeException("No Such product " + name);
  }
}
```

이와 같이 동일한 로직을 if 가 아닌 Supplier 를 이용해서 구현하면 supplier 가 함수형 인터페이스 이기에 람다 형식으로 표현이 가능하고
코드가 짧아진다.
또한 불필요한 if 구절이 없어졌기에, 현재는 로직이 간단해서 헷갈리거나 로직의 틈이 빠져나갈 일이 없어보이지만, 복잡한 로직과 조건을 가지고 있다면
if 구절이 늘어날수록 가독성이 떨어지고 추후에 ProductType 이 늘어나면 늘어나는 숫자만큼 if 문이 늘어나고 생성자 부분 즉, 팩토리 페턴을 적용한
부분에서 디자인 패턴을 적용한 이점이 사라지게 된다.

이러한 부분들을 람다 표현식을 이용해서 간단하게 해결 할 수 있다.