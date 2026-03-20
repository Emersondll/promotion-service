package com.abinbev.b2b.promotion.relay.exceptions;

public class BadRequestException extends GlobalException {

  private static final long serialVersionUID = 1L;

  private BadRequestException(final Issue issue) {
    super(issue);
  }

  public static BadRequestException invalidHeader(final String name, final String value) {
    return new BadRequestException(new Issue(IssueEnum.REQUEST_HEADER_NOT_VALID, name, value));
  }

  public static BadRequestException countryWithoutDefaultVendor(final String country) {
    return new BadRequestException(new Issue(IssueEnum.VENDOR_ID_NOT_FOUND, country));
  }
}
