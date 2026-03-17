package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.formatters.OffsetDateTimeDeserializer;
import com.abinbev.b2b.promotion.formatters.OffsetDateTimeSerializer;
import com.abinbev.b2b.promotion.v2.domain.model.Translation;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PromotionAccountMultivendor {

  private String id;
  private String vendorPromotionId;
  private String title;
  private String description;
  private PromotionType promotionType;
  private BigDecimal budget;
  private String image;
  private Integer quantityLimit;
  private Set<String> vendorItemIds = new HashSet<>();
  private Set<String> freeGoodVendorItemIds = new HashSet<>();

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime startDate;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime endDate;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime updateAt;

  private Map<String, Translation> translations = new HashMap<>();

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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

  public PromotionType getPromotionType() {
    return promotionType;
  }

  public void setPromotionType(PromotionType promotionType) {
    this.promotionType = promotionType;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public OffsetDateTime getStartDate() {
    return startDate;
  }

  public void setStartDate(OffsetDateTime startDate) {
    this.startDate = startDate;
  }

  public OffsetDateTime getEndDate() {
    return endDate;
  }

  public void setEndDate(OffsetDateTime endDate) {
    this.endDate = endDate;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Integer getQuantityLimit() {
    return quantityLimit;
  }

  public void setQuantityLimit(Integer quantityLimit) {
    this.quantityLimit = quantityLimit;
  }

  public Set<String> getVendorItemIds() {
    return vendorItemIds;
  }

  public void setVendorItemIds(Set<String> vendorItemIds) {
    this.vendorItemIds = vendorItemIds;
  }

  public OffsetDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(OffsetDateTime updateAt) {
    this.updateAt = updateAt;
  }

  public Set<String> getFreeGoodVendorItemIds() {
    return freeGoodVendorItemIds;
  }

  public void setFreeGoodVendorItemIds(Set<String> freeGoodVendorItemIds) {
    this.freeGoodVendorItemIds = freeGoodVendorItemIds;
  }

  public Map<String, Translation> getTranslations() {
    return translations;
  }

  public void setTranslations(Map<String, Translation> translations) {
    this.translations = translations;
  }

  public boolean isFreeGoodPromotion() {
    return promotionType != null
        && (PromotionType.FREE_GOOD.name().equalsIgnoreCase(promotionType.name())
            || PromotionType.STEPPED_FREE_GOOD.name().equalsIgnoreCase(promotionType.name()));
  }
}
