package com.abinbev.b2b.promotion.relay.helpers;

import com.abinbev.b2b.promotion.relay.exceptions.BadRequestException;
import org.apache.commons.lang.StringUtils;

public class ControllerValidationHelper {

  private ControllerValidationHelper() {

    throw new IllegalStateException("ControllerValidationHelper class");
  }

  public static void validateRequiredHeader(final String name, final String value) {

    if (StringUtils.isBlank(value)) {
      throw BadRequestException.invalidHeader(name, value);
    }
  }
}
