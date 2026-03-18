package com.abinbev.b2b.promotion.validators.annotations;

import com.abinbev.b2b.promotion.validators.DateWindowValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DateWindowValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {

  boolean shouldValidateTime() default false;

  boolean isStartDateNullable() default false;

  boolean isEndDateNullable() default true;

  String message() default
      "dates-must-follow-ISO-8601-pattern-end-date-could-be-null-greater-than-or-equal-to-start-date-and-start-date-cannot-be-null";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
