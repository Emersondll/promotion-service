package com.abinbev.b2b.promotion.helpers;

import java.util.Arrays;

public final class UrlHelper {

  private static final String[] TRUSTED_URLS = {
    "/swagger-ui.html", "/webjars", "/webjars**", "/v2/api-docs", "/swagger-resources", "/actuator"
  };

  private UrlHelper() {

    super();
  }

  public static boolean isTrustedUrl(final String urlPath) {

    return Arrays.asList(TRUSTED_URLS).stream().anyMatch(urlPath::startsWith);
  }
}
