package com.abinbev.b2b.promotion.relay.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "bulk")
public class BulkSizeProperties {

  private Integer promotionBulkSize;
  private Integer promotionMultiVendorBulkSize;

  public Integer getPromotionBulkSize() {
    return promotionBulkSize;
  }

  public void setPromotionBulkSize(Integer promotionBulkSize) {
    this.promotionBulkSize = promotionBulkSize;
  }

  public Integer getPromotionMultiVendorBulkSize() {
    return promotionMultiVendorBulkSize;
  }

  public void setPromotionMultiVendorBulkSize(Integer promotionMultiVendorBulkSize) {
    this.promotionMultiVendorBulkSize = promotionMultiVendorBulkSize;
  }
}
