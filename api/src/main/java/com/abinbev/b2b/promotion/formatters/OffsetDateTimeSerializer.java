package com.abinbev.b2b.promotion.formatters;

import static com.abinbev.b2b.promotion.constants.ApiConstants.DATE_PATTERN;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeSerializer extends JsonSerializer<OffsetDateTime> {

  @Override
  public void serialize(
      final OffsetDateTime offsetDateTime,
      final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider)
      throws IOException {
    if (offsetDateTime != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
      jsonGenerator.writeString(formatter.format(offsetDateTime));
    } else {
      jsonGenerator.writeNull();
    }
  }
}
