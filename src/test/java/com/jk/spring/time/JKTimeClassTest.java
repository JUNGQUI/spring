package com.jk.spring.time;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
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

	@Test
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

	@Test
	public void temporalAdjusters() {
		LocalDate now = LocalDate.now();
		LocalDate nextSunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		LocalDate endOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());

		LocalDate nextSundayByHandMade = LocalDate.now();

		while(!nextSundayByHandMade.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			nextSundayByHandMade = nextSundayByHandMade.plusDays(1);
		}

		Assertions.assertEquals(nextSundayByHandMade.getDayOfMonth(), nextSunday.getDayOfMonth());
		Assertions.assertEquals(YearMonth.now().atEndOfMonth(), endOfMonth);
	}

	@Test
	public void customTemporalTest() {
		LocalDate now = LocalDate.now();
		LocalDate nextDay = LocalDate.now();
		NextWorkingDay nextWorkingDay = new NextWorkingDay();
		now = now.with(nextWorkingDay);

		if (nextDay.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
			nextDay = nextDay.plusDays(3);
		} else if (nextDay.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			nextDay = nextDay.plusDays(2);
		} else {
			nextDay = nextDay.plusDays(1);
		}

		Assertions.assertEquals(now, nextDay);
	}

	@Test
	public void compareTo() {
		int smaller = Duration.ofHours(7).compareTo(Duration.ofHours(8));
		int same = Duration.ofHours(8).compareTo(Duration.ofHours(8));
		int bigger = Duration.ofHours(9).compareTo(Duration.ofHours(8));

		Assertions.assertEquals(-1, smaller);
		Assertions.assertEquals(0, same);
		Assertions.assertEquals(1, bigger);
	}
}