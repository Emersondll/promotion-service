package com.abinbev.b2b.promotion.relay.exceptions;

import java.util.IllegalFormatException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Enum containing error messages */
public enum IssueEnum {

  // Note: the comments are placed just to prevent auto formatting add the constants to the same
  // line.
  // General error codes 1~100.

  REQUEST_HEADER_NOT_VALID(1, "The header %s '%s' is not valid."),
  AUTHENTICATION_HEADER_NOT_VALID(2, "Invalid authentication."),
  BAD_REQUEST_ERROR(3, "Bad Request. Please contact system administrator. '%s'"),
  COUNTRY_NOT_FOUND(4, "Country '%s' not found."),
  MALFORMED_REQUEST_PAYLOAD(5, "Malformed request data."),
  JSON_DESERIALIZE_ERROR(6, "Can not deserialize JSON."),
  METHOD_NOT_ALLOWED(7, "%s method is not supported for this request. Supported methods are [%s]"),
  // Business error codes 101~200.
  VENDOR_ID_NOT_FOUND(101, "Default vendor id not found for country %s.");

  private int code;
  private String message;

  // not static because ENUMS are initialized before static fields by JVM
  private final Logger logger = LoggerFactory.getLogger(IssueEnum.class);

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
