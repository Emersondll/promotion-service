package com.abinbev.b2b.promotion.consumer.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PromotionSplitProperties {

  @Value("${promotion.split}")
  private Integer promotionSplit;

  public Integer getPromotionSplit() {
    return promotionSplit;
  }

  public void setPromotionSplit(Integer promotionSplit) {
    this.promotionSplit = promotionSplit;
  }
}
