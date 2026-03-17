package com.abinbev.b2b.promotion.config;

import com.abinbev.b2b.promotion.interceptors.ControllerRequestInterceptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

  @Autowired private ControllerRequestInterceptor controllerRequestInterceptor;
  @Autowired private SpringDataWebProperties springDataWebProperties;

  @Override
  protected void addInterceptors(final InterceptorRegistry registry) {

    registry.addInterceptor(controllerRequestInterceptor);
  }

  @Override
  protected void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    final var resolver = new PageableHandlerMethodArgumentResolver();
    resolver.setMaxPageSize(springDataWebProperties.getPageable().getMaxPageSize());
    resolver.setSizeParameterName(springDataWebProperties.getPageable().getSizeParameter());
    resolver.setFallbackPageable(
        PageRequest.of(0, springDataWebProperties.getPageable().getDefaultPageSize()));
    argumentResolvers.add(resolver);
    super.addArgumentResolvers(argumentResolvers);
  }
}
