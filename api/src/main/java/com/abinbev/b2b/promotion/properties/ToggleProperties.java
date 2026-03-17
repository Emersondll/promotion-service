package com.abinbev.b2b.promotion.properties;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@ConfigurationProperties(prefix = "features-toggle")
public class ToggleProperties {
  private List<String> supportedCountries;

  public boolean isSupportedCountry(final String country) {
    return !CollectionUtils.isEmpty(supportedCountries) && supportedCountries.contains(country);
  }

  public List<String> getSupportedCountries() {
    return supportedCountries;
  }

  public void setSupportedCountries(List<String> supportedCountries) {
    this.supportedCountries = supportedCountries;
  }
}
