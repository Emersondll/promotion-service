package com.abinbev.b2b.promotion.relay.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class OffsetDateTimeHelper {

  private OffsetDateTimeHelper() {}

  public static OffsetDateTime getEpochDateTime() {
    return OffsetDateTime.of(LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN), ZoneOffset.UTC);
  }
}
