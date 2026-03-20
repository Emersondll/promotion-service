package com.abinbev.b2b.promotion.consumer.service;

import com.abinbev.b2b.data.ingestion.feedback.layer.service.FeedbackLayerService;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMultiVendorHelper;
import com.abinbev.b2b.promotion.consumer.listener.message.PromotionMultiVendorMessage;
import com.abinbev.b2b.promotion.consumer.listener.properties.FeedbackLayerProperties;
import com.abinbev.b2b.promotion.consumer.repository.PromotionRepository;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountMultiVendorDeleteVO;
import com.abinbev.b2b.promotion.consumer.vo.PromotionMultiVendorVO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.newrelic.api.agent.Trace;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import static java.util.Objects.nonNull;

@Component
public class PromotionMultiVendorService {

  private static final String ERROR_CONSUMING_MESSAGE_PATTERN = "[PromotionMultiVendorService] Error consuming message. RequestTraceId: %s";
  private static final String ERROR_DELETE_MESSAGE_PATTERN = "[PromotionMultiVendorService] Error deleting message. RequestTraceId: %s";
  private static final String ERROR_TO_PARSE_JSON = "[PromotionMultiVendorService] Error transforming json into string.";

  private static final Logger LOGGER = LoggerFactory.getLogger(PromotionMultiVendorService.class);
  private final FeedbackLayerService feedbackLayerService;
  private final FeedbackLayerProperties feedbackLayerProperties;
  private final ObjectMapper objectMapper;
  private final PromotionRepository promotionRepository;

