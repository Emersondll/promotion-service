package com.abinbev.b2b.promotion.exceptions;

import java.util.IllegalFormatException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum IssueEnum {
  REQUEST_HEADER_NOT_VALID(1, "The header %s '%s' is not valid"),

  UNEXPECTED_ERROR(3, "Unexpected error. Please contact system administrator."),

  METHOD_NOT_ALLOWED(4, "%s method is not supported for this request. Supported methods are [%s]"),

  BAD_REQUEST(5, "Malformed Request"),

  JSON_DESERIALIZE_ERROR(6, "Can not deserialize JSON."),

  INVALID_PAGE_NUMBER(9, "Page size should be greater than zero."),

  NO_PROMOTIONS_FOUND(10, "No promotions found."),

  PROMOTIONS_NOT_EXIST(12, "Promotion not exists."),

  JWT_DECODE_ERROR(13, "Error while decoding the token"),
  JWT_TOKEN_INVALID(14, "Unauthorized JWT"),
  COUNTRY_NOT_FOUND(16, "Country '%s' not found."),
  MISSING_AT_LEAST_ONE_REQUIRED_PARAMETER(17, "At least one of the parameters its required '%s'");

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
