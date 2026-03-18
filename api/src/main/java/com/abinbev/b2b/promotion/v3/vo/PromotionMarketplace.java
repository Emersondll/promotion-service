package com.abinbev.b2b.promotion.v3.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromotionMarketplace {
  private PromotionMarketplacePlatformUniqueId platformUniqueIds;
  private PromotionMarketplaceVendorUniqueId vendorUniqueIds;
  private String title;
  private String description;
  private PromotionType type;
  private String startDate;
  private String endDate;
  private String image;

  public PromotionMarketplacePlatformUniqueId getPlatformUniqueIds() {
    return platformUniqueIds;
  }

  public void setPlatformUniqueIds(PromotionMarketplacePlatformUniqueId platformUniqueIds) {
    this.platformUniqueIds = platformUniqueIds;
  }

  public PromotionMarketplaceVendorUniqueId getVendorUniqueIds() {
    return vendorUniqueIds;
  }

  public void setVendorUniqueIds(PromotionMarketplaceVendorUniqueId vendorUniqueIds) {
    this.vendorUniqueIds = vendorUniqueIds;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public PromotionType getType() {
    return type;
  }

  public void setType(PromotionType type) {
    this.type = type;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
