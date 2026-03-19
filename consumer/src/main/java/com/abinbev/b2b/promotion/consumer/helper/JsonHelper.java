package com.abinbev.b2b.promotion.consumer.helper;

import com.abinbev.b2b.promotion.consumer.exception.MapperException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class JsonHelper {

  private static final ObjectMapper mapper = new ObjectMapper();

  private JsonHelper() {};

  public static <T> T map(final Object obj, final TypeReference<T> type) {
    try {
      final String objStr = mapper.writeValueAsString(obj);
      return mapper.readValue(objStr, type);
    } catch (IOException e) {
      throw MapperException.cannotDeserializeJson(
          "Error while retrieving following content to deserialize into the following %s",
          type.getType());
    }
  }
}
