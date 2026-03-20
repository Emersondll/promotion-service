package com.abinbev.b2b.promotion.relay.interceptors;

import com.abinbev.b2b.promotion.relay.config.properties.ToggleProperties;
import com.abinbev.b2b.promotion.relay.constants.ApiConstants;
import com.abinbev.b2b.promotion.relay.exceptions.BadRequestException;
import com.abinbev.b2b.promotion.relay.exceptions.NotFoundException;
import com.abinbev.b2b.promotion.relay.helpers.ControllerValidationHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/** Generic interceptor for all controllers. */
@Component
public class ControllerRequestInterceptor implements HandlerInterceptor {

  private final ToggleProperties toggleProperties;

  @Autowired
  public ControllerRequestInterceptor(ToggleProperties toggleProperties) {
    this.toggleProperties = toggleProperties;
  }

  @Override
  public boolean preHandle(
      final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
    final RequestValidation annotationIsPresent = getAnnotation(handler);

    if (annotationIsPresent != null) {

      if (annotationIsPresent.requestTraceId()) {
        ControllerValidationHelper.validateRequiredHeader(
            ApiConstants.REQUEST_TRACE_ID_HEADER,
            request.getHeader(ApiConstants.REQUEST_TRACE_ID_HEADER));
        MDC.put(
            ApiConstants.REQUEST_TRACE_ID_HEADER,
            request.getHeader(ApiConstants.REQUEST_TRACE_ID_HEADER));
      }

      if (annotationIsPresent.xtimestampIsRequired()) {
        final String timestamp = request.getHeader(ApiConstants.X_TIMESTAMP_HEADER);
        ControllerValidationHelper.validateRequiredHeader(
            ApiConstants.X_TIMESTAMP_HEADER, request.getHeader(ApiConstants.X_TIMESTAMP_HEADER));
        validateTimestamp(timestamp);
        MDC.put(ApiConstants.X_TIMESTAMP_HEADER, timestamp);
      }

      if (annotationIsPresent.xtimestampIsValid()) {
        final String timestamp = request.getHeader(ApiConstants.X_TIMESTAMP_HEADER);
        validateTimestamp(timestamp);
        MDC.put(ApiConstants.X_TIMESTAMP_HEADER, timestamp);
      }

      if (annotationIsPresent.country()) {
        final String country = request.getHeader(ApiConstants.COUNTRY_HEADER);
        ControllerValidationHelper.validateRequiredHeader(ApiConstants.COUNTRY_HEADER, country);
        validateCountry(country);
        MDC.put(ApiConstants.COUNTRY_HEADER, country);
      }
    }

    MDC.put("ServiceName", "promotion-relay-service");
    return true;
  }

  @Override
  public void afterCompletion(
      final HttpServletRequest request,
      final HttpServletResponse response,
      final Object handler,
      @Nullable final Exception ex) {

    MDC.clear();
  }

  /**
   * Validate if the class or method of the HandlerMethod bean instance are annotated with {@link
   * RequestValidation}
   *
   * @param object The HandlerMethod instance
   * @return TRUE if the class or method are annotated with {@link RequestValidation}, otherwise
   *     FALSE:
   */
  private RequestValidation getAnnotation(final Object object) {

    if (object instanceof HandlerMethod) {
      final HandlerMethod handler = (HandlerMethod) object;

      return getAnnotationFromMethod(handler) == null
          ? getAnnotationFromClass(handler)
          : getAnnotationFromMethod(handler);
    }

    return null;
  }

  private RequestValidation getAnnotationFromClass(final HandlerMethod handler) {

    final Class<? extends Object> beanClass = ClassUtils.getUserClass(handler.getBean().getClass());

    if (beanClass.isAnnotationPresent(RequestValidation.class)) {
      return beanClass.getAnnotation(RequestValidation.class);
    }

    return null;
  }

  private RequestValidation getAnnotationFromMethod(final HandlerMethod handler) {

    final Method beanMethod = handler.getMethod();

    if (beanMethod.isAnnotationPresent(RequestValidation.class)) {
      return beanMethod.getAnnotation(RequestValidation.class);
    }

    return null;
  }

  /**
   * Validates received country.
   *
   * @param countryToValidate
   */
  private void validateCountry(final String countryToValidate) {

    if (!toggleProperties.isSupportedCountry(countryToValidate)) {
      throw NotFoundException.countryNotFound(countryToValidate);
    }
  }

  /**
   * Validates received timestamp.
   *
   * @param headerValue
   */
  private void validateTimestamp(final String headerValue) {

    try {

      if (StringUtils.isNotBlank(headerValue)) {
        long timestamp = Long.parseLong(headerValue);

        if (timestamp < 0) {
          throw BadRequestException.invalidHeader(ApiConstants.X_TIMESTAMP_HEADER, headerValue);
        }
      }

    } catch (NumberFormatException e) {
      throw BadRequestException.invalidHeader(ApiConstants.X_TIMESTAMP_HEADER, headerValue);
    }
  }
}
