package com.abinbev.b2b.promotion.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JwtValidation {

  int position() default -1;

  boolean isMultiple() default false;

  boolean hasAccount() default true;
}
