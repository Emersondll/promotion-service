package com.abinbev.b2b.promotion.consumer.domain;

import com.abinbev.b2b.promotion.consumer.helper.OffsetDateTimeDeserializer;
import com.abinbev.b2b.promotion.consumer.helper.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import org.springframework.data.annotation.Id;

public abstract class PromotionBaseModel {

  @Id private String id;
  private PromotionType promotionType;
  private String title;
  private String description;
  private BigDecimal budget;
  private Integer quantityLimit;
  private String image;
  private Boolean deleted;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime startDate;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime endDate;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime createAt;

  @JsonSerialize(using = OffsetDateTimeSerializer.class)
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  private OffsetDateTime updateAt;

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

  public PromotionType getPromotionType() {
    return promotionType;
  }

  public void setPromotionType(PromotionType promotionType) {
    this.promotionType = promotionType;
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

  public void setCreateAt(OffsetDateTime createAt) {
    this.createAt = createAt;
  }

  public void setUpdateAt(OffsetDateTime updateAt) {
    this.updateAt = updateAt;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }

  public Boolean getDeleted() {
    return deleted;
  }
}
