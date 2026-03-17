package com.abinbev.b2b.promotion.helpers;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeHelper {

  private OffsetDateTimeHelper() {}

  public static OffsetDateTime fromTimestamp(Long timestamp) {

    final ZoneId utcZone = ZoneOffset.UTC;
    if (timestamp == null) {
      return OffsetDateTime.now(utcZone);
    }
    final Instant instant = Instant.ofEpochMilli(timestamp);
    return OffsetDateTime.ofInstant(instant, utcZone);
  }
}
