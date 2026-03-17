package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class AccountPromotionVO {

  @JsonProperty private String id;

  @JsonProperty private String accountId;

  @JsonProperty private String title;

  @JsonProperty private PromotionType type;

  @JsonProperty private String description;

  @JsonProperty private Set<String> skus = new HashSet<>();

  @JsonProperty private Integer promotionsRanking;

  @JsonProperty private BigDecimal score = new BigDecimal(0);

  @JsonProperty private BigDecimal budget;

  @JsonProperty private String updatedAt;

  @JsonProperty private String promotionId;

  @JsonProperty private String internalId;

  @JsonProperty private String endDate;

  @JsonProperty private Integer quantityLimit;

  @JsonProperty private String image;

  public AccountPromotionVO() {}

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

  public static final class AccountPromotionVoBuilder {
    private String id;
    private String accountId;
    private String title;
    private PromotionType type;
    private String description;
    private Set<String> skus = new HashSet<>();
    private Integer promotionsRanking;
    private BigDecimal score = new BigDecimal(0);
    private BigDecimal budget;
    private String updatedAt;
    private String promotionId;
    private String internalId;
    private String endDate;
    private Integer quantityLimit;
    private String image;

    public AccountPromotionVoBuilder() {}

    public static AccountPromotionVoBuilder anAccountPromotionVo() {
      return new AccountPromotionVoBuilder();
    }

    public AccountPromotionVoBuilder withId(String id) {
      this.id = id;
      return this;
    }

    public AccountPromotionVoBuilder withAccountId(String accountId) {
      this.accountId = accountId;
      return this;
    }

    public AccountPromotionVoBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public AccountPromotionVoBuilder withType(PromotionType type) {
      this.type = type;
      return this;
    }

    public AccountPromotionVoBuilder withDescription(String description) {
      this.description = description;
      return this;
    }

    public AccountPromotionVoBuilder withSkus(Set<String> skus) {
      this.skus = skus;
      return this;
    }

    public AccountPromotionVoBuilder withPromotionsRanking(Integer promotionsRanking) {
      this.promotionsRanking = promotionsRanking;
      return this;
    }

    public AccountPromotionVoBuilder withScore(BigDecimal score) {
      this.score = score;
      return this;
    }

    public AccountPromotionVoBuilder withBudget(BigDecimal budget) {
      this.budget = budget;
      return this;
    }

    public AccountPromotionVoBuilder withUpdatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public AccountPromotionVoBuilder withPromotionId(String promotionId) {
      this.promotionId = promotionId;
      return this;
    }

    public AccountPromotionVoBuilder withInternalId(String internalId) {
      this.internalId = internalId;
      return this;
    }

    public AccountPromotionVoBuilder withEndDate(String endDate) {
      this.endDate = endDate;
      return this;
    }

    public AccountPromotionVoBuilder withQuantityLimit(Integer quantityLimit) {
      this.quantityLimit = quantityLimit;
      return this;
    }

    public AccountPromotionVoBuilder withImage(String image) {
      this.image = image;
      return this;
    }

    public AccountPromotionVO build() {
      AccountPromotionVO accountPromotionVo = new AccountPromotionVO();
      accountPromotionVo.setId(id);
      accountPromotionVo.setAccountId(accountId);
      accountPromotionVo.setTitle(title);
      accountPromotionVo.setType(type);
      accountPromotionVo.setDescription(description);
      accountPromotionVo.setSkus(skus);
      accountPromotionVo.setPromotionsRanking(promotionsRanking);
      accountPromotionVo.setScore(score);
      accountPromotionVo.setBudget(budget);
      accountPromotionVo.setUpdatedAt(updatedAt);
      accountPromotionVo.setPromotionId(promotionId);
      accountPromotionVo.setInternalId(internalId);
      accountPromotionVo.setEndDate(endDate);
      accountPromotionVo.setQuantityLimit(quantityLimit);
      accountPromotionVo.setImage(image);
      return accountPromotionVo;
    }
  }
}
