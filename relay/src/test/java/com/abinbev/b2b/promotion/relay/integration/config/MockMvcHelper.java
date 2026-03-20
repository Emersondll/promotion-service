package com.abinbev.b2b.promotion.relay.integration.config;

import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import java.util.Calendar;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class MockMvcHelper {

  private static final ObjectMapper mapper = new ObjectMapper();

  public static <T> MockHttpServletRequestBuilder post(final String url, final T body)
      throws JsonProcessingException {
    return MockMvcRequestBuilders.post(url)
        .content(mapper.writeValueAsString(body))
        .contentType(MediaType.APPLICATION_JSON);
  }

  public static HttpHeaders defaultHeaders(final String country) {
    return new HeaderBuilder()
        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
        .withCountry(country)
        .withAuthorization(PromotionMocks.AUTHORIZATION_WITH_VENDOR_ID)
        .withTimesTamp(String.valueOf(Calendar.getInstance().getTimeInMillis()))
        .build();
  }

  public static HttpHeaders defaultHeaders() {
    return defaultHeaders(PromotionMocks.COUNTRY);
  }

  public static HttpHeaders headersInvalidAuthorization() {
    return new HeaderBuilder()
        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
        .withCountry(PromotionMocks.COUNTRY_US)
        .withAuthorization(PromotionMocks.AUTHORIZATION)
        .withTimesTamp(String.valueOf(Calendar.getInstance().getTimeInMillis()))
        .build();
  }
}
