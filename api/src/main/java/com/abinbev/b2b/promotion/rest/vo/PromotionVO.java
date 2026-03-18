package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.validators.DateRangeRequest;
import com.abinbev.b2b.promotion.validators.annotations.NotDuplicate;
import com.abinbev.b2b.promotion.validators.annotations.ValidDateRange;
import com.abinbev.b2b.promotion.validators.annotations.ValidFreeGood;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@ValidDateRange(shouldValidateTime = true)
@ValidFreeGood
public class PromotionVO implements DateRangeRequest {

  @JsonProperty
  @NotBlank
  @Size(max = 255)
  private String id;

  @JsonProperty @NotNull private PromotionType type;

  @JsonProperty
  @NotBlank
  @Size(max = 255)
  private String title;

  @JsonProperty
  @Size(max = 20000)
  private String description;

  @JsonProperty @NotEmpty @NotDuplicate private List<String> accountGroupIds = new ArrayList<>();

  @JsonProperty @NotEmpty @NotDuplicate private List<String> skuGroupIds = new ArrayList<>();

  @JsonProperty @NotDuplicate @Valid private List<String> freeGoodGroupIds = new ArrayList<>();

  @JsonProperty private String startDate;

  @JsonProperty private String endDate;

  @JsonProperty
  @Min(1)
  @Max(100)
  private Integer promotionsRanking;

  @JsonProperty private BigDecimal budget;

  @JsonProperty private boolean disabled;

  @JsonProperty private String promotionId;

  @JsonProperty
  @Min(1)
  private Integer quantityLimit;

  @JsonProperty private boolean hidden;

  public String getId() {

    return id;
  }

  public void setId(String id) {

    this.id = id;
  }

  public PromotionType getType() {

    return type;
  }

  public void setType(PromotionType type) {

    this.type = type;
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

  public List<String> getAccountGroupIds() {

    return accountGroupIds;
  }

  public void setAccountGroupIds(List<String> accountGroupIds) {

    this.accountGroupIds = accountGroupIds;
  }

  public List<String> getSkuGroupIds() {

    return skuGroupIds;
  }

  public void setSkuGroupIds(List<String> skuGroupIds) {

    this.skuGroupIds = skuGroupIds;
  }

  public List<String> getFreeGoodGroupIds() {

    return freeGoodGroupIds;
  }

  public void setFreeGoodGroupIds(List<String> freeGoodGroupIds) {

    this.freeGoodGroupIds = freeGoodGroupIds;
  }

  @Override
  public String getStartDate() {

    return startDate;
  }

  @Override
  public String getEndDate() {

    return endDate;
  }

  public void setEndDate(final String endDate) {

    this.endDate = endDate;
  }

  public void setStartDate(final String startDate) {

    this.startDate = startDate;
  }

  public Integer getPromotionsRanking() {

    return promotionsRanking;
  }

  public void setPromotionsRanking(Integer promotionsRanking) {

    this.promotionsRanking = promotionsRanking;
  }

  public BigDecimal getBudget() {
    return budget;
  }

  public void setBudget(BigDecimal budget) {
    this.budget = budget;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public String getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(String promotionId) {
    this.promotionId = promotionId;
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
}
