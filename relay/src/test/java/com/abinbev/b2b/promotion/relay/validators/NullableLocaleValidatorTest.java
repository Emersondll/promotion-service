package com.abinbev.b2b.promotion.relay.validators;

import static org.assertj.core.api.Assertions.assertThat;

import com.abinbev.b2b.promotion.relay.config.properties.NonOfficialSupportedLanguages;
import org.junit.jupiter.api.Test;

public class NullableLocaleValidatorTest {

  private final NonOfficialSupportedLanguages nonOfficialSupportedLanguages =
      new NonOfficialSupportedLanguages() {
        @Override
        public java.util.List<String> getNonOfficialSupportedLanguages() {
          return java.util.Collections.singletonList("en-US");
        }
      };
  private final NullableLocaleValidator validator =
      new NullableLocaleValidator(nonOfficialSupportedLanguages);

  @Test
  public void shouldBeValid() {
    assertThat(validator.isValid("en-US", null)).isTrue();
    assertThat(validator.isValid("EN-CA", null)).isTrue();
    assertThat(validator.isValid("en-us", null)).isTrue();
    assertThat(validator.isValid("", null)).isTrue();
    assertThat(validator.isValid(null, null)).isTrue();
  }

  @Test
  public void shouldBeInvalid() {
    assertThat(validator.isValid(" ", null)).isFalse();
    assertThat(validator.isValid("xx-xx", null)).isFalse();
    assertThat(validator.isValid("enn-US", null)).isFalse();
    assertThat(validator.isValid("enn-USS", null)).isFalse();
    assertThat(validator.isValid("en-USS", null)).isFalse();
  }
}
