package com.abinbev.b2b.promotion.relay.rest.vo;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public class PromotionSingleVendorDeleteRequest {

  @NotEmpty private Set<String> promotions;

  public Set<String> getPromotions() {
    return promotions;
  }

  public void setPromotions(Set<String> promotions) {
    this.promotions = promotions;
  }
}
