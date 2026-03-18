package com.abinbev.b2b.promotion.aspect;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.annotations.JwtValidation;
import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.abinbev.b2b.promotion.helpers.ContextHelper;
import com.abinbev.b2b.promotion.services.SecurityService;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class JwtValidationAspectTest {

  @InjectMocks private JwtValidationAspect jwtValidationAspect;
  @Mock private ProceedingJoinPoint joinPoint;
  @Mock private SecurityService securityService;
  @Mock private ContextHelper contextHelper;
  @Mock private HttpServletRequest httpServletRequest;
  @Mock private MethodSignature signature;
  @Mock private RuntimeException exception;

  private static final String TOKEN = "tokenabc";
  private static final String COUNTRY = "BR";
  private static final String ACCOUNT_1 = "ACC01";
  private static final String ACCOUNT_2 = "ACC02";
  private static final String ACCOUNT_3 = "ACC03";
  private static final String METHOD_PATH = "/";
  private static final String TRUSTED_PATH = "/swagger-ui.html";
  private static final String METHOD_ACCOUNTS_TEST = "methodAccountsTest";
  private static final String METHOD_NO_ACCOUNT_TEST = "methodNoAccountTest";
  private static final String METHOD_ACCOUNT_TEST = "methodAccountTest";

  @BeforeEach
  public void setup() throws IOException {
    ReflectionTestUtils.setField(jwtValidationAspect, "enableJwtValidation", true);
    when(contextHelper.getServletRequest()).thenReturn(httpServletRequest);
    when(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER)).thenReturn(TOKEN);
    when(httpServletRequest.getHeader(ApiConstants.COUNTRY_HEADER)).thenReturn(COUNTRY);
    when(joinPoint.getSignature()).thenReturn(signature);
  }

  @Test
  public void testToggleOff() throws Throwable {
    ReflectionTestUtils.setField(jwtValidationAspect, "enableJwtValidation", false);
    jwtValidationAspect.verifyJwt(joinPoint);
  }

  @Test
  public void testWithoutToken() throws Throwable {
    when(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER)).thenReturn(null);
    jwtValidationAspect.verifyJwt(joinPoint);
  }

  @Test
  public void testTrustablePath() throws Throwable {
    when(httpServletRequest.getRequestURI()).thenReturn(TRUSTED_PATH);
    jwtValidationAspect.verifyJwt(joinPoint);
  }

  @Test
  public void testSingleAccountSuccess() throws Throwable {
    when(joinPoint.getArgs()).thenReturn(Collections.singletonList(ACCOUNT_1).toArray());
    when(signature.getMethod()).thenReturn(getClass().getMethod(METHOD_ACCOUNT_TEST, String.class));
    when(httpServletRequest.getRequestURI()).thenReturn(METHOD_PATH);
    when(securityService.isRequestInvalid(anyString(), anyList(), anyString())).thenReturn(false);
    jwtValidationAspect.verifyJwt(joinPoint);
    verify(securityService).isRequestInvalid(anyString(), anyList(), anyString());
  }

  @Test
  public void testMultipleAccountSuccess() throws Throwable {
    when(joinPoint.getArgs())
        .thenReturn(
            Collections.singletonList(Arrays.asList(ACCOUNT_1, ACCOUNT_2, ACCOUNT_3)).toArray());
    when(signature.getMethod()).thenReturn(getClass().getMethod(METHOD_ACCOUNTS_TEST, List.class));
    when(httpServletRequest.getRequestURI()).thenReturn(METHOD_PATH);
    when(securityService.isRequestInvalid(anyString(), anyList(), anyString())).thenReturn(false);
    jwtValidationAspect.verifyJwt(joinPoint);
    verify(securityService).isRequestInvalid(anyString(), anyList(), anyString());
  }

  @Test
  public void testSingleAccountInvalid() throws Throwable {
    when(joinPoint.getArgs()).thenReturn(Collections.singletonList(ACCOUNT_1).toArray());
    when(signature.getMethod()).thenReturn(getClass().getMethod(METHOD_ACCOUNT_TEST, String.class));
    when(httpServletRequest.getRequestURI()).thenReturn(METHOD_PATH);
    when(securityService.isRequestInvalid(anyString(), anyList(), anyString())).thenReturn(true);
    jwtValidationAspect.verifyJwt(joinPoint);
    verify(securityService).isRequestInvalid(anyString(), anyList(), anyString());
  }

  @Test
  public void testMultipleAccountInvalid() throws Throwable {
    when(joinPoint.getArgs())
        .thenReturn(
            Collections.singletonList(Arrays.asList(ACCOUNT_1, ACCOUNT_2, ACCOUNT_3)).toArray());
    when(signature.getMethod()).thenReturn(getClass().getMethod(METHOD_ACCOUNTS_TEST, List.class));
    when(httpServletRequest.getRequestURI()).thenReturn(METHOD_PATH);
    when(securityService.isRequestInvalid(anyString(), anyList(), anyString())).thenReturn(true);
    jwtValidationAspect.verifyJwt(joinPoint);
    verify(securityService).isRequestInvalid(anyString(), anyList(), anyString());
  }

  @Test
  public void testServiceError() throws Throwable {
    when(joinPoint.getArgs()).thenReturn(Collections.singletonList(ACCOUNT_1).toArray());
    when(signature.getMethod()).thenReturn(getClass().getMethod(METHOD_ACCOUNT_TEST, String.class));
    when(httpServletRequest.getRequestURI()).thenReturn(METHOD_PATH);
    when(securityService.isRequestInvalid(anyString(), anyList(), anyString()))
        .thenThrow(exception);
    jwtValidationAspect.verifyJwt(joinPoint);
  }

  @Test
  public void testServiceNoAccount() throws Throwable {
    when(joinPoint.getArgs()).thenReturn(Collections.emptyList().toArray());
    when(signature.getMethod()).thenReturn(getClass().getMethod(METHOD_NO_ACCOUNT_TEST));
    when(httpServletRequest.getRequestURI()).thenReturn(METHOD_PATH);
    when(securityService.isRequestInvalid(anyString(), anyList(), anyString()))
        .thenThrow(exception);
    jwtValidationAspect.verifyJwt(joinPoint);
  }

  @JwtValidation(position = 0)
  public void methodAccountTest(String accountId) {}

  @JwtValidation(position = 0, isMultiple = true)
  public void methodAccountsTest(List<String> accountIds) {}

  @JwtValidation(hasAccount = false)
  public void methodNoAccountTest() {}
}
