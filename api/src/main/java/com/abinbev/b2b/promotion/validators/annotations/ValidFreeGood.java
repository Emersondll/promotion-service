package com.abinbev.b2b.promotion.validators.annotations;

import com.abinbev.b2b.promotion.validators.FreeGoodValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = FreeGoodValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFreeGood {

  String message() default "freegoodids-cannot-be-null-for-a-freegood-promotion";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
