package com.abinbev.b2b.promotion.consumer.service;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.abinbev.b2b.data.ingestion.feedback.layer.service.FeedbackLayerService;
import com.abinbev.b2b.promotion.consumer.domain.PromotionMultiVendorModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionType;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMocks;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMultiVendorMocks;
import com.abinbev.b2b.promotion.consumer.listener.message.PromotionMultiVendorMessage;
import com.abinbev.b2b.promotion.consumer.listener.properties.FeedbackLayerProperties;
import com.abinbev.b2b.promotion.consumer.repository.PromotionRepository;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountMultiVendorDeleteVO;
import com.abinbev.b2b.promotion.consumer.vo.PromotionMultiVendorVO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.MongoBulkWriteException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PromotionMultiVendorServiceTest {

  private static final String PROMOTION_ENTITY_NAME = "PROMOTION";

  @InjectMocks
  private PromotionMultiVendorService promotionMultiVendorService;

  @Mock
  private PromotionRepository promotionRepository;

  @Mock
  private FeedbackLayerProperties feedbackLayerProperties;

  @Captor
  private ArgumentCaptor<List<PromotionMultiVendorModel>> promotionsCaptor;

  @Mock
  private FeedbackLayerService feedbackLayerService;

  @Mock
  private ObjectMapper objectMapper;

  @Test
  public void shouldSentToFeedbackLayerWhenSaveMessage() {

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    promotionMultiVendorVOList.add(
            PromotionMultiVendorMocks.PromotionMultiVendorVOBuilder.newBuilder()
                    .withTitle(PromotionMocks.TITLE)
                    .withDescription(PromotionMocks.DESCRIPTION)
                    .withStartDate(PromotionMocks.START_DATE)
                    .withEndDate(PromotionMocks.END_DATE)
                    .withBudget(PromotionMocks.BUDGET)
                    .withType(PromotionType.DISCOUNT)
                    .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
                    .withImage(PromotionMocks.IMAGE)
                    .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
                    .withTranslations(PromotionMocks.getTranslations())
                    .withDefaultLanguage(PromotionMocks.EN_US)
                    .build());

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.save(promotionMultiVendorVOList, message);

    verify(feedbackLayerService, times(1)).createTrackingRecord(
            PromotionMocks.PARENT_REQUEST_TRACE_ID, PromotionMocks.REQUEST_TRACE_ID, POST_OPERATION,
            API_PROMOTION_VERSION_V2,
            PROMOTION_ENTITY_NAME, PromotionMocks.COUNTRY, VENDOR_ID, PROMOTION_ENTITY_NAME);
    verify(feedbackLayerService, times(1)).publish(PromotionMocks.REQUEST_TRACE_ID);

    verify(promotionRepository, times(1))
            .insertMultiVendorBulk(promotionsCaptor.capture(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    var promotions = promotionsCaptor.getValue();
    assertThat(promotions).isNotEmpty();
    promotions.forEach(promotion -> assertThat(promotion.getPromotionPlatformId()).isNotNull());

    verify(feedbackLayerService, times(1)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void shouldNotSentToFeedbackLayerWhenParentRequestTraceIdIsNull() {

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();
    message.setParentRequestTraceId(null);

    promotionMultiVendorVOList.add(
            PromotionMultiVendorMocks.PromotionMultiVendorVOBuilder.newBuilder()
                    .withTitle(PromotionMocks.TITLE)
                    .withDescription(PromotionMocks.DESCRIPTION)
                    .withStartDate(PromotionMocks.START_DATE)
                    .withEndDate(PromotionMocks.END_DATE)
                    .withBudget(PromotionMocks.BUDGET)
                    .withType(PromotionType.DISCOUNT)
                    .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
                    .withImage(PromotionMocks.IMAGE)
                    .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
                    .withTranslations(PromotionMocks.getTranslations())
                    .withDefaultLanguage(PromotionMocks.EN_US)
                    .build());

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.save(promotionMultiVendorVOList, message);

    verify(feedbackLayerService, times(0)).createTrackingRecord(
            PromotionMocks.PARENT_REQUEST_TRACE_ID, PromotionMocks.REQUEST_TRACE_ID, POST_OPERATION,
            API_PROMOTION_VERSION_V2,
            PROMOTION_ENTITY_NAME, PromotionMocks.COUNTRY, VENDOR_ID, PROMOTION_ENTITY_NAME);
    verify(feedbackLayerService, times(0)).publish(PromotionMocks.REQUEST_TRACE_ID);

    verify(promotionRepository, times(1))
            .insertMultiVendorBulk(promotionsCaptor.capture(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    var promotions = promotionsCaptor.getValue();
    assertThat(promotions).isNotEmpty();
    promotions.forEach(promotion -> assertThat(promotion.getPromotionPlatformId()).isNotNull());

    verify(feedbackLayerService, times(0)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(0)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void shouldThrowDuplicateExceptionWhenHasErrorAndSentToFeedbackLayerFailureMessage() throws JsonProcessingException {

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();
    promotionMultiVendorVOList.add(
            PromotionMultiVendorMocks.PromotionMultiVendorVOBuilder.newBuilder()
                    .withTitle(PromotionMocks.TITLE)
                    .withDescription(PromotionMocks.DESCRIPTION)
                    .withStartDate(PromotionMocks.START_DATE)
                    .withEndDate(PromotionMocks.END_DATE)
                    .withBudget(PromotionMocks.BUDGET)
                    .withType(PromotionType.DISCOUNT)
                    .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
                    .withImage(PromotionMocks.IMAGE)
                    .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
                    .withTranslations(PromotionMocks.getTranslations())
                    .withDefaultLanguage(PromotionMocks.EN_US)
                    .build());

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    String promotionsToString = convertPromotionMultiVendorListAsString(promotionMultiVendorVOList);

    doReturn(promotionsToString).when(objectMapper).writeValueAsString(promotionMultiVendorVOList);

    doThrow(new DuplicateKeyException("")).when(promotionRepository)
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    assertThrows(DuplicateKeyException.class, () -> {
      promotionMultiVendorService.save(promotionMultiVendorVOList, message);
    });

    verify(feedbackLayerService, times(1)).createTrackingRecord(
            PromotionMocks.PARENT_REQUEST_TRACE_ID,
            PromotionMocks.REQUEST_TRACE_ID, POST_OPERATION,
            API_PROMOTION_VERSION_V2,
            PROMOTION_ENTITY_NAME,
            PromotionMocks.COUNTRY,
            VENDOR_ID,
            PROMOTION_ENTITY_NAME
    );

    verify(feedbackLayerService, times(1)).setPayload(PromotionMocks.REQUEST_TRACE_ID,
            promotionsToString);
    verify(promotionRepository, times(1))
            .insertMultiVendorBulk(promotionsCaptor.capture(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(1)).setFailure(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(0)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void shouldThrowDuplicateKeyExceptionWhenFeedbackLayerIsInactive() {

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();
    promotionMultiVendorVOList.add(
            PromotionMultiVendorMocks.PromotionMultiVendorVOBuilder.newBuilder()
                    .withTitle(PromotionMocks.TITLE)
                    .withDescription(PromotionMocks.DESCRIPTION)
                    .withStartDate(PromotionMocks.START_DATE)
                    .withEndDate(PromotionMocks.END_DATE)
                    .withBudget(PromotionMocks.BUDGET)
                    .withType(PromotionType.DISCOUNT)
                    .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
                    .withImage(PromotionMocks.IMAGE)
                    .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
                    .withTranslations(PromotionMocks.getTranslations())
                    .withDefaultLanguage(PromotionMocks.EN_US)
                    .build());
    doReturn(false).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    doThrow(new DuplicateKeyException("")).when(promotionRepository)
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    assertThrows(DuplicateKeyException.class, () -> {
      promotionMultiVendorService.save(promotionMultiVendorVOList, message);
    });

    verify(feedbackLayerService, times(0)).createTrackingRecord(
            PromotionMocks.PARENT_REQUEST_TRACE_ID,
            PromotionMocks.REQUEST_TRACE_ID, POST_OPERATION,
            API_PROMOTION_VERSION_V2,
            PROMOTION_ENTITY_NAME,
            PromotionMocks.COUNTRY,
            VENDOR_ID,
            PROMOTION_ENTITY_NAME
    );
    verify(feedbackLayerService, times(0)).setFailure(PromotionMocks.REQUEST_TRACE_ID);

    String promotionsToString = convertPromotionMultiVendorListAsString(promotionMultiVendorVOList);

    verify(feedbackLayerService, times(0)).setPayload(PromotionMocks.REQUEST_TRACE_ID,
            promotionsToString);
    verify(promotionRepository, times(1))
            .insertMultiVendorBulk(promotionsCaptor.capture(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(0)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(0)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void testSaveEmptyMessageWhenFeedbackLayerIsInactiveForCountry() {

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    promotionMultiVendorService.save(promotionMultiVendorVOList, message);

    verify(promotionRepository, times(0))
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(0)).createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(0)).publish(REQUEST_TRACE_ID_HEADER);
  }

  @Test
  public void testSaveEmptyMessageWhenFeedbackLayerIsActiveForCountry() {

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.save(promotionMultiVendorVOList, message);

    verify(promotionRepository, times(0))
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(0))
            .createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(0)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void testSaveNullMessageWhenFeedbackLayerIsActiveForCountry() {

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.save(null, message);
    verify(promotionRepository, times(0))
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(0))
            .createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(0)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void shouldNotSaveNullMessageWhenFeedbackLayerIsInactiveForCountry() {

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    doReturn(false).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.save(null, message);
    verify(promotionRepository, times(0))
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(0))
            .createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(0)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void shouldNotDeleteWhenVendorAccountHasValueAndTheFeedbackLayerIsActiveForCountry() {
    final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO =
            new PromotionAccountMultiVendorDeleteVO();

    promotionAccountMultiVendorDeleteVO.setVendorAccountIds(
            Collections.singletonList(PromotionMocks.ACCOUNT_ID));
    promotionAccountMultiVendorDeleteVO.setVendorPromotionIds(
            Collections.singletonList(PromotionMocks.VENDOR_PROMOTION_ID));

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.remove(promotionAccountMultiVendorDeleteVO, message);
    verify(promotionRepository, times(0))
            .softDeleteMultiVendor(
                    eq(promotionAccountMultiVendorDeleteVO),
                    eq(PromotionMocks.VENDOR_ID),
                    eq(PromotionMocks.TIMESTAMP),
                    eq(PromotionMocks.COUNTRY));

    verify(feedbackLayerService, times(1)).createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(0)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).publish(any());
  }

  @Test
  public void shouldThrowExceptionWhenHasErrorOnDeleteAndTheFeedbackLayerIsActiveForCountry() {
    final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO =
            new PromotionAccountMultiVendorDeleteVO();

    promotionAccountMultiVendorDeleteVO.setVendorPromotionIds(
            Collections.singletonList(PromotionMocks.VENDOR_PROMOTION_ID));

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    doThrow(new BulkOperationException("", mock(MongoBulkWriteException.class))).when(promotionRepository)
            .softDeleteMultiVendor(any(), eq(message.getVendorId()), eq(message.getTimestamp()), eq(message.getCountry()));

    assertThrows(BulkOperationException.class, () -> {
      promotionMultiVendorService.remove(promotionAccountMultiVendorDeleteVO, message);
    });

    verify(promotionRepository, times(1))
            .softDeleteMultiVendor(
                    eq(promotionAccountMultiVendorDeleteVO),
                    eq(PromotionMocks.VENDOR_ID),
                    eq(PromotionMocks.TIMESTAMP),
                    eq(PromotionMocks.COUNTRY));

    verify(feedbackLayerService, times(1)).createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(1)).setFailure(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).publish(any());
  }

  @Test
  public void shouldNotDeleteWhenVendorAccountIdsHasValueAndTheFeedbackLayerIsInactiveForCountry() {
    final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO =
            new PromotionAccountMultiVendorDeleteVO();

    promotionAccountMultiVendorDeleteVO.setVendorAccountIds(
            Collections.singletonList(PromotionMocks.ACCOUNT_ID));
    promotionAccountMultiVendorDeleteVO.setVendorPromotionIds(
            Collections.singletonList(PromotionMocks.VENDOR_PROMOTION_ID));

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    promotionMultiVendorService.remove(promotionAccountMultiVendorDeleteVO, message);
    verify(promotionRepository, times(0))
            .softDeleteMultiVendor(
                    eq(promotionAccountMultiVendorDeleteVO),
                    eq(PromotionMocks.VENDOR_ID),
                    eq(PromotionMocks.TIMESTAMP),
                    eq(PromotionMocks.COUNTRY));

    verify(feedbackLayerService, times(0))
            .createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(0)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(0)).publish(any());
  }

  @Test
  public void shouldDeleteWhenVendorAccountIdsHasEmptyAndTheFeedbackLayerIsActiveForCountry() {
    final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO =
            new PromotionAccountMultiVendorDeleteVO();
    promotionAccountMultiVendorDeleteVO.setVendorPromotionIds(
            Collections.singletonList(PromotionMocks.VENDOR_PROMOTION_ID));

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.remove(promotionAccountMultiVendorDeleteVO, message);
    verify(promotionRepository, times(1))
            .softDeleteMultiVendor(
                    eq(promotionAccountMultiVendorDeleteVO),
                    eq(PromotionMocks.VENDOR_ID),
                    eq(PromotionMocks.TIMESTAMP),
                    eq(PromotionMocks.COUNTRY));

    verify(feedbackLayerService, times(1))
            .createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(1)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).publish(any());
  }

  @Test
  public void testDeleteNoAccountsSuccessWhenTheFeedbackLayerIsInactiveForCountry() {
    final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO =
            new PromotionAccountMultiVendorDeleteVO();
    promotionAccountMultiVendorDeleteVO.setVendorPromotionIds(
            Collections.singletonList(PromotionMocks.VENDOR_PROMOTION_ID));

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    promotionMultiVendorService.remove(promotionAccountMultiVendorDeleteVO, message);
    verify(promotionRepository, times(1))
            .softDeleteMultiVendor(
                    eq(promotionAccountMultiVendorDeleteVO),
                    eq(PromotionMocks.VENDOR_ID),
                    eq(PromotionMocks.TIMESTAMP),
                    eq(PromotionMocks.COUNTRY));

    verify(feedbackLayerService, times(0))
            .createTrackingRecord(any(), any(), any(), any(), any(), any(), any(), any());
    verify(feedbackLayerService, times(0)).setSuccess(REQUEST_TRACE_ID_HEADER);
    verify(feedbackLayerService, times(0)).publish(any());
  }

  @Test
  public void testSaveMessageWithMetaDataDeactivatedAndWhenFeedbackLayerIsActive() {

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();
    promotionMultiVendorVOList.add(
            PromotionMultiVendorMocks.PromotionMultiVendorVOBuilder.newBuilder()
                    .withTitle(PromotionMocks.TITLE)
                    .withDescription(PromotionMocks.DESCRIPTION)
                    .withStartDate(PromotionMocks.START_DATE)
                    .withEndDate(PromotionMocks.END_DATE)
                    .withBudget(PromotionMocks.BUDGET)
                    .withType(PromotionType.DISCOUNT)
                    .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
                    .withImage(PromotionMocks.IMAGE)
                    .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
                    .withTranslations(PromotionMocks.getTranslations())
                    .withDefaultLanguage(PromotionMocks.EN_US)
                    .build());

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    promotionMultiVendorService.save(promotionMultiVendorVOList, message);

    verify(promotionRepository, times(1))
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(1))
            .createTrackingRecord(PromotionMocks.PARENT_REQUEST_TRACE_ID, PromotionMocks.REQUEST_TRACE_ID,
                    POST_OPERATION, API_PROMOTION_VERSION_V2, PROMOTION_ENTITY_NAME,
                    PromotionMocks.COUNTRY, VENDOR_ID, PROMOTION_ENTITY_NAME);
    verify(feedbackLayerService, times(1)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void testSaveMessageWithMetaDataDeactivatedAndWhenFeedbackLayerIsInactive() {

    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();
    promotionMultiVendorVOList.add(
            PromotionMultiVendorMocks.PromotionMultiVendorVOBuilder.newBuilder()
                    .withTitle(PromotionMocks.TITLE)
                    .withDescription(PromotionMocks.DESCRIPTION)
                    .withStartDate(PromotionMocks.START_DATE)
                    .withEndDate(PromotionMocks.END_DATE)
                    .withBudget(PromotionMocks.BUDGET)
                    .withType(PromotionType.DISCOUNT)
                    .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
                    .withImage(PromotionMocks.IMAGE)
                    .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
                    .withTranslations(PromotionMocks.getTranslations())
                    .withDefaultLanguage(PromotionMocks.EN_US)
                    .build());

    promotionMultiVendorService.save(promotionMultiVendorVOList, message);

    verify(promotionRepository, times(1))
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(0))
            .createTrackingRecord(PromotionMocks.PARENT_REQUEST_TRACE_ID, PromotionMocks.REQUEST_TRACE_ID,
                    POST_OPERATION, API_PROMOTION_VERSION_V2, PROMOTION_ENTITY_NAME,
                    PromotionMocks.COUNTRY, VENDOR_ID, PROMOTION_ENTITY_NAME);
    verify(feedbackLayerService, times(0)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(0)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  @Test
  public void shouldThrowExceptionWhenConvertPromotionAsString() throws JsonProcessingException {
    final PromotionMultiVendorMessage message = getPromotionMultiVendorMessage();

    final List<PromotionMultiVendorVO> promotionMultiVendorVOList = new ArrayList<>();
    promotionMultiVendorVOList.add(
            PromotionMultiVendorMocks.PromotionMultiVendorVOBuilder.newBuilder()
                    .withTitle(PromotionMocks.TITLE)
                    .withDescription(PromotionMocks.DESCRIPTION)
                    .withStartDate(PromotionMocks.START_DATE)
                    .withEndDate(PromotionMocks.END_DATE)
                    .withBudget(PromotionMocks.BUDGET)
                    .withType(PromotionType.DISCOUNT)
                    .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
                    .withImage(PromotionMocks.IMAGE)
                    .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
                    .withTranslations(PromotionMocks.getTranslations())
                    .withDefaultLanguage(PromotionMocks.EN_US)
                    .build());

    String promotionsToString = convertPromotionMultiVendorListAsString(promotionMultiVendorVOList);

    doReturn(true).when(feedbackLayerProperties)
            .isFeedbackLayerIntegrationActiveForCountry(PromotionMocks.COUNTRY);

    doThrow(new DuplicateKeyException("")).when(promotionRepository)
            .insertMultiVendorBulk(anyList(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));
    doThrow(new JsonProcessingException("") {}).when(objectMapper).writeValueAsString(promotionMultiVendorVOList);

    assertThrows(DuplicateKeyException.class, () -> {
      promotionMultiVendorService.save(promotionMultiVendorVOList, message);
    });

    verify(feedbackLayerService, times(1)).createTrackingRecord(
            PromotionMocks.PARENT_REQUEST_TRACE_ID,
            PromotionMocks.REQUEST_TRACE_ID, POST_OPERATION,
            API_PROMOTION_VERSION_V2,
            PROMOTION_ENTITY_NAME,
            PromotionMocks.COUNTRY,
            VENDOR_ID,
            PROMOTION_ENTITY_NAME
    );

    verify(promotionRepository, times(1))
            .insertMultiVendorBulk(promotionsCaptor.capture(), eq(PromotionMocks.COUNTRY), eq(PromotionMocks.TIMESTAMP));

    verify(feedbackLayerService, times(1)).setFailure(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).setPayload(PromotionMocks.REQUEST_TRACE_ID,
            null);
    verify(feedbackLayerService, times(0)).setSuccess(PromotionMocks.REQUEST_TRACE_ID);
    verify(feedbackLayerService, times(1)).publish(PromotionMocks.REQUEST_TRACE_ID);
  }

  private static PromotionMultiVendorMessage getPromotionMultiVendorMessage() {

    final PromotionMultiVendorMessage message = new PromotionMultiVendorMessage();
    message.setVersion(API_PROMOTION_VERSION_V2);
    message.setRequestTraceId(PromotionMocks.REQUEST_TRACE_ID);
    message.setParentRequestTraceId(PromotionMocks.PARENT_REQUEST_TRACE_ID);
    message.setVendorId(PromotionMocks.VENDOR_ID);
    message.setOperation(POST_OPERATION);
    message.setCountry(PromotionMocks.COUNTRY);
    message.setTimestamp(PromotionMocks.TIMESTAMP);
    return message;
  }

  private String convertPromotionMultiVendorListAsString(List<PromotionMultiVendorVO> promotionMultiVendorVOList) {

    String promotions = null;
    try {
      final ObjectMapper objectMapperTest = new ObjectMapper();
      objectMapperTest.setSerializationInclusion(JsonInclude.Include.NON_NULL);
      objectMapperTest.registerModule(new JavaTimeModule());
      promotions = objectMapperTest.writeValueAsString(promotionMultiVendorVOList);
    } catch (JsonProcessingException e) {
      return null;
    }
    return promotions;
  }
}
