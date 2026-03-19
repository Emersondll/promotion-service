package com.abinbev.b2b.promotion.consumer.domain;

import com.abinbev.b2b.promotion.consumer.helper.OffsetDateTimeDeserializer;
import com.abinbev.b2b.promotion.consumer.helper.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;

public class PromotionMultiVendorModel extends PromotionBaseModel {

  private String vendorPromotionId;

  private String vendorId;

  private String promotionPlatformId;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime deletedAt;

  private Map<String, Translation> translations;

  private String defaultLanguage;

  public PromotionMultiVendorModel() {}

  private PromotionMultiVendorModel(Builder builder) {
    setId(builder.id);
    setVendorPromotionId(builder.vendorPromotionId);
    setVendorId(builder.vendorId);
    setPromotionPlatformId(builder.promotionPlatformId);
    setTitle(builder.title);
    setPromotionType(builder.type);
    setDescription(builder.description);
    setStartDate(builder.startDate);
    setEndDate(builder.endDate);
    setBudget(builder.budget);
    setQuantityLimit(builder.quantityLimit);
    setCreateAt(builder.createAt);
    setUpdateAt(builder.updateAt);
    setImage(builder.image);
    setDeleted(builder.deleted);
    setDeletedAt(builder.deletedAt);
    setTranslations(builder.translations);
    setDefaultLanguage(builder.defaultLanguage);
  }

  public static Builder newBuilder() {
    return new Builder();
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

  public String getPromotionPlatformId() {
    return promotionPlatformId;
  }

  public void setPromotionPlatformId(String promotionPlatformId) {
    this.promotionPlatformId = promotionPlatformId;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PromotionMultiVendorModel that = (PromotionMultiVendorModel) o;

    if (Optional.ofNullable(getId()).isPresent()) {
      return getId().equals(that.getId());
    } else {
      return vendorPromotionId.equals(that.vendorPromotionId);
    }
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  public static final class Builder {

    private String id;
    private String vendorPromotionId;
    private String vendorId;
    private String promotionPlatformId;
    private String title;
    private PromotionType type;
    private String description;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal budget;
    private Integer quantityLimit;
    private OffsetDateTime createAt;
    private OffsetDateTime updateAt;
    private String image;
    private Boolean deleted;
    private OffsetDateTime deletedAt;
    private Map<String, Translation> translations;
    private String defaultLanguage;

    private Builder() {}

    public Builder withId(String id) {
      this.id = id;
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

    public Builder withPromotionPlatformId(String promotionPlatformId) {
      if (StringUtils.isNotBlank(promotionPlatformId)) {
        this.promotionPlatformId = URLDecoder.decode(promotionPlatformId, StandardCharsets.UTF_8);
      }
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withPromotionType(PromotionType promotionType) {
      this.type = promotionType;
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

    public Builder withBudget(BigDecimal budget) {
      this.budget = budget;
      return this;
    }

    public Builder withQuantityLimit(Integer quantityLimit) {
      this.quantityLimit = quantityLimit;
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

    public Builder withImage(String image) {
      this.image = image;
      return this;
    }

    public Builder withDeleted(Boolean deleted) {
      this.deleted = deleted;
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

    public PromotionMultiVendorModel build() {
      return new PromotionMultiVendorModel(this);
    }
  }
}
