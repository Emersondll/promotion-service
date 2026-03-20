package com.abinbev.b2b.promotion.relay.domain;

import com.abinbev.b2b.promotion.relay.formatters.LocaleDeserializer;
import com.abinbev.b2b.promotion.relay.validators.annotations.DateRangeRequest;
import com.abinbev.b2b.promotion.relay.validators.annotations.NullableLocale;
import com.abinbev.b2b.promotion.relay.validators.annotations.ValidDateRange;
import com.abinbev.b2b.promotion.relay.validators.annotations.ValidLocale;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Map;

@Valid
@ValidDateRange(
    shouldValidateTime = true,
    isEndDateNullable = false,
    message =
        "Dates must follow the ISO-8601 pattern, 'endDate' must be only greater than 'startDate' and 'startDate' must be greater than 1970-01-01T00:00:00Z.")
public class PromotionMultiVendor implements DateRangeRequest {

  @NotBlank private String vendorPromotionId;
  @NotBlank private String title;

  @Size(min = 1)
  private String description;

  @NotNull private PromotionType type;
  private String startDate;
  private String endDate;

  @Size(min = 1)
  private String image;

  @Min(0L)
  private BigDecimal budget;

  @Min(0L)
  private Integer quantityLimit;

  @Valid @ValidLocale private Map<String, Translation> translations;

  @NullableLocale
  @JsonDeserialize(using = LocaleDeserializer.class)
  private String defaultLanguage;

  public String getVendorPromotionId() {
    return vendorPromotionId;
  }

  public void setVendorPromotionId(String vendorPromotionId) {
    this.vendorPromotionId = vendorPromotionId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PromotionType getType() {
    return type;
  }

  public void setType(PromotionType type) {
    this.type = type;
  }

  @Override
  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  @Override
  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public Integer getQuantityLimit() {
    return quantityLimit;
  }

  public void setQuantityLimit(Integer quantityLimit) {
    this.quantityLimit = quantityLimit;
  }

  public Map<String, Translation> getTranslations() {
    return translations;
  }

  public void setTranslations(Map<String, Translation> translations) {
    this.translations = translations;
  }

  public String getDefaultLanguage() {
    return defaultLanguage;
  }

  public void setDefaultLanguage(String defaultLanguage) {
    this.defaultLanguage = defaultLanguage;
  }
}
