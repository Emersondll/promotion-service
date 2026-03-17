package com.abinbev.b2b.promotion.helpers;

import com.abinbev.b2b.promotion.exceptions.BadRequestException;
import org.apache.commons.lang.StringUtils;

public class ControllerValidationHelper {

  private ControllerValidationHelper() {

    throw new IllegalStateException("ControllerValidationHelper class");
  }

  public static void validateRequiredHeader(final String headerName, final String value) {

    if (StringUtils.isBlank(value)) {
      throw BadRequestException.invalidHeader(headerName, value);
    }
  }
}
