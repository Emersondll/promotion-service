package com.abinbev.b2b.promotion.consumer.constant;

public abstract class ApiConstants {

  public static final String X_TIMESTAMP_HEADER = "x-timestamp";
  public static final String REQUEST_TRACE_ID_HEADER = "requestTraceId";
  public static final String COUNTRY_HEADER = "country";
  public static final String REQUEST_JWT_AUTHORIZATION = "Authorization";
  public static final String CONTENT_TYPE_HEADER = "Content-Type";
  public static final String CONTENT_TYPE_VALUE = "application/json";
  public static final String X_EXCEPTION_CODE = "x-exception-code";
  public static final String X_EXCEPTION_ROOT_CAUSE = "x-exception-root-cause";
  public static final String HEADER_RETRIES = "retries";
  public static final String VENDOR_ID = "vendorId";
  public static final String PARENT_REQUEST_TRACE_ID = "parentRequestTraceId";
  public static final String TIMESTAMP = "timestamp";
  public static final String METHOD = "method";
  public static final String VERSION = "version";
  public static final String UNDERSCORE = "_";

  public static final String DEAD_LETTER_SUFFIX = ".deadLetter";
  public static final String DLQ_SUFFIX = "_dlq";

  // Queue Operations
  public static final String DELETE_OPERATION = "DELETE";
  public static final String POST_OPERATION = "POST";
  public static final String PUT_OPERATION = "PUT";

  public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final String API_PROMOTION_VERSION_V2 = "v2";
  public static final String API_PROMOTION_VERSION_V3 = "v3";

  private ApiConstants() {
    // helper class, only constants variables here
  }
}
