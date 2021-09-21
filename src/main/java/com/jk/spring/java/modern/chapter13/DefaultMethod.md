### 인터페이스 상속

인터페이스를 상속 받아 사용하는 경우 크게 두 가지 방법이 있다.

1. extend 를 통한 슈퍼 클래스 사용
2. static method 사용

```java
public class SuperClass {
  protected int superNum;
  
  public int getSuperNum() {
    return this.superNum;
  }
  
  public void setSuperNum(int num) {
    this.superNum = superNum;
  }
}

public class SubClass extends SuperClass {
  private int subNum;
  
  public int getSubNum() {
    return this.subNum;
  }
  
  public void setSubNum(int subNum) {
    this.subNum = subNum;
  }
  
  public int getSumOfNum() {
    return this.superNum + this.subNum;
  }
}
```

위와 같이 사용 할 경우 extends 를 통해 자연스럽게 superClass 의 메소드 등을 사용 할 수 있다.

```java
public class StaticClass {
  public static int getStaticNumber() {
    return 5;
  }
}

public class UseStaticClass {
  public int getValueByStaticClass() {
    return StaticClass.getStaticNumber();
  }
}
```

또한 위와 같이 사용할 경우 실질적인 상속 없이 사용이 가능하다. 자바 8 에서는 인터페이스 내에도 정적 클래스를 직접 선언할 수 있기 때문에
유틸리티 클래스를 없애고 유틸리티의 성격을 인터페이스 내에 구현할 수 있다.

```java
public interface StaticInterfaceMethod {
  public int getNumber();

  public static int getStaticNumber() { // <--- 인터페이스 내에 메서드 바디가 있지만 에러가 아님
    return 5;
  }
}
```

### Default method

언뜻 보면 static method 와 별 다를게 없어 보인다. 상속을 받을 경우 해당 구현체 또한 같이 상속을 받아 상속받은 클래스에서 해당
메서드를 사용 할 수 있는 점은 분명 static 과 유사해 보인다.

다만 큰 차이점이 있는 것은 Static method 의 경우 상속받아 사용하는 것이 아닌 interface 자체의 구현되어 있는 부분을 사용하는 것이고
default method 의 경우 아직 구현되지 않은 인터페이스 내의 메서드를 사용해서 결과를 내려줄 수 있다는 점이다.

```java
public interface DefaultMethodInterface {
  public int size();
  
  default boolean isEmpty() {
    return size() == 0;
  }
  
  static boolean isEmptyStatic() {
    return size() == 0; // <- 컴파일 에러, size method 를 사용 할 수 없음
  }
}
```

또한 당연하게도 default method 는 구현체도 상속받기 때문에 default method 에 대해서는 별도로 구현 할 필요가 없다.

인터페이스에 기본 메서드를 만들기 때문에 이를 이용해서 다양한 메서드 조합을 구성으로 만들 수 있다.

```java
public interface A {
  public int aMethod();
  default public int defaultAMethod() {
    return 1;
  } 
}

public interface B {
  public int bMethod();
  default public int defaultBMethod() {
    return 2;
  }
}

public class C implements A, B {
  @Override
  public int aMethod() {
    return defaultAMethod();
  }
  
  @Override
  public int bMethod() {
    return defaultBMethod();
  }
}
```

### 해석 규칙

위와 같이 인터페이스의 경우 여러가지를 동시에 상속이 가능하기에 default method 끼리의 충돌이 일어 날 수 있다. 이럴 경우의 해석 규칙이
있다.

```java
public interface A {
  default void hello() {
    System.out.println("Hello from A");
  }
}

public interface B extends A {
  default void hello() {
    System.out.println("Hello from B");
  }
}

public class C implements B, A {
  public static void main(String... args) {
    new C().hello();
  }
}
```

1. 무조건 클래스가 이긴다. 클래스나 슈퍼 클래스와 디폴트 메서드가 충돌한다면 디폴트를 무시한다.
2. 1번을 제외하고는 서브 인터페이스가 이긴다. 현재 상황에서 C에 새로운 `hello()` 가 있다면 C 가 이긴다.
3. 그럼에도 불구하고 우선 순위가 정해지지 않았다면, 명시적으로 디폴트 메서드를 오버라이드 한 후 호출해야 한다.

