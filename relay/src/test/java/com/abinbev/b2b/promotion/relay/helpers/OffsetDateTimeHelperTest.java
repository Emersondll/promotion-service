package com.abinbev.b2b.promotion.relay.helpers;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class OffsetDateTimeHelperTest {

  @Test
  void shouldReturnEpochOffsetDateTime() {
    final OffsetDateTime expected = OffsetDateTime.of(1970, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);

    assertThat(OffsetDateTimeHelper.getEpochDateTime()).isEqualTo(expected);
  }
}
