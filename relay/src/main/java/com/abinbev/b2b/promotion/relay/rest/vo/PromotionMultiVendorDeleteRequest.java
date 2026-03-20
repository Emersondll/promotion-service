package com.abinbev.b2b.promotion.relay.rest.vo;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class PromotionMultiVendorDeleteRequest {

  @NotEmpty private List<String> vendorPromotionIds;

  public PromotionMultiVendorDeleteRequest() {}

  public PromotionMultiVendorDeleteRequest(List<String> vendorPromotionIds) {
    this.vendorPromotionIds = vendorPromotionIds;
  }

  public List<String> getVendorPromotionIds() {
    return vendorPromotionIds;
  }

  public void setVendorPromotionIds(List<String> vendorPromotionIds) {
    this.vendorPromotionIds = vendorPromotionIds;
  }
}
