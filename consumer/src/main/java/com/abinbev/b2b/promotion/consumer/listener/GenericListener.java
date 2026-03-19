package com.abinbev.b2b.promotion.consumer.listener;

import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import org.slf4j.MDC;

public class GenericListener {

  protected void loggingSetMDC(@SuppressWarnings("rawtypes") final BaseMessage message) {

    MDC.put(ApiConstants.REQUEST_TRACE_ID_HEADER, message.getRequestTraceId());
    MDC.put(ApiConstants.COUNTRY_HEADER, message.getCountry());
  }
}
