package com.abinbev.b2b.promotion.validators;

import com.abinbev.b2b.promotion.validators.annotations.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateWindowValidator extends DateRangeValidator
    implements ConstraintValidator<ValidDateRange, DateRangeRequest> {

  private boolean isStartDateNullable;
  private boolean isEndDateNullable;

  @Override
  public boolean isValid(
      final DateRangeRequest dateRangeRequest, final ConstraintValidatorContext context) {
    final String startDateRequest = dateRangeRequest.getStartDate();
    final String endDateRequest = dateRangeRequest.getEndDate();
    return isDateRangeValid(
        startDateRequest, isStartDateNullable, endDateRequest, isEndDateNullable);
  }

  @Override
  public void initialize(final ValidDateRange validDateTimeRange) {
    shouldValidateTime = validDateTimeRange.shouldValidateTime();
    isStartDateNullable = validDateTimeRange.isStartDateNullable();
    isEndDateNullable = validDateTimeRange.isEndDateNullable();
  }
}
