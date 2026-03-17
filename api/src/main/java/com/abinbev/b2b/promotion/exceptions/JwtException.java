package com.abinbev.b2b.promotion.exceptions;

public class JwtException extends GlobalException {

  private static final long serialVersionUID = 1L;

  protected JwtException(final Issue issue) {

    super(issue);
  }

  public static JwtException decodeException() {

    return new JwtException(new Issue(IssueEnum.JWT_DECODE_ERROR));
  }

  public static JwtException invalidToken() {

    return new JwtException(new Issue(IssueEnum.JWT_TOKEN_INVALID));
  }
}
