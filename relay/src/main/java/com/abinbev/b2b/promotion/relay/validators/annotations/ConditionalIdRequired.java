package com.abinbev.b2b.promotion.relay.validators.annotations;

import com.abinbev.b2b.promotion.relay.validators.ConditionalIdRequiredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {ConditionalIdRequiredValidator.class})
public @interface ConditionalIdRequired {
  String message() default "Or field 'id' or 'promotionId' must be sent";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
