package com.abinbev.b2b.promotion.consumer.vo;

import java.util.List;

public class PromotionAccountMultiVendorDeleteVO {

  private List<String> vendorPromotionIds;
  private List<String> vendorAccountIds;

  public List<String> getVendorPromotionIds() {

    return vendorPromotionIds;
  }

  public void setVendorPromotionIds(List<String> vendorPromotionIds) {

    this.vendorPromotionIds = vendorPromotionIds;
  }

  public List<String> getVendorAccountIds() {

    return vendorAccountIds;
  }

  public void setVendorAccountIds(List<String> vendorAccountIds) {

    this.vendorAccountIds = vendorAccountIds;
  }
}
