package com.abinbev.b2b.promotion.relay.constants;

public abstract class ApiConstants {

  public static final String X_TIMESTAMP_HEADER = "x-timestamp";
  public static final String REQUEST_TRACE_ID_HEADER = "requestTraceId";
  public static final String PARENT_REQUEST_TRACE_ID_HEADER = "parentRequestTraceId";

  public static final String COUNTRY_HEADER = "country";
  public static final String AUTHORIZATION_HEADER = "authorization";

  public static final String DEAD_LETTER_SUFFIX = ".deadLetter";

  public static final String API_LABEL_V1 = "v1";
  public static final String API_LABEL_V2 = "v2";

  public static final String V2_CREATE_PROMOTIONS_POST = "/v2/createPromotions (POST)";

  public static final String V2_DELETE_PROMOTIONS = "/v2/promotionDeleteRequest (DELETE)";

  public static final String API_LABEL_V3_PROMOTIONS = "v3/promotions";

  public static final String V3_CREATE_PROMOTION_MULTIVENDOR_POST =
      "/v3/createMultiVendorPromotions (POST)";

  public static final String V3_DELETE_PROMOTION_MULTIVENDOR =
      "/v3/deleteMultiVendorPromotions (DELETE)";
  // Queue Operations
  public static final String DELETE_OPERATION = "DELETE";
  public static final String POST_OPERATION = "POST";
  public static final String PUT_OPERATION = "PUT";

  public static final String[] COMPRESS_FILTER_PATHS = {"/v2"};

  private ApiConstants() {
    // helper class, only constants variables here
  }
}
