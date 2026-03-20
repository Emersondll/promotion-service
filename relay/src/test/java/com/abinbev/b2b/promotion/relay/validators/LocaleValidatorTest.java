package com.abinbev.b2b.promotion.relay.validators;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.abinbev.b2b.promotion.relay.config.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.relay.domain.Translation;
import com.abinbev.b2b.promotion.relay.templates.TranslationTemplate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class LocaleValidatorTest {

  private final NonOfficialSupportedLanguages nonOfficialSupportedLanguages =
      new NonOfficialSupportedLanguages() {
        @Override
        public List<String> getNonOfficialSupportedLanguages() {
          return Collections.singletonList("en-US");
        }
      };
  private final LocaleValidator validator = new LocaleValidator(nonOfficialSupportedLanguages);

  @BeforeAll
  public static void fixtureSetUp() {
    FixtureFactoryLoader.loadTemplates(TranslationTemplate.class.getPackageName());
  }

  @Test
  void shouldBeValid() {

    final Translation translation =
        Fixture.from(Translation.class).gimme(TranslationTemplate.US_VALID);

    assertThat(validator.isValid(Collections.singletonMap("en-US", translation), null)).isTrue();
    assertThat(validator.isValid(Collections.singletonMap("EN-CA", translation), null)).isTrue();
    assertThat(validator.isValid(Collections.singletonMap("en-us", translation), null)).isTrue();
    assertThat(validator.isValid(null, null)).isTrue();
  }

  @Test
  void shouldBeInvalid() {

    final Translation translation =
        Fixture.from(Translation.class).gimme(TranslationTemplate.US_VALID);

    assertThat(validator.isValid(Collections.singletonMap(" ", translation), null)).isFalse();
    assertThat(validator.isValid(Collections.singletonMap("xx-xx", translation), null)).isFalse();
    assertThat(validator.isValid(Collections.singletonMap("enn-US", translation), null)).isFalse();
    assertThat(validator.isValid(Collections.singletonMap("enn-USS", translation), null)).isFalse();
    assertThat(validator.isValid(Collections.singletonMap("en-USS", translation), null)).isFalse();
  }
}
