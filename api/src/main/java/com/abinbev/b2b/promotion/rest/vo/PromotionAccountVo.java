package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

public class PromotionAccountVo {

  @JsonProperty("id")
  private String id;

  @JsonProperty("accountId")
  private String accountId;

  @JsonProperty("promotionId")
  private String promotionId;

  @JsonProperty("internalId")
  private String internalId;

  @JsonProperty("title")
  private String title;

  @JsonProperty("type")
  private PromotionType type;

  @JsonProperty("promotionRank")
  private Integer promotionRank;

  @JsonProperty("description")
  private String description;

  @JsonProperty("startDate")
  private String startDate;

  @JsonProperty("endDate")
  private String endDate;

  @JsonProperty("freeGoodsSkus")
  private Set<String> freeGoodsSkus;

  @JsonProperty("skus")
  private Set<String> skus;

  @JsonProperty("budget")
  private BigDecimal budget;

  @JsonProperty(value = "disabled")
  private boolean disabled;

  @JsonProperty("quantityLimit")
  private Integer quantityLimit;

  @JsonProperty(value = "hidden")
  private boolean hidden;

  @JsonProperty(value = "image")
  private String image;

  @JsonIgnore private boolean deleted;
  @JsonIgnore private OffsetDateTime createAt;
  @JsonIgnore private OffsetDateTime updateAt;

  private PromotionAccountVo(Builder builder) {
    setId(builder.id);
    setAccountId(builder.accountId);
    setPromotionId(builder.promotionId);
    setInternalId(builder.internalId);
    setTitle(builder.title);
    setType(builder.type);
    setPromotionRank(builder.promotionRank);
    setDescription(builder.description);
    setStartDate(builder.startDate);
    setEndDate(builder.endDate);
    setFreeGoodsSkus(builder.freeGoodsSkus);
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

  public PromotionType getType() {
    return type;
  }

  public void setType(PromotionType type) {
    this.type = type;
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

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Set<String> getFreeGoodsSkus() {
    return freeGoodsSkus;
  }

  public void setFreeGoodsSkus(Set<String> freeGoodsSkus) {
    this.freeGoodsSkus = freeGoodsSkus;
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

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public boolean isDisabled() {
    return disabled;
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
    private PromotionType type;
    private Integer promotionRank;
    private String description;
    private String startDate;
    private String endDate;
    private Set<String> freeGoodsSkus;
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

    public Builder withType(PromotionType type) {
      this.type = type;
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

    public Builder withStartDate(String startDate) {
      this.startDate = startDate;
      return this;
    }

    public Builder withEndDate(String endDate) {
      this.endDate = endDate;
      return this;
    }

    public Builder withFreeGoodsSkus(Set<String> freeGoodsSkus) {
      this.freeGoodsSkus = freeGoodsSkus;
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

    public PromotionAccountVo build() {
      return new PromotionAccountVo(this);
    }
  }
}
