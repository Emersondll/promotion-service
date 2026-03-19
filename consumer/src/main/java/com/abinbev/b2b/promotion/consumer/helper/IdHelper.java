package com.abinbev.b2b.promotion.consumer.helper;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class IdHelper {
  private static final String ID_SEPARATOR = ":";

  private IdHelper() {}

  public static String buildId(final String... keys) {
    return UUID.nameUUIDFromBytes(buildKey(keys).getBytes(StandardCharsets.UTF_16)).toString();
  }

  private static String buildKey(final String... keys) {
    return String.join(ID_SEPARATOR, Arrays.asList(keys));
  }
}
