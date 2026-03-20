package com.abinbev.b2b.promotion.consumer.helper;

import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import java.util.HashMap;
import java.util.Map;

public class HeaderMockHelper {

  public static Map<String, String> mockHeaderRequest() {

    final Map<String, String> headers = new HashMap<>();
    headers.put(ApiConstants.REQUEST_TRACE_ID_HEADER, PromotionMocks.MOCKED_REQUEST_TRACE_ID);
    headers.put(ApiConstants.COUNTRY_HEADER, PromotionMocks.MOCKED_COUNTRY);
    headers.put(ApiConstants.REQUEST_JWT_AUTHORIZATION, PromotionMocks.MOCKED_AUTHORIZATION);

    return headers;
  }
}
