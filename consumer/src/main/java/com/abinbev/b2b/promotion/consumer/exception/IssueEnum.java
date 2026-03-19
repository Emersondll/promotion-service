package com.abinbev.b2b.promotion.consumer.exception;

import java.util.IllegalFormatException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum IssueEnum {
  GENERIC_ERROR(1, "Error Message: '%s' Stack Trace: '%s'"),
  MONGO_ERROR(2, "Mongo Exception: '%s' Stack Trace: '%s'"),
  DATA_ACCESS_ERROR(3, "Data Access Exception: '%s' Stack Trace: '%s'");

  private final Logger logger = LoggerFactory.getLogger(IssueEnum.class);
  private final int code;
  private final String message;

  IssueEnum(final int code, final String message) {
    this.code = code;
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public String getFormattedMessage(final Object... args) {
    if (message == null) {
      return StringUtils.EMPTY;
    }

    try {
      return String.format(message, args);
    } catch (final IllegalFormatException e) {
      logger.warn(e.getMessage(), e);
      return message.replace("%s", StringUtils.EMPTY);
    }
  }
}
