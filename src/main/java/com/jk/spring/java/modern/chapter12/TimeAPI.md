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

