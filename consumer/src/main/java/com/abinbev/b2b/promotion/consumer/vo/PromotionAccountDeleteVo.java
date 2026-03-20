package com.abinbev.b2b.promotion.consumer.vo;

import java.util.List;

public class PromotionAccountDeleteVo {

  private List<String> promotions;
  private List<String> accounts;

  public List<String> getPromotions() {

    return promotions;
  }

  public void setPromotions(List<String> promotions) {

    this.promotions = promotions;
  }

  public List<String> getAccounts() {

    return accounts;
  }

  public void setAccounts(List<String> accounts) {

    this.accounts = accounts;
  }
}
