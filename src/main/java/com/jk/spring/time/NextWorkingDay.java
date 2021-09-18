package com.jk.spring.time;

import java.time.DayOfWeek;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;

public class NextWorkingDay implements TemporalAdjuster {

  @Override
  public Temporal adjustInto(Temporal temporal) {
    DayOfWeek dayOfWeek = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
    return temporal.plus(extractPlusDay(dayOfWeek), ChronoUnit.DAYS);
  }

  private int extractPlusDay(DayOfWeek now) {
    if (now.equals(DayOfWeek.FRIDAY)) {
      return 3;
    }
    if (now.equals(DayOfWeek.SATURDAY)) {
      return 2;
    }

    return 1;
  }
}
