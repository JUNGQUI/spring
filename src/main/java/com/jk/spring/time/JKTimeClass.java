package com.jk.spring.time;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class JKTimeClass {
	private Date date;
	private LocalDate localDate;
	private LocalDateTime localDateTime;
	private Duration duration;
	private Period period;

	public boolean compareDateLocalDate() {
		return localDate.getYear() == date.getYear()
				&& localDate.getMonthValue() == date.getMonth()
				&& localDate.getDayOfMonth() == date.getDay()
				&& localDate.getDayOfWeek().getValue() == date.getDate();
	}

	public boolean compareDateLocalDateTime() {
		return localDateTime.getYear() == date.getYear()
				&& localDateTime.getMonthValue() == date.getMonth()
				&& localDateTime.getDayOfMonth() == date.getDay()
				&& localDateTime.getDayOfWeek().getValue() == date.getDate()
				&& localDateTime.getHour() == date.getHours()
				&& localDateTime.getMinute() == date.getMinutes()
				&& localDateTime.getSecond() == date.getSeconds();
	}
}
