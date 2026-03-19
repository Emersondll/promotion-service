package com.abinbev.b2b.promotion.consumer.config.properties;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseCollectionProperties {
  @Value("${database.promotion}")
  private String promotion;

  public void setPromotion(String promotion) {
    this.promotion = promotion;
  }

  public String getPromotionCollectionByCountry(@NotNull final String country) {
    return country.toUpperCase().concat(this.promotion);
  }
}
