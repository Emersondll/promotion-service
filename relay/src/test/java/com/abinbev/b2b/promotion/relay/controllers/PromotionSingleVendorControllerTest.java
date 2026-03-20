package com.abinbev.b2b.promotion.relay.controllers;

import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorRequest;
import com.abinbev.b2b.promotion.relay.services.PromotionSingleVendorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class PromotionSingleVendorControllerTest {

  @InjectMocks PromotionSingleVendorController controller;

  @Mock PromotionSingleVendorService promotionSingleVendorService;

  @Test
  public void createPromotionThenReturnOk() {

    final PromotionSingleVendorRequest request =
        PromotionMocks.getPromotionSingleVendorRequestMock();
    final ResponseEntity<Void> response =
        controller.createPromotions(
            request,
            PromotionMocks.COUNTRY,
            PromotionMocks.REQUEST_TRACE_ID,
            PromotionMocks.TIMESTAMP);
    Assertions.assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
  }

  @Test
  public void createPromotionSecsTimestampThenReturnAccept() {

    final PromotionSingleVendorRequest request =
        PromotionMocks.getPromotionSingleVendorRequestMock();
    final ResponseEntity<Void> response =
        controller.createPromotions(
            request,
            PromotionMocks.COUNTRY,
            PromotionMocks.REQUEST_TRACE_ID,
            PromotionMocks.TIMESTAMP_SECS);
    Assertions.assertEquals(response.getStatusCode().value(), HttpStatus.OK.value());
  }

  @Test
  public void deletePromotionWithSuccess() {

    final ResponseEntity<Void> response =
        controller.deletePromotion(
            new PromotionSingleVendorDeleteRequest(),
            PromotionMocks.COUNTRY,
            PromotionMocks.TIMESTAMP,
            PromotionMocks.REQUEST_TRACE_ID);
    Assertions.assertEquals(HttpStatus.ACCEPTED.value(), response.getStatusCode().value());
  }
}
