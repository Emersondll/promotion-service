package com.abinbev.b2b.promotion.consumer.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
  private static final ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public static <T> T readJsonFromFilename(String filename, Class<T> klazz) {
    try {
      String json = getJson(filename);
      return objectMapper.readValue(json, klazz);
    } catch (JsonProcessingException e) {
      LOGGER.debug(
          "Error while retrieving following file's content {} to deserialize into the following klazz {}",
          filename,
          klazz.getSimpleName());
      return null;
    }
  }

  private static String getJson(String fileName) {
    return IOUtils.toString(JsonUtils.class.getClassLoader().getResourceAsStream(fileName));
  }

  public static boolean compare(final Object obj1, final Object obj2) {
    try {
      return objectMapper.writeValueAsString(obj1).equals(objectMapper.writeValueAsString(obj2));
    } catch (Exception e) {
      LOGGER.debug("Error parsing objects", e);
      return false;
    }
  }
}
