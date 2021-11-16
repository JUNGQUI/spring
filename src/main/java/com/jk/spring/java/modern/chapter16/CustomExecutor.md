### 커스텀 Executor

병렬적 처리를 진행함에 있어 적당한 수의 스레드를 가지게 하는 부분은 매우 중요하다.

> 스레드 풀 크기의 조절
> 
> 자바 병렬 프로그래밍에서 최적의 스레드를 가지는 공식이 있다.
> 
> N_threads = N_cpu * U_cpu * (1 + W/C)
> 
> N_threads : Runtime.getRuntime().availableProcessors() 가 반환하는 코어 수
> 
> U_cpu : 0~1 사이 값을 가지는 CPU 활용 비율
> 
> W/C 는 waiting / calculating 비율

우리 애플리케이션은 대략 99퍼센트의 시간만큼 기다리므로 W/C 를 100으로 볼 수 있다. 즉, CPU 활용률이 100퍼센트라면 400 스레드를 갖는
풀을 만들어야 하지만, 상점 수보다 많은 스레드를 가지고 있어 봤자 사용할 가능성이 전혀 없으므로 낭비다. 이러한 상황에서 Executors 를
상점마다 1개씩 가지게 조절하여 활용률을 높히고 너무 많은 스레드가 있으면 서버 크래시가 될 수 있으므로 Executor에서 최대 100개 이하로
생성하게 설정해야 한다.

```java
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class CustomExecutor {

  private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
      (ThreadFactory) runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
      });
}
```

`Executor` 를 설정하는데 상점의 개수만큼 가져오되 100개와 비교하여 최소값을 배정한다. 이렇게 만들어지는 풀은 데몬 스레드를 포함한다.

> 일반 스레드 vs 데몬 스레드
> 
> 스레드가 실행중이면 프로그램이 종료되지 않은 일반 스레드에 비해 데몬 스레드는 프로그램이 종료되면 강제 종료될 수 있다. 강제 종료가 될
> 수 있기 때문에 하염없이 기다리는 일이 없어진다.

이렇게 만든 Executor 를 이용해서 다음 코드처럼 만들어 낼 수 있다.

```java
import java.util.concurrent.CompletableFuture;

public class SomeClass {

  private final Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
      (ThreadFactory) runnable -> {
        Thread t = new Thread(runnable);
        t.setDaemon(true);
        return t;
      });
  
  void SomeMethod() {
    CompletableFuture.supplyAsync(() -> shop.getName() + " price is " + shop.getPrice(product), executor);
  }
}
```

이런 방식으로 진행하는게 성능 측면에서도 효율성 측면에서도 가장 효과적인 방법이다.

> Stream parallels vs CompletableFuture parallels
> 
> 지금까지 컬렉션 계산 시 병렬화에 두 가지 케이스를 살펴봤다. 첫번째는 Stream API 를 통해 병렬화하여 계산하는 방법, 두 번째는
> CompletableFuture 를 통해 병렬화 하는 방법이다.
> 
> 두 가지를 선택할 때 I/O 작업의 유무를 참고하면 선택하는데 도움이 된다.
>
> 유 : I/O 를 기다리는 작업을 병렬로 처리 할 경우 CompletableFuture 를 사용해 executor 를 전달하여 적절한 개수 조절을 
> 통해 효율적으로 사용 할 수 있다. 또한 스트림 특유의 게으른 특성 때문에 I/O 가 언제 끝날지 알 수 없기에 더더욱 효율적이다.
> 
> 무 : 구현도 간단하고 다른 작업이 끼어들어 변수가 만들어질 가능성이 없기 때문에 Stream 을 통해서 구현하면 효율적이다.

### 비동기 작업 파이프라인 만들기

상점에 할인 서비스를 제공하기로 하고 해당 할인율에 대해 등급을 제공해 총 5가지 할인율을 제공한다고 가정해보자.

이런 경우 할인 율은 바뀔지언정 할인에 대한 등급은 변경이 일어날 일이 별로 없기 때문에 Enum 으로 제공하는게 나쁘지 않아 보인다.

```java
public class Discount {
  public enum Code {
    NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);
    
    private final int percentage;
    
    Code(int percentage) {
      this.percentage = percentage;
    }
  }
}
```

그리고 할인율 적용에 따라 `getPrice()` 메서드도 shopName : price : DiscountCode 형식으로 반환하게 변경해야 한다.

```java
public class Shop {
  public String getPrice(String product) {
    double price = calculatePrice(product);
    Discount.Code code = Discount.Code.values() [
        random.nextInt(Discount.Code.values().length)];
    return String.format("%s:%.2f:%s", name, price, code);
  }
  
  private double calculatePrice(String product) {
    delay();
    return random.nextDouble() * product.charAt(0) + product.charAt(1);
  }
}
```

기본적으로 할인율에 대한 정의가 끝났으므로 할인 서비스를 구현해야 한다. 상점 이름, 할인전 가격, 할인된 가격 정보를 포함하는 `Quote` 클래스를
구현해서 정적 클래스 `parse` 를 통해 정보를 전달 받으면 (getPrice를 통해) 이를 분석해서 할인율을 적용시킨 후 결과를 전달한다.

```java
package com.jk.spring.java.modern.chapter16;

public class Quote {
  private final String shopName;
  private final double price;
  private final Discount.Code discountCode;

  public Quote(String shopName, double price, Discount.Code code) {
    this.shopName = shopName;
    this.price = price;
    this.discountCode = code;
  }

  public static Quote parse(String s) {
    String[] split = s.split(":");
    String shopName = split[0];
    double price = Double.parseDouble(split[1]);
    Discount.Code discountCode = Discount.Code.valueOf(split[2]);
    return new Quote(shopName, price, discountCode);
  }

  public String getShopName() {
    return shopName;
  }

  public double getPrice() {
    return price;
  }

  public Discount.Code getDiscountCode() {
    return discountCode;
  }
}
```

```java
public class CombineClass {
  public static List<String> findPricesCombine(List<Shop> shops, String product) {
    // 상점에 맞게 스레드풀 생성
    Executor executor = Executors.newFixedThreadPool(Math.min(shops.size(), 100),
        runnable -> {
          Thread t = new Thread(runnable);
          t.setDaemon(true);
          return t;
        });

    // 스트림으로 파이프라인 구축
    List<CompletableFuture<String>> priceFutures = shops.stream()
        // 내부에 CompletabeFuture 로 계산식을 감싼다. 이걸 통해 병렬적 처리 -> delay 가 있는 부분
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
        // future 에 대해 완료되었을 경우 parse 작업 진행
        .map(future -> future.thenApply(Quote::parse))
        // quote 로 나온 future 에 대해 한번 더 감싸서 실행한다. -> delay 가 있는 부분
        .map(future -> future.thenCompose(
            quote -> CompletableFuture.supplyAsync(
                () -> Discount.applyDiscount(quote), executor)
        ))
        .collect(toList());

    // 새로운 스트림으로 Stream<CompletableFuture> 를 만들어 전체 로직 수행에 대해 병렬적 처리, 이후 결과 전송
    return priceFutures.stream()
        .map(CompletableFuture::join)
        .collect(toList());
  }
}
```

이와 같이 stream 과 CompletableFuture 를 적절하게 섞어서 사용한다면 병렬적 구현을 조금 더 손쉽게 할 수 있다.

### Future 리플렉션, CompletableFuture 리플렉션