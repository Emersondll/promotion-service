package com.abinbev.b2b.promotion.relay.formatters;

import com.abinbev.b2b.promotion.relay.config.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.relay.helpers.LocaleHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class LocaleDeserializer extends JsonDeserializer<String> {

  private final NonOfficialSupportedLanguages nonOfficialSupportedLanguages;

  @Autowired
  public LocaleDeserializer(NonOfficialSupportedLanguages nonOfficialSupportedLanguages) {
    this.nonOfficialSupportedLanguages = nonOfficialSupportedLanguages;
  }

  @Override
  public String deserialize(
      final JsonParser jsonParser, final DeserializationContext deserializationContext)
      throws IOException {
    String value = jsonParser.getText().trim();
    return LocaleHelper.normalizeValid(
        value, nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages());
  }
}
