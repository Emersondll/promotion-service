package com.abinbev.b2b.promotion.v3.vo;

public class PromotionMarketplaceVendorUniqueId {

  private String vendorId;
  private String vendorPromotionId;

  public PromotionMarketplaceVendorUniqueId() {}

  public PromotionMarketplaceVendorUniqueId(String vendorId, String vendorPromotionId) {
    this.vendorId = vendorId;
    this.vendorPromotionId = vendorPromotionId;
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }

  public String getVendorPromotionId() {
    return vendorPromotionId;
  }

  public void setVendorPromotionId(String vendorPromotionId) {
    this.vendorPromotionId = vendorPromotionId;
  }
}
