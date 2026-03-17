package com.abinbev.b2b.promotion.interceptors;

import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.abinbev.b2b.promotion.exceptions.NotFoundException;
import com.abinbev.b2b.promotion.helpers.ControllerValidationHelper;
import com.abinbev.b2b.promotion.properties.ToggleProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import org.jboss.logging.MDC;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ControllerRequestInterceptor implements HandlerInterceptor {

  @Autowired private ToggleProperties toggleProperties;

  @Override
  public boolean preHandle(
      @NotNull final HttpServletRequest request,
      @NotNull final HttpServletResponse response,
      @NotNull final Object handler) {

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

      if (annotationIsPresent.country()) {
        ControllerValidationHelper.validateRequiredHeader(
            ApiConstants.COUNTRY_HEADER, request.getHeader(ApiConstants.COUNTRY_HEADER));
        final String country = request.getHeader(ApiConstants.COUNTRY_HEADER).toUpperCase();
        MDC.put(ApiConstants.COUNTRY_HEADER, country);

        validateCountry(country);
      }

      if (annotationIsPresent.timestamp()) {
        ControllerValidationHelper.validateRequiredHeader(
            ApiConstants.X_TIMESTAMP_HEADER, request.getHeader(ApiConstants.X_TIMESTAMP_HEADER));
        MDC.put(
            ApiConstants.X_TIMESTAMP_HEADER, request.getHeader(ApiConstants.X_TIMESTAMP_HEADER));
      }
    }

    MDC.put("ServiceName", "promotions-service");

    return true;
  }

  @Override
  public void afterCompletion(
      @NotNull final HttpServletRequest request,
      @NotNull final HttpServletResponse response,
      @NotNull final Object handler,
      @Nullable final Exception ex) {

    MDC.clear();
  }

  private RequestValidation getAnnotation(final Object object) {

    if (object instanceof HandlerMethod) {
      final HandlerMethod handler = (HandlerMethod) object;

      return getAnnotationFromMethod(handler) != null
          ? getAnnotationFromMethod(handler)
          : getAnnotationFromClass(handler);
    }

    return null;
  }

  private RequestValidation getAnnotationFromClass(final HandlerMethod handler) {

    final Class<?> beanClass = ClassUtils.getUserClass(handler.getBean().getClass());

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

  private void validateCountry(final String countryToValidate) {
    if (!toggleProperties.isSupportedCountry(countryToValidate)) {
      throw NotFoundException.countryNotFound(countryToValidate);
    }
  }
}
