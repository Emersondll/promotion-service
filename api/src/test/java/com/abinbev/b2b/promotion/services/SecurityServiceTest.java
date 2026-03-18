package com.abinbev.b2b.promotion.services;

import static com.abinbev.b2b.promotion.constants.ApiConstants.ROLE_READ;
import static com.abinbev.b2b.promotion.constants.ApiConstants.ROLE_WRITE;
import static java.util.Collections.emptyList;

import com.abinbev.b2b.promotion.exceptions.JwtException;
import com.abinbev.b2b.promotion.helpers.JwtGenerator;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SecurityServiceTest {

  @InjectMocks private SecurityService securityService;

  private static final String TOKEN_INVALID = "wrongtoken";
  private static final String SINGLE_ACCOUNT_1 = "0010001001";
  private static final String LIST_ACCOUNT_1 = "12451517";
  private static final String LIST_ACCOUNT_2 = "12451515";
  private static final String EXTENSION_ACCOUNT_1 = "97613757000191";
  private static final String EXTENSION_ACCOUNT_2 = "24053945000132";
  private static final String EXTENSION_ACCOUNT_3 = "68026833000163";
  private static final String DEFAULT_COUNTRY = "BR";

  @Test
  void testSuccessAccounts() {
    boolean isInvalidResult =
        securityService.isRequestInvalid(
            JwtGenerator.generateHMAC(
                Arrays.asList(LIST_ACCOUNT_1, LIST_ACCOUNT_2), DEFAULT_COUNTRY),
            Arrays.asList(LIST_ACCOUNT_1, LIST_ACCOUNT_2),
            DEFAULT_COUNTRY);
    Assertions.assertFalse(isInvalidResult);
  }

  @Test
  void testSuccessExtensionAccounts() {
    boolean isInvalidResult =
        securityService.isRequestInvalid(
            JwtGenerator.generateHMAC(
                Arrays.asList(EXTENSION_ACCOUNT_1, EXTENSION_ACCOUNT_2, EXTENSION_ACCOUNT_3),
                DEFAULT_COUNTRY),
            Arrays.asList(EXTENSION_ACCOUNT_1, EXTENSION_ACCOUNT_2, EXTENSION_ACCOUNT_3),
            DEFAULT_COUNTRY);
    Assertions.assertFalse(isInvalidResult);
  }

  @Test
  void testErrorWrongToken() {
    try {
      securityService.isRequestInvalid(
          TOKEN_INVALID, Collections.singletonList(SINGLE_ACCOUNT_1), DEFAULT_COUNTRY);
      throw new RuntimeException("Expected error parsing");
    } catch (JwtException ignored) {
    }
  }

  @Test
  void testFailAccountsWithNotAccountRequests() {
    boolean isInvalidResult =
        securityService.isRequestInvalid(
            JwtGenerator.generateHMAC(Arrays.asList(LIST_ACCOUNT_1), DEFAULT_COUNTRY),
            emptyList(),
            DEFAULT_COUNTRY);
    Assertions.assertFalse(isInvalidResult);
  }

  @Test
  void testFailWithTokenWithNoAccountID() {
    boolean isInvalidResult =
        securityService.isRequestInvalid(
            JwtGenerator.generateHMAC(emptyList(), DEFAULT_COUNTRY),
            Collections.singletonList(SINGLE_ACCOUNT_1),
            DEFAULT_COUNTRY);
    Assertions.assertTrue(isInvalidResult);
  }

  @Test
  void testFailExtensionsWithNoAccountID() {
    boolean isInvalidResult =
        securityService.isRequestInvalid(
            JwtGenerator.generateHMAC(emptyList(), DEFAULT_COUNTRY), emptyList(), DEFAULT_COUNTRY);
    Assertions.assertFalse(isInvalidResult);
  }

  @Test
  void simulateValidRolesM2M() {
    Assertions.assertFalse(
        securityService.isRequestInvalid(
            JwtGenerator.generateM2M(Arrays.asList(ROLE_WRITE, ROLE_READ)),
            emptyList(),
            DEFAULT_COUNTRY));
  }

  @Test
  void simulateJustReadRoleM2M() {
    Assertions.assertTrue(
        securityService.isRequestInvalid(
            JwtGenerator.generateM2M(Arrays.asList(ROLE_READ)), emptyList(), DEFAULT_COUNTRY));
  }

  @Test
  void simulateJustWriteRoleM2M() {
    Assertions.assertTrue(
        securityService.isRequestInvalid(
            JwtGenerator.generateM2M(Arrays.asList(ROLE_WRITE)), emptyList(), DEFAULT_COUNTRY));
  }

  @Test
  void testSuccessSupportedCountriesWithDifferentCountryFromToken() {

    final String token =
        JwtGenerator.generateB2C(LIST_ACCOUNT_1, DEFAULT_COUNTRY, Arrays.asList("US", "MX"));

    boolean isInvalidResult =
        securityService.isRequestInvalid(token, Collections.singletonList(LIST_ACCOUNT_1), "US");

    Assertions.assertFalse(isInvalidResult);
  }
}
