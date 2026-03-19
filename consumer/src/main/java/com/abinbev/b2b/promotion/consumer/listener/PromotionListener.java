package com.abinbev.b2b.promotion.consumer.listener;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.API_PROMOTION_VERSION_V2;

import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.constant.LogConstant.INFO;
import com.abinbev.b2b.promotion.consumer.helper.PromotionHelper;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import com.abinbev.b2b.promotion.consumer.service.PromotionService;
import com.newrelic.api.agent.Trace;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PromotionListener extends GenericListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromotionListener.class);

  private final PromotionService promotionService;

  @Autowired
  public PromotionListener(PromotionService promotionService) {
    this.promotionService = promotionService;
  }

  @SuppressWarnings("rawtypes")
  @RabbitListener(
      queues = {"#{promotionProperties.getAllQueueNames()}"},
      containerFactory = "simpleContainerFactory")
  @Trace(dispatcher = true, metricName = "promotionListener")
  public void receive(@Valid @Payload final BaseMessage message) {

    try {
      loggingSetMDC(message);

      if (message.getOperation().equals(ApiConstants.DELETE_OPERATION)
          && message.getVersion() != null
          && message.getVersion().equals(API_PROMOTION_VERSION_V2)) {

        LOGGER.info(INFO.INIT_DELETE_PROMOTION_SINGLE_VENDOR, message);

        promotionService.deletePromotion(message);

        LOGGER.info(INFO.END_DELETE_PROMOTION_SINGLE_VENDOR);
      } else if (message.getVersion() != null
          && message.getVersion().equals(API_PROMOTION_VERSION_V2)) {

        LOGGER.info(INFO.INIT_POST_PROMOTION_SINGLE_VENDOR, message);

        promotionService.createPromotions(
            PromotionHelper.mapBaseMessageToPromotionListVo(message),
            message.getCountry(),
            message.getTimestamp());

        LOGGER.info(INFO.END_POST_PROMOTION_SINGLE_VENDOR);
      } else {
        LOGGER.info(INFO.DEPRECATED_PROMOTION_VERSION, message);
      }

    } finally {
      MDC.clear();
    }
  }
}
