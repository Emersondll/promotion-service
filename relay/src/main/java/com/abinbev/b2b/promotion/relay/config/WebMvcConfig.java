package com.abinbev.b2b.promotion.relay.config;

import com.abinbev.b2b.promotion.relay.interceptors.ControllerRequestInterceptor;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
  @Autowired private ControllerRequestInterceptor controllerRequestInterceptor;

  @Override
  protected void addInterceptors(final InterceptorRegistry registry) {

    registry.addInterceptor(controllerRequestInterceptor);
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("language");
    registry.addInterceptor(localeChangeInterceptor);
  }

  @Bean(name = "localeResolver")
  public LocaleResolver localeResolver() {
    SessionLocaleResolver slr = new SessionLocaleResolver();
    slr.setDefaultLocale(Locale.US);
    return slr;
  }
}
