package com.abinbev.b2b.promotion.relay.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.config.properties.BulkSizeProperties;
import com.abinbev.b2b.promotion.relay.config.properties.SingleToMultiVendorConversionProperties;
import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.relay.mapper.PromotionMapper;
import com.abinbev.b2b.promotion.relay.queue.senders.PromotionQueueSender;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorRequest;
import com.google.common.collect.Sets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromotionSingleVendorServiceTest {

  PromotionSingleVendorService service;

  @Mock PromotionQueueSender queueSender;

  @Mock BulkSizeProperties properties;

  @Mock SingleToMultiVendorConversionProperties conversionProperties;

  @BeforeEach
  public void setup() {
    service =
        new PromotionSingleVendorService(
            queueSender, properties, conversionProperties, PromotionMapper.INSTANCE);
    when(properties.getPromotionBulkSize()).thenReturn(100);
  }

  @Test
  public void createPromotions() {
    final PromotionSingleVendorRequest requestMock =
        PromotionMocks.getPromotionSingleVendorRequestMock();

    service.createPromotions(requestMock.getList(), PromotionMocks.BASE_MESSAGE);
    final ArgumentCaptor<BaseMessage> argumentCaptureForMessage =
        ArgumentCaptor.forClass(BaseMessage.class);
    verify(queueSender, times(1)).sendMultiVendor(argumentCaptureForMessage.capture());

    final BaseMessage message = argumentCaptureForMessage.getValue();
    assertNotNull(message);
  }

  @Test
  public void deletePromotion() {

    PromotionSingleVendorDeleteRequest promotionSingleVendorDeleteRequest =
        new PromotionSingleVendorDeleteRequest();
    promotionSingleVendorDeleteRequest.setPromotions(Sets.newHashSet(PromotionMocks.PROMOTION_ID));

    service.deletePromotion(promotionSingleVendorDeleteRequest, PromotionMocks.BASE_MESSAGE);
    final ArgumentCaptor<BaseMessage> argumentCaptureForMessage =
        ArgumentCaptor.forClass(BaseMessage.class);
    verify(queueSender, times(1)).sendMultiVendor(argumentCaptureForMessage.capture());

    final BaseMessage message = argumentCaptureForMessage.getValue();
    assertNotNull(message);
    assertEquals(PromotionMocks.COUNTRY, message.getCountry());
    assertEquals(PromotionMocks.REQUEST_TRACE_ID, message.getRequestTraceId());
    assertEquals(PromotionMocks.TIMESTAMP, message.getTimestamp());

    PromotionMultiVendorDeleteRequest payload =
        (PromotionMultiVendorDeleteRequest) message.getPayload();
    assertTrue(payload.getVendorPromotionIds().contains(PromotionMocks.PROMOTION_ID));
  }

  @Test
  public void givenSingleToMultiVendorConversionModeTrueWhenPostThenShouldSendToMultiVendorQueue() {
    when(conversionProperties.getVendorIdByCountry(PromotionMocks.COUNTRY)).thenReturn("0x001");

    final var promotionMocks = PromotionMocks.getPromotionSingleVendorRequestMock().getList();
    service.createPromotions(promotionMocks, PromotionMocks.BASE_MESSAGE);

    var argument = ArgumentCaptor.forClass(BaseMessage.class);
    verify(queueSender).sendMultiVendor(argument.capture());
    assertEquals("0x001", argument.getValue().getVendorId());
    assertDoesNotThrow(() -> (List<PromotionMultiVendor>) argument.getValue().getPayload());
    var multivendor = ((List<PromotionMultiVendor>) argument.getValue().getPayload()).get(0);
    var singlevendor = promotionMocks.get(0);
    assertEquals(singlevendor.getBudget(), multivendor.getBudget());
    assertEquals(singlevendor.getDescription(), multivendor.getDescription());
    assertEquals(singlevendor.getEndDate(), multivendor.getEndDate());
    assertEquals(singlevendor.getId(), multivendor.getVendorPromotionId());
    assertEquals(singlevendor.getImage(), multivendor.getImage());
    assertEquals(singlevendor.getQuantityLimit(), multivendor.getQuantityLimit());
    assertEquals(singlevendor.getStartDate(), multivendor.getStartDate());
    assertEquals(singlevendor.getTitle(), multivendor.getTitle());
    assertEquals(singlevendor.getStartDate(), multivendor.getStartDate());
    assertEquals(singlevendor.getType(), multivendor.getType());
  }

  @Test
  public void
      givenSingleToMultiVendorConversionModeFalseWhenPostThenShouldSendToSingleVendorQueue() {
    service.createPromotions(
        PromotionMocks.getPromotionSingleVendorRequestMock().getList(),
        PromotionMocks.BASE_MESSAGE);
    verify(queueSender).sendMultiVendor(any());
  }

  @Test
  public void shouldDeleteInMultiVendorQueueRetroCompatible() {

    when(conversionProperties.getVendorIdByCountry(PromotionMocks.COUNTRY))
        .thenReturn(PromotionMocks.VENDOR_ID);

    service.deletePromotion(
        PromotionMocks.getPromotionSingleVendorDeleteRequestMock(), PromotionMocks.BASE_MESSAGE);

    verify(queueSender).sendMultiVendor(any());
  }

  @Test
  public void shouldDeleteInSingleVendorQueueRetroCompatible() {
    service.deletePromotion(
        PromotionMocks.getPromotionSingleVendorDeleteRequestMock(), PromotionMocks.BASE_MESSAGE);

    verify(queueSender).sendMultiVendor(any());
  }
}
