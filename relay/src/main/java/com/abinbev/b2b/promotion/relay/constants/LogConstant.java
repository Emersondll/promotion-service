package com.abinbev.b2b.promotion.relay.constants;

public class LogConstant {

  public static class ERROR {
    public static final String INVALID_JWT = "Error on parsing jwt: {}";
    public static final String ASPECT_BEFORE_LOG = "Fail to log before controller";
    public static final String ASPECT_AFTER_LOG = "Fail to log after controller";
    public static final String ASPECT_AFTER_QUEUE_LOG = "Fail to log after controller";

    public static final String UNEXPECTED = "Unexpected error";
    public static final String MALFORMED_REQUEST = "Malformed request";
    public static final String HTTP_METHOD_NOT_ALLOWED = "HTTP request method is not allowed";
    public static final String HTTP_INVALID_ARGUMENT = "Invalid argument in the request";
    public static final String INVALID_JSON = "Invalid json";
    public static final String NOT_FOUND = "Not found error";
    public static final String FORBIDDEN = "Access denied";
    public static final String INVALID_ACCEPT_LANGUAGE = "Invalid language {}";
  }

  public static class INFO {
    public static final String ASPECT_BEFORE_LOG = "Request to {} - {} with args {}";
    public static final String ASPECT_BEFORE_LOG_WITH_ERROR = "Request to {} - {} with error {}";
    public static final String ASPECT_AFTER_LOG = "Response {} to {} - {} with args {}";
    public static final String ASPECT_AFTER_QUEUE = "Message sent to queue {} from request {} - {}";
    public static final String POST_PROMOTION_MULTI_VENDOR =
        "POST promotions multi vendor for vendor {} with {} elements";
    public static final String DELETE_PROMOTION_MULTI_VENDOR =
        "DELETE promotions multi vendor for vendor {} with {} promotions";
  }

  public static class WARN {
    public static final String INVALID_START_DATE = "Could not parse startDate {}";
    public static final String INVALID_END_DATE = "Could not parse endDate {}";
    public static final String INVALID_MINIMUM_START_DATE =
        "Start date cannot be before epoch date {} startDate: {}";
  }
}
