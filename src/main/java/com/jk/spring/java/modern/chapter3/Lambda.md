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

- 람다 활용 요소 : 실행 어라운드 패턴

Aspect 를 알고 있는가? 어떤 특정한 시점을 지정하여 thread 가 인터셉트 당해(?) 어떤 작업을 수행하과 기존 로직을
수행하는 기능을 의미한다.

실행 어라운드 패턴도 이와 동일하다. 어떤 작업 (A, B 아니면 C 등)들이 있는데, 이 작업을 수행하기 전 똑같이 `초기화 및 준비 - 작업 - 정리, 마무리` 와 같은
패턴을 가질때 이러환 환경을 실행 어라운드 패턴 이라고 한다.

람다를 사용해서 이와 같은 부분들을 쉽게 구현이 가능하다.

이와 같은 코드가 있다고 가정하자.

```java
import java.io.BufferedReader;

public class ExecuteAroundPattern {

  public String processFile() throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
      return br.readLine();
    }
  }
}
```

위 함수를 실행하면 한 줄씩 읽게 된다. 그런데 이 부분이 두줄, 세줄, 혹은 줄을 읽고 별도의 작업을 수행한다고 가정한다면 많은 부분을 수정해야 한다.

그 상태에서 다시 이전의 1줄도 필요하다고 가정을 해보자. (즉 두 메서드가 동시에 필요하다)

이럴 경우 br.readLine 을 람다로 구현이 가능하다.

```java
import java.io.IOException;

public interface BufferedReaderProcessor {

  String process(BufferedReader b) throws IOException;
}

public class ExecuteAroundPatter {
  public String processFile(BufferedReaderProcessor p) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
      return p.process(br);
    }
  }

  public void usingWithLambda() throws IOException {
    String oneLine = processFile(BufferedReader::readLine);
    String secondLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());

    System.out.println(oneLine);
    System.out.println(secondLine);
  }
}
```

위와 같이 람다를 이용해서 그때 그때 함수형 인터페이스에 메서드를 구현해서 넣어주면 그대로 실행이 가능해진다.

- Consumer

T 객체를 받아서 void 를 반환하는 accept 라는 메서드가 정의되어 있다.

```java
@FunctionalInterface
public interface Consumer<T> {
  void accept(T t);
}
```

이러한 부분이 보통 어느 형식으로 쓰이냐면 Stream API 의 forEach 에서 쓰이곤 한다.

```java
public class JKConsumer {
  public void main(List<String> someStrings) {
    someStrings.forEach(s -> System.out.println(s));
  }
}
```

특징은 앞서 말한것처럼 T 객체를 받되 아무런 값을 리턴하지 않기에 보통 list 의 filter, list 를 순회하며 어떠한 변경 작업 등에서
사용된다.

- Function

Consumer 와는 비슷하면서 반대인 이 개념은 T 객체를 받고 R 객체를 반환하는 추상 메서드 apply 를 제공한다.

즉 어떠한 값이 주어지면 로직을 수행후 수행된 로직을 전달하는 것이 가능하다.

```java
public class JKFunction {
  public void main(List<String> someStrings) {
    List<String> editedSomeStrings = someStrings.stream()
        .map(s -> s + " edited by function in map method")
        .collect(Collectors.toList());
  }
}
```

> reference type
> 
> Consumer<T> 나 Function<T, R> 의 경우 파라미터를 제네릭 타입을 사용하게 되는데 제네릭 타입의 내부 구현 때문에 
> 어쩔수 없이 참조형(reference type) 밖에는 사용 할 수 없다. 
> 
> ex) int -> primitive type / Integer -> reference type

- 메서드 참조

:: 콜론 두 개가 붙은 형식을 본적 있는가? 그게 바로 메서드 참조이다. 이게 중요한 이유는 당연하게도 가독성때문이다.(그리고 배워야 하는 이유이기도 하다)

가장 대표적인 메서드 참조 방식이 `System.out.println` 이 있다.

```java
public class MethodReference {
  public void printSomeString(List<String> someStrings) {
    someStrings.forEach(System.out::println);
  }
}
```

위 메서드 참조는 forEach 를 통해 전달 받는 list 의 한 객체 string 을 출력하는 것이다.

```java
import java.util.List;

public class ExampleOfMethodReference {

  public void main() {
    // ToIntFunction<String> stringToInt = (String s) -> Integer.parseInt(s);
    ToIntFunction<String> stringToInt = Integer::parseInt;
    // BiPredicate<List<String>, String> contains = (list, string) -> list.contains(string);
    BiPredicate<List<String>, String> contains = List::contains;
    // Predicate<String> startsWithNumber = (String string) -> this.startWithNumber(string);
    Predicate<String> startsWithNumber = this::startWithNumber;
  }
}
```

- 생성자 메서드 참조

생성자 또한 메서드 참조를 통해 생성이 가능하다. 가령 Apple 을 다시 한번 예를 들어보자.

```java
import com.jk.spring.java.modern.chapter1.Color;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Apple {

  private Color color;
  private int weight;
}

public class useConstructorMethodReference {

  public void useCMR() {
    Supplier<Apple> appleSupplier = Apple::new;
    Apple emptyApple = appleSupplier.get(); // 빈 사과
    
    BiFunction<Color, Integer, Apple> allArguConstructorByBiFunction = Apple::new;
    Apple apple = allArguConstructorByBiFunction.apply(Color.GREEN, 150); // 색이 GREEN, 무게가 150 인 사과
  }
}
```

