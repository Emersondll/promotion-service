package com.abinbev.b2b.promotion.relay.validators.annotations;

import com.abinbev.b2b.promotion.relay.validators.DateWindowValidator;
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
      "Dates must follow the ISO-8601 pattern, 'endDate' could be null or greater than 'startDate' and startDate cannot be null.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
