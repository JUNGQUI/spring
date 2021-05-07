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
- 함수 : 클래스에 종속되지 않으므로 함수라 칭하며, 그외 메소드처럼 파라미터 전달 등 기능
- 전달 : 람다 표현식 자체를 메소드 인수로 전달하거나 변수로 저장 가능
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

