package com.abinbev.b2b.promotion.relay.exceptions;

public class NotFoundException extends GlobalException {

  private static final long serialVersionUID = 1L;

  private NotFoundException(final Issue issue) {

    super(issue);
  }

  public static NotFoundException countryNotFound(final String country) {

    return new NotFoundException(new Issue(IssueEnum.COUNTRY_NOT_FOUND, country));
  }
}
