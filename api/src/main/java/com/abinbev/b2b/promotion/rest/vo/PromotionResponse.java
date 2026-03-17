package com.abinbev.b2b.promotion.rest.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class PromotionResponse {

  @JsonProperty private String id;

  @JsonProperty private String title;

  @JsonProperty private String type;

  @JsonProperty private String description;

  @JsonProperty private Set<String> skus = new HashSet<>();

  @JsonProperty private Integer promotionsRanking;

  @JsonProperty private BigDecimal score = new BigDecimal(0);

  @JsonProperty
  @JsonInclude(Include.NON_NULL)
  private BigDecimal budget;

  @JsonProperty
  @JsonInclude(Include.NON_NULL)
  private String updatedAt;

  @JsonProperty private String promotionId;

  @JsonProperty private String internalId;

  @JsonProperty
  @JsonInclude(Include.NON_NULL)
  private String endDate;

  @JsonProperty private Integer quantityLimit;

  @JsonProperty private String image;

  public PromotionResponse() {}

  private PromotionResponse(Builder builder) {
    setId(builder.id);
    setTitle(builder.title);
    setType(builder.type);
    setDescription(builder.description);
    setSkus(builder.skus);
    setPromotionsRanking(builder.promotionsRanking);
    setScore(builder.score);
    setBudget(builder.budget);
    setUpdatedAt(builder.updatedAt);
    setPromotionId(builder.promotionId);
    setInternalId(builder.internalId);
    setEndDate(builder.endDate);
    setQuantityLimit(builder.quantityLimit);
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

  public String getTitle() {

    return title;
  }

  public void setTitle(String title) {

    this.title = title;
  }

  public String getType() {

    return type;
  }

  public void setType(String type) {

    this.type = type;
  }

  public String getDescription() {

    return description;
  }

  public void setDescription(String description) {

    this.description = description;
  }

  public Set<String> getSkus() {

    return skus;
  }

  public void setSkus(Set<String> skus) {

    this.skus = skus;
  }

  public Integer getPromotionsRanking() {

    return promotionsRanking;
  }

  public void setPromotionsRanking(Integer promotionsRanking) {

    this.promotionsRanking = promotionsRanking;
  }

  public BigDecimal getScore() {

    return score;
  }

  public void setScore(BigDecimal score) {

    this.score = score;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
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

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public Integer getQuantityLimit() {
    return quantityLimit;
  }

  public void setQuantityLimit(Integer quantityLimit) {
    this.quantityLimit = quantityLimit;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public static final class Builder {

    private String id;
    private String title;
    private String type;
    private String description;
    private Set<String> skus;
    private Integer promotionsRanking;
    private BigDecimal score;
    private BigDecimal budget;
    private String updatedAt;
    private String promotionId;
    private String internalId;
    private String endDate;
    private Integer quantityLimit;
    private String image;

    private Builder() {}

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder withType(String type) {
      this.type = type;
      return this;
    }

    public Builder withDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder withSkus(Set<String> skus) {
      this.skus = skus;
      return this;
    }

    public Builder withPromotionsRanking(Integer promotionsRanking) {
      this.promotionsRanking = promotionsRanking;
      return this;
    }

    public Builder withScore(BigDecimal score) {
      this.score = score;
      return this;
    }

    public Builder withBudget(BigDecimal budget) {
      this.budget = budget;
      return this;
    }

    public Builder withUpdatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
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

    public Builder withEndDate(String endDate) {
      this.endDate = endDate;

      return this;
    }

    public Builder withQuantityLimit(Integer quantityLimit) {
      this.quantityLimit = quantityLimit;
      return this;
    }

    public Builder withImage(String image) {
      this.image = image;
      return this;
    }

    public PromotionResponse build() {
      return new PromotionResponse(this);
    }
  }
}
