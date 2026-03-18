package com.abinbev.b2b.promotion.v3.vo;

public class Pagination {
  private final int page;
  private final boolean hasNext;

  public Pagination() {
    this.page = 0;
    this.hasNext = false;
  }

  public Pagination(int page) {
    this.page = page;
    this.hasNext = false;
  }

  public Pagination(int page, boolean hasNext) {
    this.page = page;
    this.hasNext = hasNext;
  }

  public int getPage() {
    return page;
  }

  public boolean isHasNext() {
    return hasNext;
  }
}
