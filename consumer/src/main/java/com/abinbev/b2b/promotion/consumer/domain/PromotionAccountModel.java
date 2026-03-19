package com.abinbev.b2b.promotion.consumer.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

public class PromotionAccountModel extends PromotionAccountBaseModel {

  private String accountId;
  private String promotionId;
  private Set<String> freeGoodSkus;
  private Set<String> skus;

  public PromotionAccountModel() {}

  private PromotionAccountModel(Builder builder) {
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
    setQuantityLimit(builder.quantityLimit);
    setDeleted(builder.deleted);
    setCreateAt(builder.createAt);
    setUpdateAt(builder.updateAt);
    setDisabled(builder.disabled);
    setImage(builder.image);
    setLowestDealStartDate(builder.lowestDealStartDate);
    setHighestDealEndDate(builder.highestDealEndDate);
  }

  public static Builder newBuilder() {
    return new Builder();
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
    private Integer quantityLimit;
    private boolean deleted;
    private OffsetDateTime createAt;
    private OffsetDateTime updateAt;
    private boolean disabled;
    private String image;
    private OffsetDateTime lowestDealStartDate;
    private OffsetDateTime highestDealEndDate;

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

    public Builder withQuantityLimit(Integer quantityLimit) {
      this.quantityLimit = quantityLimit;
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

    public Builder withImage(String image) {
      this.image = image;
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

    public PromotionAccountModel build() {
      return new PromotionAccountModel(this);
    }
  }
}
