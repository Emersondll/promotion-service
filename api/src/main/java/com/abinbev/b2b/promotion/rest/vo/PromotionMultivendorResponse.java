package com.abinbev.b2b.promotion.rest.vo;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class PromotionMultivendorResponse {

  private String id;
  private String vendorPromotionId;
  private String title;
  private String description;
  private String type;
  private BigDecimal budget;
  private String image;
  private Integer quantityLimit;
  private Set<String> vendorItemIds = new HashSet<>();
  private String startDate;
  private String endDate;
  private String updatedAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getVendorPromotionId() {
    return vendorPromotionId;
  }

  public void setVendorPromotionId(String vendorPromotionId) {
    this.vendorPromotionId = vendorPromotionId;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Integer getQuantityLimit() {
    return quantityLimit;
  }

  public void setQuantityLimit(Integer quantityLimit) {
    this.quantityLimit = quantityLimit;
  }

  public Set<String> getVendorItemIds() {
    return vendorItemIds;
  }

  public void setVendorItemIds(Set<String> vendorItemIds) {
    this.vendorItemIds = vendorItemIds;
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

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }
}
