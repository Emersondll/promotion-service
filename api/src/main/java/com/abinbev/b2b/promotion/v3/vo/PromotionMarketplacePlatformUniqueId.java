package com.abinbev.b2b.promotion.v3.vo;

public class PromotionMarketplacePlatformUniqueId {

  private String promotionId;
  private String promotionPlatformId;

  public PromotionMarketplacePlatformUniqueId() {}

  public PromotionMarketplacePlatformUniqueId(String promotionId, String promotionPlatformId) {
    this.promotionId = promotionId;
    this.promotionPlatformId = promotionPlatformId;
  }

  public String getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(String promotionId) {
    this.promotionId = promotionId;
  }

  public String getPromotionPlatformId() {
    return promotionPlatformId;
  }

  public void setPromotionPlatformId(String promotionPlatformId) {
    this.promotionPlatformId = promotionPlatformId;
  }
}
