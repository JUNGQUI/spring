### 새로운 날짜와 시간 API

Date : 1900년도부터 시작, 0부터 시작하는 month 인덱스
calendar : 0부터 시작하는 month 인덱스 여전, DateFormat 과 같은 기능 작동하지 않음

이러한 이슈들로 인해 새로운 날짜와 시간과 관련된 API 가 필요했고 그렇게 나온게 `LocalDate`, `LocalTime`, `LocalDateTime` 이다.

```java
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

public class newDateAndTimeAPI {

  public void newDate() {
    LocalDate date = LocalDate.of(2021, 8, 1);
    int year = date.getYear();
    Month month = date.getMonth();  // 0부터가 아닌 8월(AUGUST) 가 들어옴
    int day = date.getDayOfMonth();
    DayOfWeek dow = date.getDayOfWeek();
    int len = date.lengthOfMonth();
    boolean leap = date.isLeapYear(); // 윤년 여부
  }

  public void newTime() {
    LocalTime time = LocalTime.of(13, 45, 20);
    int hour = time.getHour();
    int minute = time.getMinute();
    int second = time.getSecond();
  }
}
```

이와 같이 새로운 LocalDate, LocalTime 의 경우 팩토리 패턴으로 of 를 사용해서 새로운 객체를 생성하며 이전의 Date 에 비해 훨씬
수월하게 작업을 할 수 있다.
(가령, 비교 연산자의 경우 isAfter 등을 이용 할 수 있다)


### 날짜와 시간 조합

`LocalDateTime` 의 경우 `LocalDate` 와 `LocalTime` 의 조합으로 이루어진 복합 클래스다. 이를 이용하면 시간만을, 날짜만을,
시간과 날짜 모두를 각기 표현이 가능하다.

기존의 Date 를 이용해서 표현을 할 경우 변수명을 가지고 이 변수의 목적을 추측할 수 밖에 없었던 반면, Local 류들을 이용하게 되면
이전과 달리 변수 타입으로 목적을 이해할 수 있고 해당 클래스에서 사용 가능한 여러 메서드 또한 사용 가능하기에 훌륭한 대체가 가능하다.

Duration 과 Period

시간과 시간 사이의 값을 LocalDate 나 LocalTime 으로 표현하기는 어렵다. 표현을 한다면 `TemporalField` 인 `ChronoUnit` 을
이용해서 날, 달, 연 등을 구할 수 있다.

```java
import java.time.LocalDate;

public class JKLocalDateCal {

  public long calculation(LocalDate a, LocalDate b) {
    return ChronoUnit.DAYS.between(startDate, endDate);
  }
}
```

이런 상황에 대해 Duration 과 Period 가 있다.

```java
public class JKDuration {
  public Duration calcBetween(LocalDateTime a, LocalDateTime b) {
    return Duration.between(a, b);
  }
}
```

```text
LocalDate a = LocalDate.of(2021, 9, 13);
LocalDate b = LocalDate.of(2021, 9, 14);

calcBetween(a, b);  // Duration.of(1, ChronoUnit.DAYS), "PT24H"
```

이러한 값을 이용하면 명확하게 시간등의 단위를 명시해주면서 사용이 가능하다. Duration, Period 를 사용 할 경우 구분이 명확해지기 때문에
다른 개발자가 보고 판단하기 쉽고 내부 메소드를 이용해서 원하고자 하는 값이나 변경도 용이하다.

다만, 이러한 기능을 ChronoUnit 과 같이 쓰고자 변경을 한다면 조금 복잡해진다.

```java
public class JKTestClass {
  public void calculationLocalDate() {
    LocalDate a = LocalDate.of(2021, 8, 1);
    LocalDate b = LocalDate.of(2255, 5, 2);

    long betweenDays = ChronoUnit.DAYS.between(a, b);
    long betweenDaysByPeriod = this.calculateMonthDays(a, b);

    Assertions.assertEquals(betweenDaysByPeriod, betweenDays);
  }

  private long calculateMonthDays(LocalDate startDate, LocalDate endDate) {
    Period period = Period.between(startDate, endDate);
    int startYear = startDate.getYear();
    LocalDate startMonth = LocalDate.from(startDate);
    int yearDays = 0;
    int monthDays = 0;

    for (int i = 0; i < period.getYears(); i++) {
      yearDays += LocalDate.of(startYear + i, 12, 31).getDayOfYear();
    }

    for (int i = 1; i < period.getMonths(); i++) {
      startMonth = startMonth.plusMonths(1);
      monthDays += YearMonth.from(startMonth).atEndOfMonth().getDayOfMonth();

      if (startMonth.getMonthValue() == 12) {
        startMonth = startMonth.plusYears(period.getYears());
      }
    }

    monthDays += YearMonth.from(startDate).atEndOfMonth().getDayOfMonth() - startDate.getDayOfMonth() + 1;
    Duration.of(1, ChronoUnit.DAYS);
    return yearDays
        + monthDays
        + (period.getDays());
  }
}
```

테스트 코드에 있는 부분인데, 윤달, 달이 달라질 때 변경되는 일수 등을 고려하여 두 날짜간 차이를 구하는 부분이다.
period 를 이용하면 단순히 몇년, 몇개월, 몇일의 차이만 난다고 표현해줄뿐이고 일수에 대한 차이도 1개월 단위를 넘어가면 윗단계 단위인
개월로 변경되기 때문에 정확하게 얼만큼의 일자가 다른지를 확인하기는 어렵다.

이를 풀어내려면 위와 같이 모든 요소를 고려한 별도의 메소드가 필요할 정도로 복잡해진다.

이와 같이 무조건적으로 사용하는게 아닌 특정 목적에 부합되게 Duration 과 Period 를 적절히 섞어서 사용 해야 한다.