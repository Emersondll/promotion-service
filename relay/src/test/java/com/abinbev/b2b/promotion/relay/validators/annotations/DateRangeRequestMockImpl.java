package com.abinbev.b2b.promotion.relay.validators.annotations;

public class DateRangeRequestMockImpl implements DateRangeRequest {

  private String startDate;
  private String endDate;

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  @Override
  public String getStartDate() {
    return startDate;
  }

  @Override
  public String getEndDate() {
    return endDate;
  }
}
