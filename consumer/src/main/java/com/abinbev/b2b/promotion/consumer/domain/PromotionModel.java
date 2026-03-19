package com.abinbev.b2b.promotion.consumer.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

public class PromotionModel extends PromotionBaseModel {

  private String promotionId;

  public PromotionModel() {}

  private PromotionModel(Builder builder) {
    setId(builder.id);
    setPromotionId(builder.promotionId);
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
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(String promotionId) {
    this.promotionId = promotionId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PromotionModel that = (PromotionModel) o;

    if (Optional.ofNullable(getId()).isPresent()) {
      return getId().equals(that.getId());
    } else {
      return promotionId.equals(that.promotionId);
    }
  }

  @Override
  public int hashCode() {
    return getId().hashCode();
  }

  public static final class Builder {

    private String id;
    private String promotionId;
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

    private Builder() {}

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withPromotionId(String promotionId) {
      this.promotionId = promotionId;
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

    public PromotionModel build() {
      return new PromotionModel(this);
    }
  }
}
