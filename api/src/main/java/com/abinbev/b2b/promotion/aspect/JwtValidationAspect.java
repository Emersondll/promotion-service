package com.abinbev.b2b.promotion.aspect;

import com.abinbev.b2b.promotion.annotations.JwtValidation;
import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.abinbev.b2b.promotion.exceptions.JwtException;
import com.abinbev.b2b.promotion.helpers.ContextHelper;
import com.abinbev.b2b.promotion.helpers.UrlHelper;
import com.abinbev.b2b.promotion.services.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Aspect
@Configuration
public class JwtValidationAspect {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtValidationAspect.class);

  @Value("${enableJwtValidation}")
  private boolean enableJwtValidation;

  @Autowired private SecurityService securityService;
  @Autowired private ContextHelper contextHelper;

  @Around("@annotation(com.abinbev.b2b.promotion.annotations.JwtValidation)")
  public Object verifyJwt(ProceedingJoinPoint joinPoint) throws Throwable {

    final HttpServletRequest request = contextHelper.getServletRequest();

    String token = request.getHeader(ApiConstants.AUTHORIZATION_HEADER);

    if (StringUtils.isEmpty(token)
        || !enableJwtValidation
        || UrlHelper.isTrustedUrl(request.getRequestURI())) {
      return joinPoint.proceed();
    }

    try {
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      Method method = signature.getMethod();

      JwtValidation jwtValidation = method.getAnnotation(JwtValidation.class);

      String country = request.getHeader(ApiConstants.COUNTRY_HEADER);

      Object[] args = joinPoint.getArgs();
      List<String> accountIds = new ArrayList<>();
      if (jwtValidation.hasAccount()) {
        if (jwtValidation.isMultiple()) {
          if (Objects.nonNull(args[jwtValidation.position()])) {
            accountIds.addAll((Collection<String>) args[jwtValidation.position()]);
          }
        } else {
          String accountId = (String) args[jwtValidation.position()];
          if (!StringUtils.isEmpty(accountId)) {
            accountIds.add(accountId);
          }
        }
      }

      if (securityService.isRequestInvalid(token, accountIds, country)) {
        throw JwtException.invalidToken();
      }

    } catch (JwtException jwtException) {
      LOGGER.error(jwtException.getIssue().getMessage(), jwtException);
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value())
          .contentType(MediaType.APPLICATION_JSON)
          .body(jwtException.getIssue());
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
      return ResponseEntity.status(HttpStatus.FORBIDDEN.value());
    }

    return joinPoint.proceed();
  }
}
