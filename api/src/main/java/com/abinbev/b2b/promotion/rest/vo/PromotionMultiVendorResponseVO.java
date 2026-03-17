package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import java.util.List;

public class PromotionMultiVendorResponseVO {

  private List<PromotionMultivendorResponse> promotions;
  private Pagination pagination;

  public PromotionMultiVendorResponseVO(
      final List<PromotionMultivendorResponse> promotions, final Pagination pagination) {
    this.promotions = promotions;
    this.pagination = pagination;
  }

  public List<PromotionMultivendorResponse> getPromotions() {
    return promotions;
  }

  public void setPromotions(List<PromotionMultivendorResponse> promotions) {
    this.promotions = promotions;
  }

  public Pagination getPagination() {
    return pagination;
  }

  public void setPagination(Pagination pagination) {
    this.pagination = pagination;
  }
}
