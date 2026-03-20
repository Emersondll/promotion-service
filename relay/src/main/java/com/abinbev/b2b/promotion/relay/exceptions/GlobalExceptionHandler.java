package com.abinbev.b2b.promotion.relay.exceptions;

import com.abinbev.b2b.promotion.relay.constants.ApiConstants;
import com.abinbev.b2b.promotion.relay.constants.LogConstant;
import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected Issue processExceptions(final Exception ex, final WebRequest request) {
    setMessageHeaders(request);
    LOGGER.error(LogConstant.ERROR.UNEXPECTED, ex);
    return new Issue(IssueEnum.BAD_REQUEST_ERROR, Arrays.asList(ex.getLocalizedMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected Issue handleMethodArgumentNotValid(
      final MethodArgumentNotValidException ex, final WebRequest request) {
    setMessageHeaders(request);
    LOGGER.error(LogConstant.ERROR.HTTP_INVALID_ARGUMENT, ex);
    final List<String> errors = new ArrayList<>();
    for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }
    return new Issue(IssueEnum.MALFORMED_REQUEST_PAYLOAD, errors);
  }

  @ExceptionHandler({BadRequestException.class})
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected Issue processBadRequestException(final GlobalException ex, final WebRequest request) {
    setMessageHeaders(request);
    LOGGER.error(LogConstant.ERROR.MALFORMED_REQUEST, ex);
    return ex.getIssue();
  }

  @ExceptionHandler({NotFoundException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  protected Issue processNotFoundException(final GlobalException ex, final WebRequest request) {
    setMessageHeaders(request);
    LOGGER.error(LogConstant.ERROR.NOT_FOUND, ex);
    return ex.getIssue();
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  protected Issue handleHttpMessageNotReadableException(
      final HttpMessageNotReadableException ex, final WebRequest request) {
    setMessageHeaders(request);
    LOGGER.error(LogConstant.ERROR.INVALID_JSON, ex);
    return new Issue(IssueEnum.JSON_DESERIALIZE_ERROR);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, value = HttpStatus.METHOD_NOT_ALLOWED)
  protected Issue processHttpRequestMethodNotSupportedException(
      final HttpRequestMethodNotSupportedException ex, final WebRequest request) {
    setMessageHeaders(request);
    LOGGER.error(LogConstant.ERROR.HTTP_METHOD_NOT_ALLOWED, ex);
    return new Issue(
        IssueEnum.METHOD_NOT_ALLOWED,
        ex.getMethod(),
        Joiner.on(", ").join(ex.getSupportedHttpMethods()));
  }

  @ExceptionHandler({BasicAuthorizationException.class})
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  protected Issue invalidAuthorizationException(
      final GlobalException ex, final WebRequest request) {
    setMessageHeaders(request);
    LOGGER.error(LogConstant.ERROR.FORBIDDEN, ex);
    return ex.getIssue();
  }

  private void setMessageHeaders(final WebRequest request) {
    final String requestTraceId = request.getHeader(ApiConstants.REQUEST_TRACE_ID_HEADER);
    final String country = request.getHeader(ApiConstants.COUNTRY_HEADER);

    if (Objects.nonNull(requestTraceId)) {
      MDC.put(ApiConstants.REQUEST_TRACE_ID_HEADER, requestTraceId);
    }

    if (Objects.nonNull(country)) {
      MDC.put(ApiConstants.COUNTRY_HEADER, country.toUpperCase());
    }
  }
}
