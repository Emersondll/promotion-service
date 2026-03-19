package com.abinbev.b2b.promotion.consumer.listener;

import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.constant.LogConstant.INFO;
import com.abinbev.b2b.promotion.consumer.helper.JsonHelper;
import com.abinbev.b2b.promotion.consumer.listener.message.PromotionMultiVendorMessage;
import com.abinbev.b2b.promotion.consumer.service.PromotionMultiVendorService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.newrelic.api.agent.Trace;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PromotionMultiVendorListener extends GenericListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromotionMultiVendorListener.class);

  private final PromotionMultiVendorService promotionMultiVendorService;

  public PromotionMultiVendorListener(
      final PromotionMultiVendorService promotionMultiVendorService) {
    this.promotionMultiVendorService = promotionMultiVendorService;
  }

  @RabbitListener(
      queues = {"#{promotionMultiVendorProperties.getAllQueueNames()}"},
      containerFactory = "simpleContainerFactory")
  @Trace(dispatcher = true, metricName = "promotionMultiVendor")
  public void receive(@Valid @Payload final PromotionMultiVendorMessage<?> message) {

    try {
      loggingSetMDC(message);

      if (ApiConstants.POST_OPERATION.equalsIgnoreCase(message.getOperation())) {

        LOGGER.info(INFO.INIT_POST_PROMOTION_MULTI_VENDOR, message.getVendorId(), message);

        promotionMultiVendorService.save(JsonHelper.map(message.getPayload(), new TypeReference<>() {}), message);

        LOGGER.info(INFO.END_POST_PROMOTION_MULTI_VENDOR);
      } else if (ApiConstants.DELETE_OPERATION.equalsIgnoreCase(message.getOperation())) {

        LOGGER.info(INFO.INIT_DELETE_PROMOTION_MULTI_VENDOR, message.getVendorId(), message);

        promotionMultiVendorService.remove(JsonHelper.map(message.getPayload(), new TypeReference<>() {}), message);

        LOGGER.info(INFO.END_DELETE_PROMOTION_MULTI_VENDOR);
      }

    } finally {
      MDC.clear();
    }
  }
}