  public PromotionMultiVendorService(final PromotionRepository promotionRepository,
                                     @Autowired(required = false)
                                     FeedbackLayerService feedbackLayerService,
                                     FeedbackLayerProperties feedbackLayerProperties,
                                     ObjectMapper objectMapper) {
    this.promotionRepository = promotionRepository;
    this.feedbackLayerService = feedbackLayerService;
    this.feedbackLayerProperties = feedbackLayerProperties;

    this.objectMapper = objectMapper;
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Trace
  public void save(final List<PromotionMultiVendorVO> promotions, PromotionMultiVendorMessage<?> message) {

    final boolean isFeedbackLayerIntegrationActiveForCountry = feedbackLayerProperties.isFeedbackLayerIntegrationActiveForCountry(
            message.getCountry());
    final boolean hasParentRequestTraceId = nonNull(message.getParentRequestTraceId());

    if (isFeedbackLayerIntegrationActiveForCountry && hasParentRequestTraceId) {
      processMessageWithFeedbackLayerIntegration(promotions, message);
    } else {
      processMessageWithoutFeedbackLayerIntegration(promotions, message);
    }
  }

  private void processMessageWithFeedbackLayerIntegration(List<PromotionMultiVendorVO> promotions,
                                                          PromotionMultiVendorMessage<?> message) {
    if (CollectionUtils.isEmpty(promotions)) {
      return;
    }

    try {
      LOGGER.info("[PromotionMultiVendorService] Starting consumer Message on Feedback Layer. RequestTraceId: {}", message.getRequestTraceId());

      feedbackLayerService.createTrackingRecord(message.getParentRequestTraceId(), message.getRequestTraceId(),
              message.getOperation(), message.getVersion(), "PROMOTION", message.getCountry(),
              message.getVendorId(), "PROMOTION");
      LOGGER.info("[PromotionMultiVendorService] Tracking created on feedback layer. RequestTraceId: {}", message.getRequestTraceId());

      promotionRepository.insertMultiVendorBulk(
              PromotionMultiVendorHelper.mapToPromotionMultiVendorModel(promotions, message.getVendorId()),
              message.getCountry(),
              message.getTimestamp());

      feedbackLayerService.setSuccess(message.getRequestTraceId());

      LOGGER.info("[PromotionMultiVendorService] Consumed message. RequestTraceId: {}", message.getRequestTraceId());
    } catch (final DuplicateKeyException | BulkOperationException exception) {
      LOGGER.info(String.format(ERROR_CONSUMING_MESSAGE_PATTERN, message.getRequestTraceId()), exception);

      feedbackLayerService.setFailure(message.getRequestTraceId());
      feedbackLayerService.setErrorMessage(message.getRequestTraceId(), exception.getMessage());
      feedbackLayerService.setPayload(message.getRequestTraceId(), getMessagePayload(promotions));
      throw exception;
    } finally {
      LOGGER.info("[PromotionMultiVendorService] Starting the publish message to feedback Layer. RequestTraceId: {}", message.getRequestTraceId());
      feedbackLayerService.publish(message.getRequestTraceId());
      LOGGER.info("[PromotionMultiVendorService] Published message to feedback Layer. RequestTraceId: {}", message.getRequestTraceId());
    }
  }

  private void processMessageWithoutFeedbackLayerIntegration(List<PromotionMultiVendorVO> promotions, PromotionMultiVendorMessage<?> message) {

    if (CollectionUtils.isEmpty(promotions)) {
      return;
    }
    try {
      promotionRepository.insertMultiVendorBulk(
              PromotionMultiVendorHelper.mapToPromotionMultiVendorModel(promotions, message.getVendorId()),
              message.getCountry(),
              message.getTimestamp());
    } catch (DuplicateKeyException | BulkOperationException exception) {
      LOGGER.error(String.format(ERROR_CONSUMING_MESSAGE_PATTERN, message.getRequestTraceId()), exception);
      throw exception;
    }
  }

  @Trace
  public void remove(
          final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO,
          PromotionMultiVendorMessage<?> message) {

    final boolean isFeedbackLayerIntegrationActiveForCountry = feedbackLayerProperties.isFeedbackLayerIntegrationActiveForCountry(
            message.getCountry());
    final boolean hasParentRequestTraceId = nonNull(message.getParentRequestTraceId());

    if (isFeedbackLayerIntegrationActiveForCountry && hasParentRequestTraceId) {
      removeMessageWithFeedbackLayerIntegration(promotionAccountMultiVendorDeleteVO, message);
    } else {
      removeMessageWithoutFeedbackLayerIntegration(promotionAccountMultiVendorDeleteVO, message);
    }
  }

  private void removeMessageWithFeedbackLayerIntegration(
          PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO,
          PromotionMultiVendorMessage<?> message) {
    try {
      feedbackLayerService.createTrackingRecord(message.getParentRequestTraceId(), message.getRequestTraceId(),
              message.getOperation(), message.getVersion(), "PROMOTIONS", message.getCountry(),
              message.getVendorId(), "PROMOTIONS");

      if (CollectionUtils.isEmpty(promotionAccountMultiVendorDeleteVO.getVendorAccountIds())) {
        promotionRepository.softDeleteMultiVendor(
                promotionAccountMultiVendorDeleteVO, message.getVendorId(), message.getTimestamp(), message.getCountry());
        feedbackLayerService.setSuccess(message.getRequestTraceId());
      }
    } catch (Exception exception) {
      LOGGER.error(String.format(ERROR_DELETE_MESSAGE_PATTERN, message.getRequestTraceId()), exception);

      feedbackLayerService.setFailure(message.getRequestTraceId());
      feedbackLayerService.setErrorMessage(message.getRequestTraceId(), exception.getMessage());
      throw exception;
    } finally {
      feedbackLayerService.publish(message.getRequestTraceId());
    }
  }

  private void removeMessageWithoutFeedbackLayerIntegration(
          PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO,
          PromotionMultiVendorMessage<?> message) {

      if (CollectionUtils.isEmpty(promotionAccountMultiVendorDeleteVO.getVendorAccountIds())) {
        promotionRepository.softDeleteMultiVendor(
                promotionAccountMultiVendorDeleteVO, message.getVendorId(), message.getTimestamp(), message.getCountry());
      }
  }

  private String getMessagePayload(final List<PromotionMultiVendorVO> promotions) {
    try {
      return objectMapper.writeValueAsString(promotions);
    } catch (final JsonProcessingException exception) {
      LOGGER.error(ERROR_TO_PARSE_JSON, exception);
      return null;
    }
  }
}
