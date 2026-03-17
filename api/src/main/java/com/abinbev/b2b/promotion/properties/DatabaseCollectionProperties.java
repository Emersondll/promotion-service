package com.abinbev.b2b.promotion.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "database")
public class DatabaseCollectionProperties {

  private final String base;
  private final String promotionAccounts;
  private final Long maxQueryTimeout;

  public DatabaseCollectionProperties(String base, String promotionAccounts, Long maxQueryTimeout) {
    this.base = base;
    this.promotionAccounts = promotionAccounts;
    this.maxQueryTimeout = maxQueryTimeout;
  }

  public String getBase(@NotNull final String country) {
    return country.toUpperCase() + base;
  }

  public String getPromotionAccounts(@NotNull final String country) {
    return country.toUpperCase() + promotionAccounts;
  }

  public Long getMaxQueryTimeout() {
    return this.maxQueryTimeout;
  }
}
