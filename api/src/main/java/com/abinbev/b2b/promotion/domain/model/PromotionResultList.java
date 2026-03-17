package com.abinbev.b2b.promotion.domain.model;

import com.abinbev.b2b.promotion.v2.domain.model.Promotion;
import java.util.List;

public class PromotionResultList {

  private final List<Promotion> promotions;
  private final Pagination pagination;

  public PromotionResultList(List<Promotion> promotions, Pagination pagination) {
    this.promotions = promotions;
    this.pagination = pagination;
  }

  public List<Promotion> getPromotions() {
    return promotions;
  }

  public Pagination getPagination() {
    return pagination;
  }
}
