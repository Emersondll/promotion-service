package com.abinbev.b2b.promotion.relay.domain;

import com.abinbev.b2b.promotion.relay.validators.annotations.ConditionalIdRequired;
import com.abinbev.b2b.promotion.relay.validators.annotations.DateRangeRequest;
import com.abinbev.b2b.promotion.relay.validators.annotations.ValidDateRange;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@ValidDateRange(shouldValidateTime = true)
@ConditionalIdRequired
public class PromotionSingleVendor implements DateRangeRequest {

  @JsonProperty private String id;

  @JsonProperty private String promotionId;

  @JsonProperty
  @Size(max = 20000)
  private String description;

  @JsonProperty private String startDate;

  @JsonProperty private String endDate;

  @JsonProperty
  @NotBlank
  @Size(min = 1, max = 255)
  private String title;

  @JsonProperty @NotNull private PromotionType type;

  @JsonProperty
  @Min(0)
  private BigDecimal budget;

  @JsonProperty
  @Min(1)
  private Integer quantityLimit;

  @JsonProperty private String image;

  public String getId() {

    return id;
  }

  public void setId(String id) {

    this.id = id;
  }

  public String getPromotionId() {

    return promotionId;
  }

  public void setPromotionId(String promotionId) {

    this.promotionId = promotionId;
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

  public String getImage() {

    return image;
  }

  public void setImage(String image) {

    this.image = image;
  }
}
