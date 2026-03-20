package com.abinbev.b2b.promotion.relay.validators;

import static org.assertj.core.api.Assertions.assertThat;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.abinbev.b2b.promotion.relay.templates.DateRangeRequestMockImplTemplate;
import com.abinbev.b2b.promotion.relay.templates.ValidDateRangeMockImplTemplate;
import com.abinbev.b2b.promotion.relay.validators.annotations.DateRangeRequestMockImpl;
import com.abinbev.b2b.promotion.relay.validators.annotations.ValidDateRangeMockImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DateWindowValidatorTest {

  private final DateWindowValidator validator = new DateWindowValidator();

  @BeforeAll
  public static void fixtureSetup() {
    FixtureFactoryLoader.loadTemplates(ValidDateRangeMockImplTemplate.class.getPackageName());
    FixtureFactoryLoader.loadTemplates(DateRangeRequestMockImplTemplate.class.getPackageName());
  }

  @BeforeEach
  public void initializer() {
    final ValidDateRangeMockImpl validateTime =
        Fixture.from(ValidDateRangeMockImpl.class)
            .gimme(ValidDateRangeMockImplTemplate.VALIDATE_TIME);

    validator.initialize(validateTime);
  }

  @Test
  void shouldBeValidWithAllTrue() {
    final DateRangeRequestMockImpl request =
        Fixture.from(DateRangeRequestMockImpl.class).gimme(DateRangeRequestMockImplTemplate.BASE);

    final ValidDateRangeMockImpl allTrue =
        Fixture.from(ValidDateRangeMockImpl.class).gimme(ValidDateRangeMockImplTemplate.ALL_TRUE);

    validator.initialize(allTrue);

    assertThat(validator.isValid(request, null)).isTrue();
  }

  @Test
  void shouldBeValidWithValidateTimeAsFalse() {
    final DateRangeRequestMockImpl request =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.BASE_ONLY_DATE);

    final ValidDateRangeMockImpl startDateAndEndDate =
        Fixture.from(ValidDateRangeMockImpl.class)
            .gimme(ValidDateRangeMockImplTemplate.START_DATE_AND_END_DATE);

    validator.initialize(startDateAndEndDate);

    assertThat(validator.isValid(request, null)).isTrue();
  }

  @Test
  void shouldBeValidWhenStartDateOrEndDateIsNullWithAllTrue() {
    final DateRangeRequestMockImpl request =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.NULL_DATES);

    final ValidDateRangeMockImpl allTrue =
        Fixture.from(ValidDateRangeMockImpl.class).gimme(ValidDateRangeMockImplTemplate.ALL_TRUE);

    validator.initialize(allTrue);

    assertThat(validator.isValid(request, null)).isTrue();
  }

  @Test
  void shouldNotBeValidWhenStartDateOrEndDateHasWrongFormatWithValidateTimeAsFalse() {
    final DateRangeRequestMockImpl requestWithWrongStartDateFormat =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.WRONG_START_DATE_FORMAT);

    final DateRangeRequestMockImpl requestWithWrongEndDateFormat =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.WRONG_END_DATE_FORMAT);

    assertThat(validator.isValid(requestWithWrongStartDateFormat, null)).isFalse();
    assertThat(validator.isValid(requestWithWrongEndDateFormat, null)).isFalse();
  }

  @Test
  void shouldNotBeValidWhenStartDateOrEndDateIsNullWithValidateTimeAsTrue() {
    final DateRangeRequestMockImpl requestWithNullStartDate =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.NULL_START_DATE);

    final DateRangeRequestMockImpl requestWithNullEndDate =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.NULL_END_DATE);

    assertThat(validator.isValid(requestWithNullStartDate, null)).isFalse();
    assertThat(validator.isValid(requestWithNullEndDate, null)).isFalse();
  }

  @Test
  void shouldNotBeValidWhenStartDateOrEndDateHasWrongFormatWithValidateTimeAsTrue() {
    final DateRangeRequestMockImpl requestWithWrongStartDate =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.WRONG_START_DATE_FORMAT);

    final DateRangeRequestMockImpl requestWithWrongEndDate =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.WRONG_END_DATE_FORMAT);

    assertThat(validator.isValid(requestWithWrongStartDate, null)).isFalse();
    assertThat(validator.isValid(requestWithWrongEndDate, null)).isFalse();
  }

  @Test
  void shouldNotBeValidWhenEndDateIsEqualToStartDateWithValidateTimeAsTrue() {
    final DateRangeRequestMockImpl request =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.END_DATE_EQUALS_TO_START_DATE);

    assertThat(validator.isValid(request, null)).isFalse();
  }

  @Test
  void shouldNotBeValidWhenEndDateIsBeforeStartDateWithValidateTimeAsTrue() {
    final DateRangeRequestMockImpl request =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.END_DATE_BEFORE_START_DATE);

    assertThat(validator.isValid(request, null)).isFalse();
  }

  @Test
  void shouldNotBeValidWhenStartDateIsBeforeEpochWithValidateTimeAsTrue() {
    final DateRangeRequestMockImpl request =
        Fixture.from(DateRangeRequestMockImpl.class)
            .gimme(DateRangeRequestMockImplTemplate.START_DATE_BEFORE_EPOCH);

    assertThat(validator.isValid(request, null)).isFalse();
  }
}
