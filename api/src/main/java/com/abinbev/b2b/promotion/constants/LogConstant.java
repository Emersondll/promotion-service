package com.abinbev.b2b.promotion.constants;

public class LogConstant {

  public static class ERROR {
    public static final String INVALID_JWT = "Error on parsing jwt: {}";
    public static final String ASPECT_BEFORE_LOG = "Fail to log before controller";
    public static final String ASPECT_AFTER_LOG = "Fail to log after controller";
    public static final String UNEXPECTED = "Unexpected error";
    public static final String MALFORMED_REQUEST = "Malformed request";
    public static final String HTTP_METHOD_NOT_ALLOWED = "HTTP request method is not allowed";
    public static final String HTTP_INVALID_ARGUMENT = "Invalid argument in the request";
    public static final String HTTP_INVALID_ARGUMENT_TYPE = "Invalid argument type in the request";
    public static final String INVALID_JSON = "Invalid json";
    public static final String NOT_FOUND = "Not found error";
    public static final String FORBIDDEN = "Access denied";
    public static final String INVALID_ACCEPT_LANGUAGE = "Invalid language {}";
    public static final String UNEXPECTED_ACCEPT_LANGUAGE =
        "Unexpected error to validate language {}";
    public static final String INVALID_MAX_SIZE_PROPERTY_VALUE =
        "Fail to parse maxSize property in annotation {}";
  }

  public static class INFO {
    public static final String ASPECT_BEFORE_LOG = "Request to {} - {} with args {}";
    public static final String ASPECT_BEFORE_LOG_WITH_ERROR = "Request to {} - {} with error {}";
    public static final String ASPECT_AFTER_LOG = "Response {} to {} - {} with args {} with {}";
    public static final String EMPTY_ACCEPT_LANGUAGE =
        "Language empty '' the default language will be used";
  }

  public static class WARN {
    public static final String INVALID_START_DATE = "Could not parse startDate {}";
    public static final String INVALID_END_DATE = "Could not parse endDate {}";
  }
}
