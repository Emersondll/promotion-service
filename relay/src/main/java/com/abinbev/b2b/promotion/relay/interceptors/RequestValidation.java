package com.abinbev.b2b.promotion.relay.interceptors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequestValidation {

  /**
   * Determines if requestTraceId is required.
   *
   * @return true when required
   */
  boolean requestTraceId() default false;

  /**
   * Determines if x-timestamp is required.
   *
   * @return true when required
   */
  boolean xtimestampIsRequired() default false;

  /**
   * Determines if country is required.
   *
   * @return true when required
   */
  boolean country() default true;

  /**
   * Determines if xtimestamp is valid.
   *
   * @return true when required
   */
  boolean xtimestampIsValid() default true;
}
