package com.abinbev.b2b.promotion.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequestValidation {

  boolean requestTraceId() default true;

  boolean country() default true;

  boolean timestamp() default true;
}
