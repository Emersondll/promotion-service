package com.abinbev.b2b.promotion.consumer.domain;

import com.abinbev.b2b.promotion.consumer.helper.OffsetDateTimeDeserializer;
import com.abinbev.b2b.promotion.consumer.helper.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;

public class PromotionAccountMultiVendorModel extends PromotionAccountBaseModel {

  private String vendorAccountId;
  private String vendorPromotionId;
  private String vendorId;
  private Set<String> freeGoodVendorItemIds;
  private Set<String> vendorItemIds;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime deletedAt;

  private Map<String, Translation> translations;

  private String defaultLanguage;

  public PromotionAccountMultiVendorModel() {}

  private PromotionAccountMultiVendorModel(Builder builder) {
    setId(builder.id);
    setVendorAccountId(builder.vendorAccountId);
    setVendorPromotionId(builder.vendorPromotionId);
    setVendorId(builder.vendorId);
    setInternalId(builder.internalId);
    setTitle(builder.title);
    setPromotionType(builder.promotionType);
    setPromotionRank(builder.promotionRank);
    setDescription(builder.description);
    setStartDate(builder.startDate);
    setEndDate(builder.endDate);
    setFreeGoodVendorItemIds(builder.freeGoodVendorItemIds);
    setVendorItemIds(builder.vendorItemIds);
    setBudget(builder.budget);
    setQuantityLimit(builder.quantityLimit);
    setDeleted(builder.deleted);
    setCreateAt(builder.createAt);
    setUpdateAt(builder.updateAt);
    setDisabled(builder.disabled);
    setImage(builder.image);
    setDeletedAt(builder.deletedAt);
    setTranslations(builder.translations);
    setDefaultLanguage(builder.defaultLanguage);
    setLowestDealStartDate(builder.lowestDealStartDate);
    setHighestDealEndDate(builder.highestDealEndDate);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getVendorAccountId() {
    return vendorAccountId;
  }

  public void setVendorAccountId(String vendorAccountId) {
    this.vendorAccountId = vendorAccountId;
  }

  public String getVendorPromotionId() {
    return vendorPromotionId;
  }

  public void setVendorPromotionId(String vendorPromotionId) {
    this.vendorPromotionId = vendorPromotionId;
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public Set<String> getFreeGoodVendorItemIds() {
    return freeGoodVendorItemIds;
  }

  public void setFreeGoodVendorItemIds(Set<String> freeGoodVendorItemIds) {
    this.freeGoodVendorItemIds = freeGoodVendorItemIds;
  }

  public Set<String> getVendorItemIds() {
    return vendorItemIds;
  }

  public void setVendorItemIds(Set<String> vendorItemIds) {
    this.vendorItemIds = vendorItemIds;
  }

  public OffsetDateTime getDeletedAt() {
    return deletedAt;
  }

  public void setDeletedAt(OffsetDateTime deletedAt) {
    this.deletedAt = deletedAt;
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

  public static final class Builder {

    private String id;
    private String vendorAccountId;
    private String vendorPromotionId;
    private String vendorId;
    private String internalId;
    private String title;
    private PromotionType promotionType;
    private Integer promotionRank;
    private String description;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Set<String> freeGoodVendorItemIds;
    private Set<String> vendorItemIds;
    private BigDecimal budget;
    private Integer quantityLimit;
    private boolean deleted;
    private OffsetDateTime createAt;
    private OffsetDateTime updateAt;
    private boolean disabled;
    private String image;
    private OffsetDateTime deletedAt;
    private Map<String, Translation> translations;
    private String defaultLanguage;
    private OffsetDateTime lowestDealStartDate;
    private OffsetDateTime highestDealEndDate;

    private Builder() {}

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withVendorAccountId(String vendorAccountId) {
      this.vendorAccountId = vendorAccountId;
      return this;
    }

    public Builder withVendorPromotionId(String vendorPromotionId) {
      this.vendorPromotionId = vendorPromotionId;
      return this;
    }

    public Builder withVendorId(String vendorId) {
      this.vendorId = vendorId;
      return this;
    }

    public Builder withInternalId(String internalId) {
      this.internalId = internalId;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withPromotionType(PromotionType promotionType) {
      this.promotionType = promotionType;
      return this;
    }

    public Builder withPromotionRank(Integer promotionRank) {
      this.promotionRank = promotionRank;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withStartDate(OffsetDateTime startDate) {
      this.startDate = startDate;
      return this;
    }

    public Builder withEndDate(OffsetDateTime endDate) {
      this.endDate = endDate;
      return this;
    }

    public Builder withFreeGoodVendorItemIds(Set<String> freeGoodSkus) {
      this.freeGoodVendorItemIds = freeGoodSkus;
      return this;
    }

    public Builder withVendorItemIds(Set<String> skus) {
      this.vendorItemIds = skus;
      return this;
    }

    public Builder withBudget(BigDecimal budget) {
      this.budget = budget;
      return this;
    }

    public Builder withQuantityLimit(Integer quantityLimit) {
      this.quantityLimit = quantityLimit;
      return this;
    }

    public Builder withDeleted(Boolean deleted) {
      this.deleted = deleted;
      return this;
    }

    public Builder withCreateAt(OffsetDateTime createAt) {
      this.createAt = createAt;
      return this;
    }

    public Builder withUpdateAt(OffsetDateTime updateAt) {
      this.updateAt = updateAt;
      return this;
    }

    public Builder withDisabled(Boolean disabled) {
      this.disabled = disabled;
      return this;
    }

    public Builder withImage(String image) {
      this.image = image;
      return this;
    }

    public Builder withDeletedAt(OffsetDateTime deletedAt) {
      this.deletedAt = deletedAt;
      return this;
    }

    public Builder withTranslations(Map<String, Translation> translations) {
      this.translations = translations;
      return this;
    }

    public Builder withDefaultLanguage(String defaultLanguage) {
      this.defaultLanguage = defaultLanguage;
      return this;
    }

    public Builder withLowestDealStartDate(OffsetDateTime lowestDealStartDate) {
      this.lowestDealStartDate = lowestDealStartDate;
      return this;
    }

    public Builder withHighestDealEndDate(OffsetDateTime highestDealEndDate) {
      this.highestDealEndDate = highestDealEndDate;
      return this;
    }

    public PromotionAccountMultiVendorModel build() {
      return new PromotionAccountMultiVendorModel(this);
    }
  }
}
