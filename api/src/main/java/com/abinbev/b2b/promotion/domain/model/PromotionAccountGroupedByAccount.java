package com.abinbev.b2b.promotion.domain.model;

import java.util.List;

public class PromotionAccountGroupedByAccount {

  private String accountId;
  private List<PromotionAccount> records;

  public PromotionAccountGroupedByAccount() {
    // not used
  }

  private PromotionAccountGroupedByAccount(Builder builder) {
    setAccountId(builder.accountId);
    setRecords(builder.records);
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  public List<PromotionAccount> getRecords() {
    return records;
  }

  public void setRecords(List<PromotionAccount> records) {
    this.records = records;
  }

  public static final class Builder {

    private String accountId;
    private List<PromotionAccount> records;

    private Builder() {}

    public Builder withAccountId(String accountId) {
      this.accountId = accountId;
      return this;
    }

    public Builder withRecords(List<PromotionAccount> records) {
      this.records = records;
      return this;
    }

    public PromotionAccountGroupedByAccount build() {
      return new PromotionAccountGroupedByAccount(this);
    }
  }
}
