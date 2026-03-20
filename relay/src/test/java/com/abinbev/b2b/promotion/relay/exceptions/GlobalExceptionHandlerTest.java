package com.abinbev.b2b.promotion.relay.exceptions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

  @Mock private WebRequest webRequest;

  @Mock private GlobalException globalException;
  private static GlobalExceptionHandler handler;

  @BeforeAll
  public static void before() {
    handler = new GlobalExceptionHandler();
  }

  @Test
  public void validateUnexpectedErrorCode() {
    final Issue processExceptions = handler.processExceptions(new Exception(), webRequest);
    Assertions.assertEquals(3, processExceptions.getCode());
  }
}
