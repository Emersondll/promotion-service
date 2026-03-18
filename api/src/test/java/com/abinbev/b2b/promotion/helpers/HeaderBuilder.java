package com.abinbev.b2b.promotion.helpers;

import com.abinbev.b2b.promotion.constants.ApiConstants;
import org.springframework.http.HttpHeaders;

public class HeaderBuilder {

  private final HttpHeaders headers;

  public HeaderBuilder() {
    headers = new HttpHeaders();
  }

  public static HeaderBuilder builder() {
    return new HeaderBuilder();
  }

  public HttpHeaders build() {
    return headers;
  }

  public HeaderBuilder withRequestTraceId(final String requestTraceId) {
    headers.add(ApiConstants.REQUEST_TRACE_ID_HEADER, requestTraceId);
    return this;
  }

  public HeaderBuilder withAcceptLanguage(final String acceptLanguage) {
    headers.add(ApiConstants.ACCEPT_LANGUAGE_HEADER, acceptLanguage);
    return this;
  }

  public HeaderBuilder withCountry(final String country) {
    headers.add(ApiConstants.COUNTRY_HEADER, country);
    return this;
  }

  public HeaderBuilder withAuthorization(final String authorization) {
    headers.add(ApiConstants.AUTHORIZATION_HEADER, authorization);
    return this;
  }
}
