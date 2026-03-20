package com.abinbev.b2b.promotion.relay.formatters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.relay.config.properties.NonOfficialSupportedLanguages;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocaleDeserializerTest {

  private static final String EN_ES_LANGUAGE = "en-ES";
  private static final String EN_ES_LANGUAGE_ALL_UPPERCASE = "EN-ES";
  private static final String EN_ES_LANGUAGE_WITH_LOWERCASE = "EN-es";
  private static final String EMPTY_LANGUAGE = "";
  private static final String BLANK_LANGUAGE = " ";

  private LocaleDeserializer localeDeserializer;
  private NonOfficialSupportedLanguages nonOfficialSupportedLanguages;

  @BeforeEach
  public void setUp() {
    nonOfficialSupportedLanguages = mock(NonOfficialSupportedLanguages.class);
    localeDeserializer = new LocaleDeserializer(nonOfficialSupportedLanguages);
  }

  @Test
  void shouldReturnNormalizedValueWhenValueIsValid() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(EN_ES_LANGUAGE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Collections.emptyList());

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EN_ES_LANGUAGE)));
  }

  @Test
  void shouldReturnNormalizedValueWhenValueIsNonOfficial() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(EN_ES_LANGUAGE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Arrays.asList("en-ES"));

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EN_ES_LANGUAGE)));
  }

  @Test
  void shouldReturnNormalizedValueWhenValueIsNonOfficialAndHasMultipleOptions() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(EN_ES_LANGUAGE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Arrays.asList("en-GB", "en-ES"));

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EN_ES_LANGUAGE)));
  }

  @Test
  void shouldReturnNormalizedValueWhenValueIsNonOfficialAndHasNoOptions() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(EN_ES_LANGUAGE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Arrays.asList("en-US", "en-CA"));

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EN_ES_LANGUAGE)));
  }

  @Test
  void shouldReturnNormalizedValueWhenValueHasUppercaseCharactersOnLanguage() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(EN_ES_LANGUAGE_ALL_UPPERCASE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Arrays.asList("en-ES"));

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EN_ES_LANGUAGE_ALL_UPPERCASE)));
  }

  @Test
  void shouldReturnNormalizedValueWhenValueHasLowercaseCharactersOnLocation() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(EN_ES_LANGUAGE_WITH_LOWERCASE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Collections.emptyList());

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EN_ES_LANGUAGE_WITH_LOWERCASE)));
  }

  @Test
  void shouldReturnEmptyValueWhenValueIsEmpty() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(EMPTY_LANGUAGE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Collections.emptyList());

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EMPTY_LANGUAGE)));
  }

  @Test
  void shouldReturnBlankValueWhenValueIsBlank() throws IOException {
    JsonParser jsonParser = mock(JsonParser.class);
    DeserializationContext deserializationContext = mock(DeserializationContext.class);

    when(jsonParser.getText()).thenReturn(BLANK_LANGUAGE);
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(Collections.emptyList());

    assertThat(
        localeDeserializer.deserialize(jsonParser, deserializationContext),
        is(equalTo(EMPTY_LANGUAGE)));
  }
}
