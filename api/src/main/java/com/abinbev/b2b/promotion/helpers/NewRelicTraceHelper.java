package com.abinbev.b2b.promotion.helpers;

import static com.abinbev.b2b.promotion.constants.NewRelicParameters.PAGE;
import static com.abinbev.b2b.promotion.constants.NewRelicParameters.PAGE_SIZE;
import static com.abinbev.b2b.promotion.constants.NewRelicParameters.REQUEST;
import static com.abinbev.b2b.promotion.constants.NewRelicParameters.RESPONSE;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatusCode;

import com.newrelic.api.agent.NewRelic;

public class NewRelicTraceHelper {

  private static final String ARRAY_TO_STRING_DELIMITER = ",";

  private NewRelicTraceHelper() {}

  public static void includeRequestInfo(Object... args) {
    includeParameterArrayAsString(REQUEST.args, args);
  }

  public static void includeResponseInfo(
      HttpStatusCode statusCode,
      Integer page,
      Integer size,
      Object... args) {

    includeParameter(PAGE, page);
    includeParameter(PAGE_SIZE, size);
    includeParameter(RESPONSE.statusCode, statusCode);
    includeParameterArrayAsString(args);
  }

  private static void includeParameterArrayAsString(Object... args) {

    includeParameter(
            REQUEST.args,
            Arrays.stream(args)
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .collect(Collectors.joining(ARRAY_TO_STRING_DELIMITER)));
  }

  public static void includeParameter(String name, String value) {

    if (isNonNullParameter(name, value)) {
      NewRelic.addCustomParameter(name, value);
    }
  }

  public static void includeParameter(String name, Integer value) {

    if (isNonNullParameter(name, value)) {
      NewRelic.addCustomParameter(name, value);
    }
  }

  public static void includeParameter(String name, Boolean value) {

    if (isNonNullParameter(name, value)) {
      NewRelic.addCustomParameter(name, value);
    }
  }

  public static void includeParameter(String name, Object value) {

    if (isNonNullParameter(name, value)) {
      NewRelic.addCustomParameter(name, value.toString());
    }
  }

  private static boolean isNonNullParameter(String name, Object value) {
    return Objects.nonNull(name) && Objects.nonNull(value);
  }
}
