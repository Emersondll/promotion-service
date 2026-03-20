package com.abinbev.b2b.promotion.relay.aspect;

import com.abinbev.b2b.promotion.relay.constants.LogConstant;
import com.abinbev.b2b.promotion.relay.exceptions.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspectj {

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspectj.class);

  @Autowired(required = false)
  private HttpServletRequest request;

  @Before("within(@org.springframework.web.bind.annotation.RestController *)")
  public void logBeforeController(final JoinPoint joinPoint) {
    try {

      final String message =
          isRequestWithError(joinPoint)
              ? LogConstant.INFO.ASPECT_BEFORE_LOG_WITH_ERROR
              : LogConstant.INFO.ASPECT_BEFORE_LOG;

      LOGGER.info(message, request.getMethod(), request.getRequestURI(), joinPoint.getArgs());

    } catch (Exception ex) {
      LOGGER.warn(LogConstant.ERROR.ASPECT_BEFORE_LOG, ex);
    }
  }

  @AfterReturning(
      value = "within(@org.springframework.web.bind.annotation.RestController *)",
      returning = "result")
  public void logAfterController(final JoinPoint joinPoint, final ResponseEntity<?> result) {
    try {
      LOGGER.info(
          LogConstant.INFO.ASPECT_AFTER_LOG,
          result.getStatusCode(),
          request.getMethod(),
          request.getRequestURI(),
          joinPoint.getArgs());
    } catch (Exception ex) {
      LOGGER.warn(LogConstant.ERROR.ASPECT_AFTER_LOG, ex);
    }
  }

  @After(value = "execution(* com.abinbev.b2b.promotion.relay.queue.MessageGateway.*(..))")
  public void logAfterSendQueue(final JoinPoint joinPoint) {
    try {
      LOGGER.info(
          LogConstant.INFO.ASPECT_AFTER_QUEUE,
          joinPoint.getArgs(),
          request.getMethod(),
          request.getRequestURI());
    } catch (Exception ex) {
      LOGGER.warn(LogConstant.ERROR.ASPECT_AFTER_QUEUE_LOG, ex);
    }
  }

  private boolean isRequestWithError(final JoinPoint joinPoint) {
    return joinPoint.getTarget() instanceof GlobalExceptionHandler;
  }
}
