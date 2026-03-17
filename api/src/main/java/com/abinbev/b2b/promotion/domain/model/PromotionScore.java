package com.abinbev.b2b.promotion.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class PromotionScore {
  @JsonProperty
  @NotEmpty
  @Size(max = 255)
  private String promotionId;

  @JsonProperty @NotNull @PositiveOrZero private BigDecimal score;

  public String getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(String promotionId) {
    this.promotionId = promotionId;
  }

  public BigDecimal getScore() {

    return score;
  }

  public void setScore(BigDecimal score) {

    this.score = score;
  }
}
