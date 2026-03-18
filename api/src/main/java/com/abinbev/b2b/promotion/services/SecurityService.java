package com.abinbev.b2b.promotion.services;

import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.abinbev.b2b.promotion.constants.LogConstant;
import com.abinbev.b2b.promotion.exceptions.JwtException;
import com.abinbev.b2b.promotion.rest.vo.JwtDataVO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class SecurityService {

  private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

  public boolean isRequestInvalid(
      final String token, List<String> accountIds, final String country) {

    final JwtDataVO jwtDataVO = getJwt(token);

    return !isValidB2CRSA(jwtDataVO, accountIds, country)
        && !isValidHMAC(jwtDataVO, accountIds)
        && !isValidM2MRSA(jwtDataVO);
  }

  private JwtDataVO getJwt(final String token) {
    try {
      DecodedJWT decodedJWT = JWT.decode(token.split(" ")[1]);
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(decoderJwtToString(decodedJWT), JwtDataVO.class);
    } catch (final Exception ex) {
      LOGGER.error(LogConstant.ERROR.INVALID_JWT, ex.getLocalizedMessage());
      throw JwtException.decodeException();
    }
  }

  private boolean isValidHMAC(final JwtDataVO jwtDataVO, final List<String> accountIds) {
    if (!CollectionUtils.isEmpty(jwtDataVO.getAccounts())) {
      if (CollectionUtils.isEmpty(accountIds)) {
        return true;
      }
      return isAccountListAuthorized(accountIds, jwtDataVO.getAccounts());
    }
    return false;
  }

  private boolean isValidB2CRSA(
      final JwtDataVO jwtDataVO, List<String> accountIds, final String country) {

    if (checkCountry(jwtDataVO, country) && Objects.nonNull(jwtDataVO.getApp())) {

      if (!ApiConstants.REQUEST_PARAM_B2B.equals(jwtDataVO.getApp())
          || CollectionUtils.isEmpty(accountIds)) {
        return true;
      }

      return jwtDataVO.getExtensionAccountIds() != null
          && isAccountListAuthorized(accountIds, jwtDataVO.getExtensionAccountIds());
    }

    return false;
  }

  private boolean isValidM2MRSA(final JwtDataVO jwtData) {
    return jwtData.getRoles() != null
        && jwtData.getRoles().stream()
            .map(String::toUpperCase)
            .collect(Collectors.toList())
            .containsAll(ApiConstants.ROLES_M2M);
  }

  private boolean isAccountListAuthorized(
      final List<String> accountIds, final List<String> validAccountIds) {
    return validAccountIds.containsAll(accountIds);
  }

  private String decoderJwtToString(DecodedJWT decodedJWT) {
    Map<String, Claim> claimMap = decodedJWT.getClaims();
    Map<String, Object> jwtPayloadMap = new HashMap<>();
    claimMap.forEach((key, value) -> jwtPayloadMap.put(key, value.as(Object.class)));
    return new Gson().toJson(jwtPayloadMap);
  }

  private boolean checkCountry(final JwtDataVO jwtDataVO, final String country) {

    boolean uniqueCountry =
        jwtDataVO.getCountry() != null && jwtDataVO.getCountry().equalsIgnoreCase(country);

    boolean supportedCountries =
        jwtDataVO.getSupportedCountries() != null
            && jwtDataVO.getSupportedCountries().stream()
                .anyMatch(supportedCountry -> supportedCountry.equalsIgnoreCase(country));

    return uniqueCountry || supportedCountries;
  }
}
