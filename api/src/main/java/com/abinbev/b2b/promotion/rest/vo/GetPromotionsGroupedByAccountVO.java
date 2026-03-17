package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import java.util.List;

public class GetPromotionsGroupedByAccountVO {

  private final List<PromotionResponseListByAccount> accounts;
  private final Pagination pagination;

  public GetPromotionsGroupedByAccountVO(
      final List<PromotionResponseListByAccount> accounts, final Pagination pagination) {

    this.accounts = accounts;
    this.pagination = pagination;
  }

  public List<PromotionResponseListByAccount> getAccounts() {

    return accounts;
  }

  public Pagination getPagination() {

    return pagination;
  }
}
