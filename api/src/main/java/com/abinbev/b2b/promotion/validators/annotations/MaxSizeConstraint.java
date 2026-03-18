package com.abinbev.b2b.promotion.validators.annotations;

import com.abinbev.b2b.promotion.validators.MaxSizeConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MaxSizeConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxSizeConstraint {
  String message() default "Parameter with size higher than expected";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String property();
}
