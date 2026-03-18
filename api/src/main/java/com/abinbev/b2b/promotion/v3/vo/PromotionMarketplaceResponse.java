package com.abinbev.b2b.promotion.v3.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class PromotionMarketplaceResponse extends Paged<PromotionMarketplace> {

  @JsonCreator
  public PromotionMarketplaceResponse(
      @JsonProperty("promotions") List<PromotionMarketplace> promotions,
      @JsonProperty("pagination") Pagination pagination) {
    super(promotions, pagination.isHasNext(), pagination.getPage());
  }

  @JsonProperty("promotions")
  @Override
  public List<PromotionMarketplace> getContent() {
    return super.getContent();
  }
}
