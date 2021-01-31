package com.jk.spring.time;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JKTimeClassTest {

	@Test
	public void dateTest() {
		JKTimeClass jkTimeClass = new JKTimeClass();

		int year = 2020, month = 1,
				dayOfMonth1 = 1, dayOfMonth2 = 2,
				hour1 = 0, hour2 = 23,
				minute1 = 0, minute2 = 59,
				second1 = 0, second2 = 59;

		int secondOfDiff = (hour2*3600 + minute2*60 + second2) - (hour1*3600 + minute1*60 + second1) + (dayOfMonth2 - dayOfMonth1) * 24 * 3600;

		LocalDateTime localDateTime1 = LocalDateTime.of(year, month, dayOfMonth1, hour1, minute1, second1);
		LocalDateTime localDateTime2 = LocalDateTime.of(year, month, dayOfMonth2, hour2, minute2, second2);

		jkTimeClass.setDate(Date.from(localDateTime1.toInstant(ZoneOffset.UTC)));
		jkTimeClass.setLocalDate(localDateTime1.toLocalDate());
		jkTimeClass.setLocalDateTime(localDateTime1);
		jkTimeClass.setDuration(Duration.between(localDateTime1, localDateTime2));
		jkTimeClass.setPeriod(Period.between(localDateTime1.toLocalDate(), localDateTime2.toLocalDate()));

		Assertions.assertEquals(jkTimeClass.getDate().getYear(), year - 2000 + 100);
		Assertions.assertEquals(jkTimeClass.getDate().getMonth(), month - 1);
		Assertions.assertEquals(jkTimeClass.getDate().getDate(), dayOfMonth1);
		Assertions.assertEquals(jkTimeClass.getDate().getHours(), hour1 + 9);
		Assertions.assertEquals(jkTimeClass.getDate().getMinutes(), minute1);
		Assertions.assertEquals(jkTimeClass.getDate().getSeconds(), second1);

		Assertions.assertEquals(jkTimeClass.getLocalDate().getYear(), year);
		Assertions.assertEquals(jkTimeClass.getLocalDate().getMonthValue(), month);
		Assertions.assertEquals(jkTimeClass.getLocalDate().getDayOfMonth(), dayOfMonth1);

		Assertions.assertEquals(jkTimeClass.getLocalDateTime().getHour(), hour1);
		Assertions.assertEquals(jkTimeClass.getLocalDateTime().getMinute(), minute1);
		Assertions.assertEquals(jkTimeClass.getLocalDateTime().getSecond(), second1);

		Assertions.assertEquals(jkTimeClass.getDuration().getSeconds()/3600, secondOfDiff/3600);
		Assertions.assertEquals(jkTimeClass.getDuration().getSeconds()%3600, secondOfDiff%3600);

		Assertions.assertEquals(jkTimeClass.getPeriod().getDays(), dayOfMonth2 - dayOfMonth1);
	}
}