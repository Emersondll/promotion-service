package com.abinbev.b2b.promotion.rest.vo;

import java.util.List;

public class PromotionResponseListByAccount {
  private final String accountId;
  private final List<PromotionResponse> promotions;

  public PromotionResponseListByAccount(
      final String accountId, final List<PromotionResponse> promotions) {
    this.accountId = accountId;
    this.promotions = promotions;
  }

  private PromotionResponseListByAccount(Builder builder) {
    accountId = builder.accountId;
    promotions = builder.promotions;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getAccountId() {
    return accountId;
  }

  public List<PromotionResponse> getPromotions() {
    return promotions;
  }

  public static final class Builder {

    private String accountId;
    private List<PromotionResponse> promotions;

    private Builder() {}

    public Builder withAccountId(String accountId) {
      this.accountId = accountId;
      return this;
    }

    public Builder withPromotions(List<PromotionResponse> promotions) {
      this.promotions = promotions;
      return this;
    }

    public PromotionResponseListByAccount build() {
      return new PromotionResponseListByAccount(this);
    }
  }
}
