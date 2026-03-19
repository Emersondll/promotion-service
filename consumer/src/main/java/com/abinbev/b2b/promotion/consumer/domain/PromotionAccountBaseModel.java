package com.abinbev.b2b.promotion.consumer.domain;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public abstract class PromotionAccountBaseModel {

  private String id;
  private PromotionType promotionType;
  private Integer promotionRank;
  private String internalId;
  private String title;
  private String description;
  private BigDecimal budget;
  private Integer quantityLimit;
  private Boolean deleted;
  private OffsetDateTime startDate;
  private OffsetDateTime endDate;
  private Boolean disabled;
  private OffsetDateTime createAt;
  private OffsetDateTime updateAt;
  private String image;
  private OffsetDateTime lowestDealStartDate;
  private OffsetDateTime highestDealEndDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public Integer getQuantityLimit() {

    return quantityLimit;
  }

  public void setQuantityLimit(Integer quantityLimit) {

    this.quantityLimit = quantityLimit;
  }

  public Boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(Boolean deleted) {
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

  public Boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public OffsetDateTime getLowestDealStartDate() {
    return lowestDealStartDate;
  }

  public void setLowestDealStartDate(OffsetDateTime lowestDealStartDate) {
    this.lowestDealStartDate = lowestDealStartDate;
  }

  public OffsetDateTime getHighestDealEndDate() {
    return highestDealEndDate;
  }

  public void setHighestDealEndDate(OffsetDateTime highestDealEndDate) {
    this.highestDealEndDate = highestDealEndDate;
  }
}
