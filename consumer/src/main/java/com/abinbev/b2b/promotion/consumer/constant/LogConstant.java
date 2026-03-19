package com.abinbev.b2b.promotion.consumer.constant;

public class LogConstant {

  public static class ERROR {
    public static final String GET_REQUEST_TRACE_ID_FAILED =
        "Not possible to retrieve the requestTraceId from message";
    public static final String PROCESS_MESSAGE_FAIL = "Fail to process message {}";
  }

  public static class INFO {
    public static final String INIT_POST_PROMOTION_SINGLE_VENDOR =
        "POST promotions single vendor received with body {}";
    public static final String INIT_POST_PROMOTION_MULTI_VENDOR =
        "POST promotions multi vendor received with vendor {} and body {}";
    public static final String END_POST_PROMOTION_SINGLE_VENDOR =
        "POST promotions single vendor processed";
    public static final String END_POST_PROMOTION_MULTI_VENDOR =
        "POST promotions multi vendor processed";

    public static final String INIT_DELETE_PROMOTION_SINGLE_VENDOR =
        "DELETE promotions single vendor received with body {}";
    public static final String INIT_DELETE_PROMOTION_MULTI_VENDOR =
        "DELETE promotions multi vendor received with vendor {} and body {}";
    public static final String END_DELETE_PROMOTION_SINGLE_VENDOR =
        "DELETE promotions single vendor processed";
    public static final String END_DELETE_PROMOTION_MULTI_VENDOR =
        "DELETE promotions multi vendor processed";

    public static final String DEPRECATED_PROMOTION_VERSION =
        "Deprecated promotion message received: {}";

    public static final String PROMOTION_ACCOUNT_SINGLE_VENDOR_DISABLED =
        "Promotion accounts single vendor with operation {} not saved by operation or toggle";
    public static final String PROMOTION_ACCOUNT_MULTI_VENDOR_DISABLED =
        "Promotion accounts multi vendor with operation {} not saved by operation or toggle";

    public static final String INIT_POST_PROMOTION_ACCOUNT_MULTI_VENDOR =
        "Deals multi vendor received with vendor {} and {} elements and body {}";
    public static final String INIT_POST_PROMOTION_ACCOUNT_SINGLE_VENDOR =
        "Deals single vendor received with {} elements and body {}";
    public static final String END_POST_PROMOTION_ACCOUNT_MULTI_VENDOR =
        "Deals multi vendor processed";
    public static final String END_POST_PROMOTION_ACCOUNT_SINGLE_VENDOR =
        "Deals single vendor processed";
  }

  public static class WARN {
    public static final String RETRYING_MESSAGE = "Message retried with code {} for the queue: {}";
    public static final String RETRYING_MESSAGE_EXHAUSTED =
        "Retries exhausted with code {} for the queue {}";
  }
}
