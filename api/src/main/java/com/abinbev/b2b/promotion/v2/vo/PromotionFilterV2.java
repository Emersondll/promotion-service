package com.abinbev.b2b.promotion.v2.vo;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import java.util.List;
import java.util.Objects;

public class PromotionFilterV2 {

  private final boolean ignoreStartDate;
  private final Integer page;
  private final Integer pageSize;
  private final List<String> promotionIds;
  private final String query;
  private final List<PromotionType> types;
  private final String vendorId;
  private final List<String> vendorPromotionIds;
  private final Pagination pagination;

  public PromotionFilterV2(final PromotionFilterBuilderV2 filterBuilder) {
    this.ignoreStartDate = filterBuilder.ignoreStartDate;
    this.page = filterBuilder.page;
    this.pageSize = filterBuilder.pageSize;
    this.promotionIds = filterBuilder.promotionIds;
    this.query = filterBuilder.query;
    this.types = filterBuilder.types;
    this.vendorId = filterBuilder.vendorId;
    this.vendorPromotionIds = filterBuilder.vendorPromotionIds;
    this.pagination = new Pagination(filterBuilder.page, filterBuilder.pageSize);
  }

  public boolean isIgnoreStartDate() {
    return ignoreStartDate;
  }

  public Integer getPage() {
    return page;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public List<String> getPromotionIds() {
    return promotionIds;
  }

  public String getQuery() {
    return query;
  }

  public List<PromotionType> getTypes() {
    return types;
  }

  public String getVendorId() {
    return vendorId;
  }

  public List<String> getVendorPromotionIds() {
    return vendorPromotionIds;
  }

  public static PromotionFilterBuilderV2 builder() {
    return new PromotionFilterBuilderV2();
  }

  public Pagination getPagination() {
    return pagination;
  }

  public static class PromotionFilterBuilderV2 {

    private boolean ignoreStartDate;
    private Integer page;
    private Integer pageSize;
    private List<String> promotionIds;
    private String query;
    private List<PromotionType> types;
    private String vendorId;
    private List<String> vendorPromotionIds;

    public PromotionFilterBuilderV2() {}

    public PromotionFilterV2 build() {
      return new PromotionFilterV2(this);
    }

    public PromotionFilterBuilderV2 withIgnoreStartDate(final boolean ignoreStartDate) {
      this.ignoreStartDate = ignoreStartDate;
      return this;
    }

    public PromotionFilterBuilderV2 withPage(final Integer page) {
      this.page = page;
      return this;
    }

    public PromotionFilterBuilderV2 withPageSize(final Integer pageSize) {
      this.pageSize = pageSize;
      return this;
    }

    public PromotionFilterBuilderV2 withPromotionIds(final List<String> promotionIds) {
      this.promotionIds = promotionIds;
      return this;
    }

    public PromotionFilterBuilderV2 withQuery(final String query) {
      this.query = query;
      return this;
    }

    public PromotionFilterBuilderV2 withPromotionType(final List<PromotionType> types) {
      this.types = types;
      return this;
    }

    public PromotionFilterBuilderV2 withVendorId(final String vendorId) {
      this.vendorId = vendorId;
      return this;
    }

    public PromotionFilterBuilderV2 withVendorPromotionIds(final List<String> vendorPromotionIds) {
      this.vendorPromotionIds = vendorPromotionIds;
      return this;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PromotionFilterV2)) return false;
    PromotionFilterV2 that = (PromotionFilterV2) o;
    return ignoreStartDate == that.ignoreStartDate
        && Objects.equals(page, that.page)
        && Objects.equals(pageSize, that.pageSize)
        && Objects.equals(promotionIds, that.promotionIds)
        && Objects.equals(query, that.query)
        && Objects.equals(types, that.types)
        && Objects.equals(vendorId, that.vendorId)
        && Objects.equals(vendorPromotionIds, that.vendorPromotionIds)
        && Objects.equals(pagination, that.pagination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(
        ignoreStartDate,
        page,
        pageSize,
        promotionIds,
        query,
        types,
        vendorId,
        vendorPromotionIds,
        pagination);
  }
}
