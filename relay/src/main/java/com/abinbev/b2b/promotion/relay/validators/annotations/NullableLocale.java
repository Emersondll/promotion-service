package com.abinbev.b2b.promotion.relay.validators.annotations;

import com.abinbev.b2b.promotion.relay.validators.NullableLocaleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NullableLocaleValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NullableLocale {

  String message() default
      "Default language field should follow jdk 11 supported locale standards, for example, pt-BR";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
