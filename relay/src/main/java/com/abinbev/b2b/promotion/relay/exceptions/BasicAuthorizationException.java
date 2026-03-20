package com.abinbev.b2b.promotion.relay.exceptions;

public class BasicAuthorizationException extends GlobalException {

  private static final long serialVersionUID = 1L;

  protected BasicAuthorizationException(final Issue issue) {
    super(issue);
  }

  public static BasicAuthorizationException notAuthorizedBasicAuthentication() {
    return new BasicAuthorizationException(new Issue(IssueEnum.AUTHENTICATION_HEADER_NOT_VALID));
  }
}
