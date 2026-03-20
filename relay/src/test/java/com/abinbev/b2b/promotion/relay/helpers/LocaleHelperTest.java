package com.abinbev.b2b.promotion.relay.helpers;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class LocaleHelperTest {

  private final List<String> nonOfficialSupportedLanguagesList = Collections.singletonList("en-ES");

  @Test
  void shouldBeValidLocale() {
    assertThat(LocaleHelper.isValidOrEmpty("en-ES", nonOfficialSupportedLanguagesList)).isTrue();
    assertThat(LocaleHelper.isValidOrEmpty("en-US", nonOfficialSupportedLanguagesList)).isTrue();
    assertThat(LocaleHelper.isValidOrEmpty("EN-US", nonOfficialSupportedLanguagesList)).isTrue();
    assertThat(LocaleHelper.isValidOrEmpty("en-us", nonOfficialSupportedLanguagesList)).isTrue();
    assertThat(LocaleHelper.isValidOrEmpty("EN-us", nonOfficialSupportedLanguagesList)).isTrue();
    assertThat(LocaleHelper.isValidOrEmpty("", nonOfficialSupportedLanguagesList)).isTrue();
    assertThat(LocaleHelper.isValidOrEmpty(null, nonOfficialSupportedLanguagesList)).isTrue();
  }

  @Test
  void shouldBeInvalidLocale() {
    assertThat(LocaleHelper.isValidOrEmpty("enn-US", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("EN-USS", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("en-uus", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("ENN-us", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty(" ", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("xx-xx", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("xx-", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("xx-XX", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("xx-XX", nonOfficialSupportedLanguagesList)).isFalse();
    assertThat(LocaleHelper.isValidOrEmpty("en-XX", nonOfficialSupportedLanguagesList)).isFalse();
  }

  @Test
  void shouldNormalizeLocale() {
    assertThat(LocaleHelper.normalizeValid("en-US", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-US");
    assertThat(LocaleHelper.normalizeValid("en-US", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-US");
    assertThat(LocaleHelper.normalizeValid("en-US", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-US");
    assertThat(LocaleHelper.normalizeValid("EN-US", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-US");
    assertThat(LocaleHelper.normalizeValid("en-us", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-US");
    assertThat(LocaleHelper.normalizeValid("EN-us", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-US");
  }

  @Test
  void shouldNotNormalizeLocale() {
    assertThat(LocaleHelper.normalizeValid("enn-US", nonOfficialSupportedLanguagesList))
        .isEqualTo("enn-US");
    assertThat(LocaleHelper.normalizeValid("EN-USS", nonOfficialSupportedLanguagesList))
        .isEqualTo("EN-USS");
    assertThat(LocaleHelper.normalizeValid("en-uus", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-uus");
    assertThat(LocaleHelper.normalizeValid("ENN-us", nonOfficialSupportedLanguagesList))
        .isEqualTo("ENN-us");
    assertThat(LocaleHelper.normalizeValid(" ", nonOfficialSupportedLanguagesList)).isEqualTo(" ");
    assertThat(LocaleHelper.normalizeValid("xx-xx", nonOfficialSupportedLanguagesList))
        .isEqualTo("xx-xx");
    assertThat(LocaleHelper.normalizeValid("xx-", nonOfficialSupportedLanguagesList))
        .isEqualTo("xx-");
    assertThat(LocaleHelper.normalizeValid("xx-XX", nonOfficialSupportedLanguagesList))
        .isEqualTo("xx-XX");
    assertThat(LocaleHelper.normalizeValid("xx-XX", nonOfficialSupportedLanguagesList))
        .isEqualTo("xx-XX");
    assertThat(LocaleHelper.normalizeValid("en-XX", nonOfficialSupportedLanguagesList))
        .isEqualTo("en-XX");
  }

  @Test
  void shouldBeValidLocaleSet() {
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("en-US", "pt-BR", "EN-US", "pt-br", "en-ca", "EN-us"),
                nonOfficialSupportedLanguagesList))
        .isTrue();
  }

  @Test
  void shouldBeInvalidLocaleSet() {
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("enn-US", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("EN-USS", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("en-uus", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("ENN-us", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(Set.of(" ", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("xx-xx", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("xx-", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("xx-XX", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("xx-XX", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
    assertThat(
            LocaleHelper.isLocaleSetValid(
                Set.of("en-XX", "pt-BR"), nonOfficialSupportedLanguagesList))
        .isFalse();
  }
}
