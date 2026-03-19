package com.abinbev.b2b.promotion.consumer.exception;

public class MapperException extends RuntimeException {

  private final String errorMessage;

  public String getErrorMessage() {
    return errorMessage;
  }

  private MapperException(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static MapperException cannotDeserializeJson(final String errorMessage, final Object arg) {
    return new MapperException(String.format(errorMessage, arg));
  }
}
