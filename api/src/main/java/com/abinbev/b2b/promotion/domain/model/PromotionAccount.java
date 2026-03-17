package com.abinbev.b2b.promotion.domain.model;

import com.abinbev.b2b.promotion.formatters.OffsetDateTimeDeserializer;
import com.abinbev.b2b.promotion.formatters.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.Id;

public class PromotionAccount {

  @Id private String id;
  private PromotionType promotionType;
  private Integer promotionRank;
  private String accountId;
  private String promotionId;
  private String internalId;
  private String title;
  private String description;
  private Set<String> freeGoodSkus;
  private Set<String> skus;
  private BigDecimal budget;
  private boolean deleted;
  private Integer quantityLimit;
  private boolean hidden;
  private String image;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime startDate;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime endDate;

  private boolean disabled;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime createAt;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime updateAt;

  public PromotionAccount() {}

  private PromotionAccount(Builder builder) {
    setId(builder.id);
    setAccountId(builder.accountId);
    setPromotionId(builder.promotionId);
    setInternalId(builder.internalId);
    setTitle(builder.title);
    setPromotionType(builder.promotionType);
    setPromotionRank(builder.promotionRank);
    setDescription(builder.description);
    setStartDate(builder.startDate);
    setEndDate(builder.endDate);
    setFreeGoodSkus(builder.freeGoodSkus);
    setSkus(builder.skus);
    setBudget(builder.budget);
    setDeleted(builder.deleted);
    setCreateAt(builder.createAt);
    setUpdateAt(builder.updateAt);
    setDisabled(builder.disabled);
    setQuantityLimit(builder.quantityLimit);
    setHidden(builder.hidden);
    setImage(builder.image);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public String getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(String promotionId) {
    this.promotionId = promotionId;
  }

  public String getInternalId() {

    return internalId;
  }

  public void setInternalId(String internalId) {

    this.internalId = internalId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public PromotionType getPromotionType() {
    return promotionType;
  }

  public void setPromotionType(PromotionType promotionType) {
    this.promotionType = promotionType;
  }

  public Integer getPromotionRank() {
    return promotionRank;
  }

  public void setPromotionRank(Integer promotionRank) {
    this.promotionRank = promotionRank;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public Set<String> getFreeGoodSkus() {
    return freeGoodSkus;
  }

  public void setFreeGoodSkus(Set<String> freeGoodSkus) {
    this.freeGoodSkus = freeGoodSkus;
  }

  public Set<String> getSkus() {
    return skus;
  }

  public void setSkus(Set<String> skus) {
    this.skus = skus;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public OffsetDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(OffsetDateTime createAt) {
    this.createAt = createAt;
  }

  public OffsetDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(OffsetDateTime updateAt) {
    this.updateAt = updateAt;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public Integer getQuantityLimit() {
    return quantityLimit;
  }

  public void setQuantityLimit(Integer quantityLimit) {
    this.quantityLimit = quantityLimit;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public boolean isAFreeGoodPromotion() {
    return promotionType != null
        && (PromotionType.FREE_GOOD.name().equalsIgnoreCase(promotionType.name())
            || PromotionType.STEPPED_FREE_GOOD.name().equalsIgnoreCase(promotionType.name()));
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public static final class Builder {

    private String id;
    private String accountId;
    private String promotionId;
    private String internalId;
    private String title;
    private PromotionType promotionType;
    private Integer promotionRank;
    private String description;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private Set<String> freeGoodSkus;
    private Set<String> skus;
    private BigDecimal budget;
    private boolean deleted;
    private OffsetDateTime createAt;
    private OffsetDateTime updateAt;
    private boolean disabled;
    private Integer quantityLimit;
    private boolean hidden;
    private String image;

    private Builder() {}

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withAccountId(String accountId) {
      this.accountId = accountId;
      return this;
    }

    public Builder withPromotionId(String promotionId) {
      this.promotionId = promotionId;
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

    public Builder withFreeGoodSkus(Set<String> freeGoodSkus) {
      this.freeGoodSkus = freeGoodSkus;
      return this;
    }

    public Builder withSkus(Set<String> skus) {
      this.skus = skus;
      return this;
    }

    public Builder withBudget(BigDecimal budget) {
      this.budget = budget;
      return this;
    }

    public Builder withDeleted(boolean deleted) {
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

    public Builder withDisabled(boolean disabled) {
      this.disabled = disabled;
      return this;
    }

    public Builder withQuantityLimit(Integer quantityLimit) {
      this.quantityLimit = quantityLimit;
      return this;
    }

    public Builder withHidden(boolean hidden) {
      this.hidden = hidden;
      return this;
    }

    public Builder withImage(String image) {
      this.image = image;
      return this;
    }

    public PromotionAccount build() {
      return new PromotionAccount(this);
    }
  }
}
