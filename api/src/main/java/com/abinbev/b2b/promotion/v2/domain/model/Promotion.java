package com.abinbev.b2b.promotion.v2.domain.model;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.formatters.OffsetDateTimeDeserializer;
import com.abinbev.b2b.promotion.formatters.OffsetDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.annotation.Id;

public class Promotion {

  @Id @JsonProperty private String id;

  @JsonProperty private PromotionType promotionType;

  @JsonProperty private String title;

  @JsonProperty private String description;

  @JsonProperty private List<String> accountGroups = new ArrayList<>();

  @JsonProperty private List<String> skuGroups = new ArrayList<>();

  @JsonProperty private List<String> freeGoodGroups = new ArrayList<>();

  @NotNull
  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime startDate;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime endDate;

  @JsonProperty private Integer promotionsRanking;

  @JsonProperty private Map<String, BigDecimal> score = new HashMap<>();

  @JsonIgnore private boolean deleted;

  @JsonIgnore private OffsetDateTime createAt;

  @JsonIgnore private OffsetDateTime updateAt;

  @JsonProperty private BigDecimal budget;

  @JsonProperty private boolean disabled;

  @JsonProperty private String promotionId;

  @JsonProperty private String internalId;

  @JsonProperty private Integer quantityLimit;

  @JsonProperty private boolean hidden;

  @JsonProperty private String image;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PromotionType getPromotionType() {
    return promotionType;
  }

  public void setPromotionType(PromotionType promotionType) {
    this.promotionType = promotionType;
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

  public List<String> getAccountGroups() {
    return accountGroups;
  }

  public void setAccountGroups(List<String> accountGroups) {
    this.accountGroups = accountGroups;
  }

  public List<String> getSkuGroups() {
    return skuGroups;
  }

  public void setSkuGroups(List<String> skuGroups) {
    this.skuGroups = skuGroups;
  }

  public List<String> getFreeGoodGroups() {
    return freeGoodGroups;
  }

  public void setFreeGoodGroups(List<String> freeGoodGroups) {
    this.freeGoodGroups = freeGoodGroups;
  }

  public OffsetDateTime getCreateAt() {
    return createAt;
  }

  public void setCreateAt(OffsetDateTime createAt) {
    this.createAt = createAt;
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

  public OffsetDateTime getUpdateAt() {
    return updateAt;
  }

  public void setUpdateAt(OffsetDateTime updateAt) {
    this.updateAt = updateAt;
  }

  public Integer getPromotionsRanking() {
    return promotionsRanking;
  }

  public void setPromotionsRanking(Integer promotionsRanking) {
    this.promotionsRanking = promotionsRanking;
  }

  public Map<String, BigDecimal> getScore() {

    return score;
  }

  public void setScore(Map<String, BigDecimal> score) {

    this.score = score;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public boolean isAFreeGoodPromotion() {
    return PromotionType.FREE_GOOD.name().equalsIgnoreCase(promotionType.name())
        || PromotionType.STEPPED_FREE_GOOD.name().equalsIgnoreCase(promotionType.name());
  }

  public boolean containsCombinedDescription() {
    return false;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
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
}
