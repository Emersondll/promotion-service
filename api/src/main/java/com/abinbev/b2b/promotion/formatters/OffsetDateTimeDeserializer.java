package com.abinbev.b2b.promotion.formatters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.OffsetDateTime;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

  @Override
  public OffsetDateTime deserialize(
      final JsonParser jsonParser, final DeserializationContext deserializationContext)
      throws IOException {
    return OffsetDateTime.parse(jsonParser.getText());
  }
}
