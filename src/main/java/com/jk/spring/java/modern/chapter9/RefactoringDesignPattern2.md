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