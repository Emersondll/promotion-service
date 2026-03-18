package com.abinbev.b2b.promotion.v3.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.exceptions.BadRequestException;
import java.util.Set;
import org.springframework.util.CollectionUtils;

public class PromotionFilterV3 {
  private final boolean ignoreStartDate;
  private final String query;
  private final Set<PromotionType> types;
  private final Set<String> promotionPlatformIds;
  private final Set<String> vendorIds;

  public PromotionFilterV3(final PromotionFilterBuilderV3 builder) {
    this.ignoreStartDate = builder.ignoreStartDate;
    this.query = builder.query;
    this.types = builder.types;
    this.promotionPlatformIds = builder.promotionPlatformIds;
    this.vendorIds = builder.vendorIds;
    validateFilters();
  }

  public boolean isIgnoreStartDate() {
    return ignoreStartDate;
  }

  public String getQuery() {
    return query;
  }

  public Set<PromotionType> getTypes() {
    return types;
  }

  public Set<String> getPromotionPlatformIds() {
    return promotionPlatformIds;
  }

  public Set<String> getVendorIds() {
    return vendorIds;
  }

  private void validateFilters() {
    if (CollectionUtils.isEmpty(vendorIds) && CollectionUtils.isEmpty(promotionPlatformIds)) {
      throw BadRequestException.atLeastOneParameterRequired("vendorIds", "promotionPlatformIds");
    }
  }

  public static PromotionFilterBuilderV3 builder() {
    return new PromotionFilterBuilderV3();
  }

  public static class PromotionFilterBuilderV3 {
    private boolean ignoreStartDate;
    private String query;
    private Set<PromotionType> types;
    private Set<String> promotionPlatformIds;
    private Set<String> vendorIds;

    public PromotionFilterBuilderV3() {}

    public PromotionFilterV3 build() {
      return new PromotionFilterV3(this);
    }

    public PromotionFilterBuilderV3 withIgnoreStartDate(final boolean ignoreStartDate) {
      this.ignoreStartDate = ignoreStartDate;
      return this;
    }

    public PromotionFilterBuilderV3 withQuery(final String query) {
      this.query = query;
      return this;
    }

    public PromotionFilterBuilderV3 withTypes(final Set<PromotionType> types) {
      this.types = types;
      return this;
    }

    public PromotionFilterBuilderV3 withPromotionPlatformIds(
        final Set<String> promotionPlatformIds) {
      this.promotionPlatformIds = promotionPlatformIds;
      return this;
    }

    public PromotionFilterBuilderV3 withVendorIds(final Set<String> vendorIds) {
      this.vendorIds = vendorIds;
      return this;
    }
  }
}
