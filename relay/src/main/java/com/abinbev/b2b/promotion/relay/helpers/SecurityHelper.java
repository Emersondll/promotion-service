package com.abinbev.b2b.promotion.relay.helpers;

import com.abinbev.b2b.promotion.relay.constants.LogConstant;
import com.abinbev.b2b.promotion.relay.domain.JwtData;
import com.abinbev.b2b.promotion.relay.exceptions.BasicAuthorizationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class SecurityHelper {
  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHelper.class);

  public String getValidVendorId(final String token) {
    final String vendorId = getJwt(token).getVendorId();
    if (ObjectUtils.isEmpty(vendorId)) {
      throw BasicAuthorizationException.notAuthorizedBasicAuthentication();
    }
    return vendorId;
  }

  private JwtData getJwt(final String token) {
    if (ObjectUtils.isEmpty(token)) {
      throw BasicAuthorizationException.notAuthorizedBasicAuthentication();
    }
    try {
      return new ObjectMapper()
          .readValue(new String(Base64.getDecoder().decode(token.split("\\.")[1])), JwtData.class);
    } catch (final Exception ex) {
      LOGGER.error(LogConstant.ERROR.INVALID_JWT, ex.getLocalizedMessage());
      throw BasicAuthorizationException.notAuthorizedBasicAuthentication();
    }
  }
}
