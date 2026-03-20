package com.abinbev.b2b.promotion.relay.helpers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class HttpContextHelper {

  public HttpServletRequest getServletRequest() {
    RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
    if (attrs != null) {
      return ((ServletRequestAttributes) attrs).getRequest();
    }
    return null;
  }
}
