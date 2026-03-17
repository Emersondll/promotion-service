package com.abinbev.b2b.promotion.constants;

import java.util.Arrays;
import java.util.List;

public abstract class ApiConstants {

  public static final String REQUEST_TRACE_ID_HEADER = "requestTraceId";
  public static final String COUNTRY_HEADER = "country";
  public static final String AUTHORIZATION_HEADER = "authorization";
  public static final String X_TIMESTAMP_HEADER = "x-timestamp";
  public static final String ACCEPT_LANGUAGE_HEADER = "accept-language";
  public static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final String REQUEST_PARAM_B2B = "b2b";
  public static final String ROLE_WRITE = "WRITE";
  public static final String ROLE_READ = "READ";
  public static final List<String> ROLES_M2M = Arrays.asList(ROLE_READ, ROLE_WRITE);

  private ApiConstants() {}
}
