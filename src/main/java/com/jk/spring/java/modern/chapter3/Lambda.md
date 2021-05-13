### Lambda

java 8에서 처음 소개된 기능으로 쉽게 말해 1-line 익명클래스라고 볼 수 있다.

> 익명 클래스?
> 
> 말 그대로 이름이 없는 클래스이다.
> 
> ```java
> public class SomeClass {
>   public void printSomething(String something) {
>     System.out.println(something);
>   }
> }
> // ...
> public class Main {
>   public void main(String[] args) {
>     SomeClass someClass = new SomeClass();
>     someClass.printSomething("call by main");
>   }
> }
> ```
> 
> 우리가 흔히 사용하는 클래스는 위와 같이 이름이 있고 사용 시 생성해서 사용하기 마련이다.
>
> ```java
> public class SomeClass {
>   public void printSomething(String something) {
>     System.out.println(something);
>   }
> }
> // ...
> public class Main {
>   public void main(String[] args) {
>     SomeClass someClass = new SomeClass() {
>       @Override
>       public void printSomething(String something) {
>         System.out.println("interrupted, " + something);
>       }
>     };
>     someClass.printSomething("call by main");
>   }
> }
> ```
> 
> 반면 이와 같이 직접 runtime 에서 구현을 할 경우를 익명 클래스라고 볼 수 있다.
> 
> 위 예시는 실제 클래스를 Override 하여 구현하였기에 애매한감이 있는데, Runnable, Callable 을 상속받은 클래스나
> 인터페이스를 이용하여 익명클래스를 구현하는 방법이 보편적이다.

람다의 특징은 크게

- 익명 : 익명 클래스와 마찬가지로 런타임에서 구현
- 함수 : 클래스에 종속되지 않으므로 함수라 칭하며, 그외 메서드처럼 파라미터 전달 등 기능
- 전달 : 람다 표현식 자체를 메서드 인수로 전달하거나 변수로 저장 가능
- 간결성 : 불필요하게 붙는 코드 구현이 필요 없음

으로 볼 수 있다.

[이전의 코드](../chapter1/ComparisonApple.java) 를 보면 기존의 재정의하고 사용하는 부분을 람다를 통해 간결하게 구현이 가능하다.
적응되기 전까진 가독성이 떨어지는데, 람다 표현식에 익숙해지면 이전 코드에서 불필요한 부분들이 눈에 띄게 된다.

이렇게 생산성을 늘릴 수 있다.

사용하는 부분은 가장 대표적인 예시가 함수형 인터페이스에 적용이 가능하다.

```java
public interface ApplePredicate {
  boolean test(Apple apple);
}
```

위 코드는 이전에 작성하였던 ApplePredicate 이다. 이와 같은 인터페이스를 사용 할때 별도의 클래스를 정의하고 상속받게 해서
ApplePredicate 를 생성 시 주입을 해주거나 익명 클래스로 런타임에 정의해줘야 했는데, 람다로도 동일하게 표현이 가능하다.

```java
public class LambdaNoLambda {
  public void diff() {
    List<Apple> heavyApples = FilterApple.filterApple(inventory, new FilterHeavy());
    List<Apple> greenApples = FilterApple.filterApple(inventory, new FilterGreenColor());
    List<Apple> heavyApplesWithLambda = FilterT.filter(inventory, apple -> apple.getWeight() > 150);
    List<Apple> greenApplesWithLambda = FilterT.filter(inventory, apple -> Color.GREEN.equals(apple.getColor()));
  }
}
```

위 필터(chapter2 패키지 참조)는 결과론적으로 동일한 작업을 진행하는데, 람다를 이용하지 않고 사용 시

1. 인터페이스를 상속받아서
2. 클래스를 구현하고
3. 해당 클래스를 new 를 통해 생성하고
4. 실제 사용 시 주입

하는 과정을 거치지 않고 람다를 통해 구현하면 4번을 함과 동시에 구현을 해서 전달을 해줄 수 있다.

- 함수형 인터페이스

`Predicate<T>` 의 경우가 함수형 인터페이스라고 볼 수 있다.

일반적으로 함수형 인터페이스는 하나의 추상 메서드만 지정할 경우를 의미한다. 대표적인 java 함수형 인터페이스는 Comparator, Callable, Runnable 이 있다.

```java
import java.util.Comparator;

public class JKComparator implements Comparator<Integer> {
  @Override
  public int compare(Integer o1, Integer o2) {
    return o1.compareTo(o2);
  }
}
```

> Comparator 는 함수형 인터페이스가 맞는가?
> 
> `Comparator` 를 구현하다 보면 `compare` 말고도 다른 메서드가 존재하기에 자칫 함수형 인터페이스가 아니지 않나 라는 생각이 들 수 있다.
> 
> 그러나 해당 구현체를 자세히 살펴보면 `super` 가 눈에 띄는데 즉, 해당 메서드 들은 확장해서 구현한 abstract method 이기에 실제 카운팅으로 치지 않는다.

람다에서 갑자기 함수형 인터페이스가 나오는 이유는 뭘까? 잘 보면 람다와 비슷한 구석이 있다.

람다도 하나의 함수 메서드 형식을 1개의 line 으로 표현했는데, 해당 표현 시 익명 함수 형식으로 구현되어 있고 1개만 존재한다.
즉, 함수형 인터페이스의 단일 메서드 지원하는 부분이 일반적으로 클래스 상속 후 구현 시 하나만 표현하는 것과 유사하다는 것이다.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public interface ExampleInterface {

  public String returnAdded(String added);
}

@Component
public class ExampleClass implements ExampleInterface {

  @Override
  public String returnAdded(String added) {
    return added + " added!";
  }
}

public class UseExampleClass {

  @Autowired
  private ExampleClass exampleClass;

  public String withInterface(List<String> added) {
    return added.stream()
        .map(exampleClass::added)
        .collect(Collectors.joining("\n"));
  }

  public String withLambda(List<String> added) {
    return added.stream()
        .map(s -> s + " with lambda added!")
        .collect(Collectors.joining("\n"));
  }
}
```

위와 같이 별도 interface, class 를 통해 구현하는 대신 그 구현체를 람다를 통해 구현하여 인스턴트처럼 사용할 수 있다.

- 함수 디스크립터 (function descriptor)

말 그대로 함수를 표현하는 방식이다. 이를 람다 표현식을 통해 설명 할 수 있다.

예를 들어 함수형 인터페이스 Runnable 의 run 메서드의 경우 인자로도 아무런 값을 받지 않고, 리턴도 아무런 값을 주지 않는다.

이런 경우 함수 디스크립터로 () -> void 와 같이 표현한다.

반대로, Comparator 의 경우 인자 T 를 받고 int 를 리턴하기에 이를 표현 할 경우 (T) -> int 로 표현이 가능하다.

> @FunctionalInterface
> 
> 앞서 설명했듯이 함수형 인터페이스의 경우 추상 메서드가 단 하나만 존재해야 한다. 이를 보정하기 위해 인터페이스에 @FunctionalInterface 어노테이션을
> 붙여서 컴파일 시 해당 검증 작업을 수행 할 수 있다.
> 
> 만약 해당 어노테이션이 붙어있음에도 불구하고 메서드가 2개 이상 있을 경우 익셉션이 발생한다.

