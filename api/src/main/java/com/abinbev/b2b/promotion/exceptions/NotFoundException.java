package com.abinbev.b2b.promotion.exceptions;

public class NotFoundException extends GlobalException {

  private static final long serialVersionUID = 1L;

  protected NotFoundException(final Issue issue) {

    super(issue);
  }

  public static NotFoundException noPromotionsFound() {

    return new NotFoundException(new Issue(IssueEnum.NO_PROMOTIONS_FOUND));
  }

  public static NotFoundException promotionsNotExist() {

    return new NotFoundException(new Issue(IssueEnum.PROMOTIONS_NOT_EXIST));
  }

  public static NotFoundException countryNotFound(final String country) {

    return new NotFoundException(new Issue(IssueEnum.COUNTRY_NOT_FOUND, country));
  }
}
