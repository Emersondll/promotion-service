package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import java.util.List;

public class GetPromotionsVO {

  private final List<PromotionResponse> promotions;
  private final Pagination pagination;

  public GetPromotionsVO(final List<PromotionResponse> promotions, final Pagination pagination) {

    this.promotions = promotions;
    this.pagination = pagination;
  }

  public List<PromotionResponse> getPromotions() {

    return promotions;
  }

  public Pagination getPagination() {

    return pagination;
  }
}
