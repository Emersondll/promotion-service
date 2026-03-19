package com.abinbev.b2b.promotion.consumer.listener.message;

import static org.apache.commons.lang3.builder.ToStringStyle.JSON_STYLE;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class GenericLoggableObject {

  @Override
  public String toString() {

    return new ReflectionToStringBuilder(this, JSON_STYLE).toString();
  }
}
