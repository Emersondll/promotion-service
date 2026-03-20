package com.abinbev.b2b.promotion.relay.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.relay.constants.ApiConstants;
import com.abinbev.b2b.promotion.relay.helpers.HttpContextHelper;
import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.relay.helpers.SecurityHelper;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorRequest;
import com.abinbev.b2b.promotion.relay.services.PromotionMultiVendorService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class PromotionMultiVendorControllerTest {

  @InjectMocks PromotionMultiVendorController promotionMultiVendorController;
  @Mock PromotionMultiVendorService promotionMultiVendorService;
  @Mock SecurityHelper securityHelper;
  @Mock private HttpContextHelper httpContextHelper;
  @Mock private HttpServletRequest httpServletRequest;

  @Test
  public void shouldCreatePromotionThenReturnOk() {

    final PromotionMultiVendorRequest request = PromotionMocks.getPromotionMultiVendorRequestMock();

    when(securityHelper.getValidVendorId(any())).thenReturn(PromotionMocks.VENDOR_ID);
    when(httpContextHelper.getServletRequest()).thenReturn(httpServletRequest);
    when(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER))
        .thenReturn(PromotionMocks.AUTHORIZATION);
    final ResponseEntity<Void> response =
        promotionMultiVendorController.createMultiVendorPromotions(
            request,
            PromotionMocks.COUNTRY,
            PromotionMocks.REQUEST_TRACE_ID,
            PromotionMocks.TIMESTAMP,
            PromotionMocks.PARENT_REQUEST_TRACE_ID);

    verify(promotionMultiVendorService, times(1)).createMultiVendorPromotions(any(), any(), any());
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
  }

  @Test
  public void shouldCreatePromotionThenReturnOkWithTimestamp10() {

    final PromotionMultiVendorRequest request = PromotionMocks.getPromotionMultiVendorRequestMock();

    when(securityHelper.getValidVendorId(any())).thenReturn(PromotionMocks.VENDOR_ID);
    when(httpContextHelper.getServletRequest()).thenReturn(httpServletRequest);
    when(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER))
        .thenReturn(PromotionMocks.AUTHORIZATION);

    final ResponseEntity<Void> response =
        promotionMultiVendorController.createMultiVendorPromotions(
            request,
            PromotionMocks.COUNTRY,
            PromotionMocks.REQUEST_TRACE_ID,
            PromotionMocks.TIMESTAMP_10,
            PromotionMocks.PARENT_REQUEST_TRACE_ID);

    verify(promotionMultiVendorService, times(1)).createMultiVendorPromotions(any(), any(), any());
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
  }

  @Test
  public void shouldCreatePromotionThenReturnOkWithParentRequestTraceIdNull() {

    final PromotionMultiVendorRequest request = PromotionMocks.getPromotionMultiVendorRequestMock();

    when(securityHelper.getValidVendorId(any())).thenReturn(PromotionMocks.VENDOR_ID);
    when(httpContextHelper.getServletRequest()).thenReturn(httpServletRequest);
    when(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER))
        .thenReturn(PromotionMocks.AUTHORIZATION);
    final ResponseEntity<Void> response =
        promotionMultiVendorController.createMultiVendorPromotions(
            request,
            PromotionMocks.COUNTRY,
            PromotionMocks.REQUEST_TRACE_ID,
            PromotionMocks.TIMESTAMP,
            null);

    verify(promotionMultiVendorService, times(1)).createMultiVendorPromotions(any(), any(), any());
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
  }

  @Test
  public void shouldDeletePromotion() {

    final PromotionMultiVendorDeleteRequest request =
        PromotionMocks.getPromotionMultiVendorDeleteRequestMock();

    when(securityHelper.getValidVendorId(any())).thenReturn(PromotionMocks.VENDOR_ID);
    when(httpContextHelper.getServletRequest()).thenReturn(httpServletRequest);
    when(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER))
        .thenReturn(PromotionMocks.AUTHORIZATION);
    final ResponseEntity<Void> response =
        promotionMultiVendorController.deleteMultiVendorPromotions(
            request,
            PromotionMocks.COUNTRY,
            PromotionMocks.REQUEST_TRACE_ID,
            PromotionMocks.TIMESTAMP,
            PromotionMocks.PARENT_REQUEST_TRACE_ID);

    verify(promotionMultiVendorService, times(1)).deleteMultiVendorPromotions(any(), any(), any());
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
  }

  @Test
  public void shouldDeletePromotionWithParentRequestTraceIdNull() {

    final PromotionMultiVendorDeleteRequest request =
        PromotionMocks.getPromotionMultiVendorDeleteRequestMock();

    when(securityHelper.getValidVendorId(any())).thenReturn(PromotionMocks.VENDOR_ID);
    when(httpContextHelper.getServletRequest()).thenReturn(httpServletRequest);
    when(httpServletRequest.getHeader(ApiConstants.AUTHORIZATION_HEADER))
        .thenReturn(PromotionMocks.AUTHORIZATION);
    final ResponseEntity<Void> response =
        promotionMultiVendorController.deleteMultiVendorPromotions(
            request,
            PromotionMocks.COUNTRY,
            PromotionMocks.REQUEST_TRACE_ID,
            PromotionMocks.TIMESTAMP,
            null);

    verify(promotionMultiVendorService, times(1)).deleteMultiVendorPromotions(any(), any(), any());
    Assertions.assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
  }
}
