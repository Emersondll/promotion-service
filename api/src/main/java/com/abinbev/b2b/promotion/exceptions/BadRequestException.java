package com.abinbev.b2b.promotion.exceptions;

import java.util.Arrays;

public class BadRequestException extends GlobalException {

  private static final long serialVersionUID = 1L;

  private BadRequestException(final Issue issue) {

    super(issue);
  }

  public static BadRequestException invalidHeader(final String headerName, final String value) {

    return new BadRequestException(
        new Issue(IssueEnum.REQUEST_HEADER_NOT_VALID, headerName, value));
  }

  public static BadRequestException invalidPageSize() {

    return new BadRequestException(new Issue(IssueEnum.INVALID_PAGE_NUMBER));
  }

  public static BadRequestException invalidPageNumber() {

    return new BadRequestException(new Issue(IssueEnum.INVALID_PAGE_NUMBER));
  }

  public static BadRequestException atLeastOneParameterRequired(final Object... params) {
    return new BadRequestException(
        new Issue(IssueEnum.MISSING_AT_LEAST_ONE_REQUIRED_PARAMETER, Arrays.toString(params)));
  }
}
