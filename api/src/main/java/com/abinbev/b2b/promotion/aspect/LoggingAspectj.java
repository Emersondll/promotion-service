package com.abinbev.b2b.promotion.aspect;

import static java.util.Optional.ofNullable;

import java.util.List;

import com.abinbev.b2b.promotion.constants.LogConstant;
import com.abinbev.b2b.promotion.domain.model.Pagination;
import com.abinbev.b2b.promotion.exceptions.GlobalExceptionHandler;
import com.abinbev.b2b.promotion.helpers.NewRelicTraceHelper;
import com.abinbev.b2b.promotion.v2.rest.vo.GetPromotionsVO;
import com.abinbev.b2b.promotion.v3.vo.Paged;
import com.abinbev.b2b.promotion.v3.vo.PromotionMarketplaceResponse;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
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

  @Autowired(required = false)
  private HttpServletRequest request;

  private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspectj.class);

  @Before("within(@org.springframework.web.bind.annotation.RestController *)")
  public void logBeforeController(final JoinPoint joinPoint) {
    try {

      final String message =
          isRequestWithError(joinPoint)
              ? LogConstant.INFO.ASPECT_BEFORE_LOG_WITH_ERROR
              : LogConstant.INFO.ASPECT_BEFORE_LOG;

      LOGGER.debug(message, request.getMethod(), request.getRequestURI(), joinPoint.getArgs());

      NewRelicTraceHelper.includeRequestInfo(joinPoint.getArgs());

    } catch (Exception ex) {
      LOGGER.warn(LogConstant.ERROR.ASPECT_BEFORE_LOG, ex);
    }
  }

  @AfterReturning(
      value = "within(@org.springframework.web.bind.annotation.RestController *)",
      returning = "result")
  public void logAfterController(final JoinPoint joinPoint, final ResponseEntity<?> result) {
    try {
      LOGGER.debug(
          LogConstant.INFO.ASPECT_AFTER_LOG,
          result.getStatusCode(),
          request.getMethod(),
          request.getRequestURI(),
          joinPoint.getArgs(),
          getValidResponseContent(result));

      Integer page = null, size = null;
      if (result.getBody() instanceof GetPromotionsVO response) {
        page = ofNullable(response.getPagination()).map(Pagination::getPage).orElse(null);
        size = ofNullable(response.getPromotions()).map(List::size).orElse(null);
      } else if (result.getBody() instanceof PromotionMarketplaceResponse response) {
        page = ofNullable(response.getPagination()).map(com.abinbev.b2b.promotion.v3.vo.Pagination::getPage).orElse(null);
        size = ofNullable(response.getContent()).map(List::size).orElse(null);
      } else if (result.getBody() instanceof Paged<?> paged) {
        page = ofNullable(paged.getPagination()).map(com.abinbev.b2b.promotion.v3.vo.Pagination::getPage).orElse(null);
        size = ofNullable(paged.getContent()).map(List::size).orElse(null);
      }

      NewRelicTraceHelper.includeResponseInfo(result.getStatusCode(), page, size, joinPoint.getArgs());
    } catch (Exception ex) {
      LOGGER.warn(LogConstant.ERROR.ASPECT_AFTER_LOG, ex);
    }
  }

  private boolean isRequestWithError(final JoinPoint joinPoint) {
    return joinPoint.getTarget() instanceof GlobalExceptionHandler;
  }

  private String getValidResponseContent(final ResponseEntity<?> result) {

    if (result.getBody() instanceof GetPromotionsVO) {
      return "size of response " + ((GetPromotionsVO) result.getBody()).getPromotions().size();
    } else if (result.getBody() instanceof PromotionMarketplaceResponse) {
      return "size of response "
          + ((PromotionMarketplaceResponse) result.getBody()).getContent().size();
    } else {
      return "status" + result.getStatusCode();
    }
  }
}
