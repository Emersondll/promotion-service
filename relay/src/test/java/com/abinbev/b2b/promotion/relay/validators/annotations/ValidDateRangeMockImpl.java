package com.abinbev.b2b.promotion.relay.validators.annotations;

import jakarta.validation.Payload;
import java.lang.annotation.Annotation;

public class ValidDateRangeMockImpl implements ValidDateRange {

  private boolean validateTime;
  private boolean startDateNullable;
  private boolean endDateNullable;

  public ValidDateRangeMockImpl() {}

  public ValidDateRangeMockImpl(
      boolean validateTime, boolean startDateNullable, boolean endDateNullable) {
    this.validateTime = validateTime;
    this.startDateNullable = startDateNullable;
    this.endDateNullable = endDateNullable;
  }

  public void setValidateTime(boolean validateTime) {
    this.validateTime = validateTime;
  }

  public void setStartDateNullable(boolean startDateNullable) {
    this.startDateNullable = startDateNullable;
  }

  public void setEndDateNullable(boolean endDateNullable) {
    this.endDateNullable = endDateNullable;
  }

  @Override
  public boolean shouldValidateTime() {
    return validateTime;
  }

  @Override
  public boolean isStartDateNullable() {
    return startDateNullable;
  }

  @Override
  public boolean isEndDateNullable() {
    return endDateNullable;
  }

  @Override
  public String message() {
    return null;
  }

  @Override
  public Class<?>[] groups() {
    return new Class[0];
  }

  @Override
  public Class<? extends Payload>[] payload() {
    return new Class[0];
  }

  @Override
  public Class<? extends Annotation> annotationType() {
    return null;
  }
}
