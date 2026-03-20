package com.abinbev.b2b.promotion.relay.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.config.properties.BulkSizeProperties;
import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.relay.queue.senders.PromotionQueueSender;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromotionMultiVendorServiceTest {

  @InjectMocks private PromotionMultiVendorService service;

  @Mock private PromotionQueueSender queueSender;

  @Mock private BulkSizeProperties properties;

  @BeforeEach
  public void setup() {
    when(properties.getPromotionMultiVendorBulkSize()).thenReturn(100);
  }

  @Test
  public void shouldCreateMultiVendorPromotions() {
    final PromotionMultiVendorRequest requestMock =
        PromotionMocks.getPromotionMultiVendorRequestMock();

    service.createMultiVendorPromotions(
        requestMock.getList(), PromotionMocks.BASE_MESSAGE, PromotionMocks.VENDOR_ID);
    final ArgumentCaptor<BaseMessage> argumentCaptureForMessage =
        ArgumentCaptor.forClass(BaseMessage.class);
    verify(queueSender, times(1)).sendMultiVendor(argumentCaptureForMessage.capture());

    final BaseMessage message = argumentCaptureForMessage.getValue();
    Assertions.assertNotNull(message);
  }

  @Test
  public void shouldCreateMultiVendorPromotionsWithoutParentRequestTraceId() {
    final PromotionMultiVendorRequest requestMock =
        PromotionMocks.getPromotionMultiVendorRequestMock();

    service.createMultiVendorPromotions(
        requestMock.getList(),
        PromotionMocks.BASE_MESSAGE_NO_PARENT_REQUEST_TRACE_ID,
        PromotionMocks.VENDOR_ID);
    final ArgumentCaptor<BaseMessage> argumentCaptureForMessage =
        ArgumentCaptor.forClass(BaseMessage.class);
    verify(queueSender, times(1)).sendMultiVendor(argumentCaptureForMessage.capture());

    final BaseMessage message = argumentCaptureForMessage.getValue();
    Assertions.assertNotNull(message);
  }

  @Test
  public void shouldDeleteMultiVendorPromotions() {
    final PromotionMultiVendorDeleteRequest requestMock =
        PromotionMocks.getPromotionMultiVendorDeleteRequestMock();

    service.deleteMultiVendorPromotions(
        requestMock, PromotionMocks.BASE_MESSAGE, PromotionMocks.VENDOR_ID);
    final ArgumentCaptor<BaseMessage> argumentCaptureForMessage =
        ArgumentCaptor.forClass(BaseMessage.class);
    verify(queueSender, times(1)).sendMultiVendor(argumentCaptureForMessage.capture());

    final BaseMessage message = argumentCaptureForMessage.getValue();
    Assertions.assertNotNull(message);
  }

  @Test
  public void shouldDeleteMultiVendorPromotionsWithoutParentRequestTraceId() {
    final PromotionMultiVendorDeleteRequest requestMock =
        PromotionMocks.getPromotionMultiVendorDeleteRequestMock();

    service.deleteMultiVendorPromotions(
        requestMock,
        PromotionMocks.BASE_MESSAGE_NO_PARENT_REQUEST_TRACE_ID,
        PromotionMocks.VENDOR_ID);
    final ArgumentCaptor<BaseMessage> argumentCaptureForMessage =
        ArgumentCaptor.forClass(BaseMessage.class);
    verify(queueSender, times(1)).sendMultiVendor(argumentCaptureForMessage.capture());

    final BaseMessage message = argumentCaptureForMessage.getValue();
    Assertions.assertNotNull(message);
  }
}
