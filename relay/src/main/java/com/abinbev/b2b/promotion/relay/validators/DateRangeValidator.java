package com.abinbev.b2b.promotion.relay.validators;

import com.abinbev.b2b.promotion.relay.constants.LogConstant;
import com.abinbev.b2b.promotion.relay.helpers.OffsetDateTimeHelper;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateRangeValidator {
  protected static final Logger LOGGER = LoggerFactory.getLogger(DateRangeValidator.class);

  protected boolean shouldValidateTime;

  protected boolean isDateRangeValid(
      final String startDateRequest,
      boolean isStartDateNullable,
      final String endDateRequest,
      boolean isEndDateNullable) {
    OffsetDateTime startDate;
    OffsetDateTime endDate;

    final var minimumDate = OffsetDateTimeHelper.getEpochDateTime();

    if (StringUtils.isBlank(startDateRequest)) {
      return isStartDateNullable;
    }
    try {
      startDate = parseDate(startDateRequest);
    } catch (final DateTimeParseException e) {
      LOGGER.warn(LogConstant.WARN.INVALID_START_DATE, startDateRequest, e);
      return false;
    }

    if (startDate.isBefore(minimumDate)) {
      LOGGER.warn(LogConstant.WARN.INVALID_MINIMUM_START_DATE, LocalDate.EPOCH, startDate);
      return false;
    }

    if (StringUtils.isBlank(endDateRequest)) {
      return isEndDateNullable;
    }

    try {
      endDate = parseDate(endDateRequest);
    } catch (final DateTimeParseException e) {
      LOGGER.warn(LogConstant.WARN.INVALID_END_DATE, endDateRequest, e);
      return false;
    }

    return startDate.isBefore(endDate);
  }

  protected OffsetDateTime parseDate(final String date) {
    return shouldValidateTime
        ? OffsetDateTime.parse(date)
        : OffsetDateTime.of(LocalDate.parse(date).atStartOfDay(), ZoneOffset.UTC);
  }
}
