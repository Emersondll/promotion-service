package com.abinbev.b2b.promotion.relay.config.properties;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ToggleProperties {

  @Value("${featuresToggle.supportedCountries}")
  private List<String> supportedCountries;

  public List<String> getSupportedCountries() {
    return supportedCountries;
  }

  public boolean isSupportedCountry(final String country) {
    return !CollectionUtils.isEmpty(supportedCountries)
        && supportedCountries.contains(country.toUpperCase());
  }
}
