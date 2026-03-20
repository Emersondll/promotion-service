package com.abinbev.b2b.promotion.relay.services;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.config.properties.BulkSizeProperties;
import com.abinbev.b2b.promotion.relay.constants.ApiConstants;
import com.abinbev.b2b.promotion.relay.constants.LogConstant.INFO;
import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import com.abinbev.b2b.promotion.relay.queue.senders.PromotionQueueSender;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorDeleteRequest;
import com.google.common.collect.Lists;
import com.newrelic.api.agent.Trace;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionMultiVendorService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromotionMultiVendorService.class);

  private final PromotionQueueSender promotionQueueSender;
  private final BulkSizeProperties bulkSizeProperties;

  @Autowired
  public PromotionMultiVendorService(
      final PromotionQueueSender promotionQueueSender,
      final BulkSizeProperties bulkSizeProperties) {

    this.promotionQueueSender = promotionQueueSender;
    this.bulkSizeProperties = bulkSizeProperties;
  }

  @Trace
  public void createMultiVendorPromotions(
      @Valid final List<PromotionMultiVendor> requestList,
      final BaseMessage baseMessage,
      final String vendorId) {

    List<List<PromotionMultiVendor>> bulkList =
        Lists.partition(requestList, bulkSizeProperties.getPromotionMultiVendorBulkSize());

    bulkList.forEach(
        bulk -> {
          LOGGER.info(INFO.POST_PROMOTION_MULTI_VENDOR, vendorId, bulk.size());
          buildBaseMessageAndSend(bulk, baseMessage, vendorId, ApiConstants.POST_OPERATION);
        });
  }

  @Trace
  public void deleteMultiVendorPromotions(
      final PromotionMultiVendorDeleteRequest promotionMultiVendorDeleteRequest,
      final BaseMessage baseMessage,
      final String vendorId) {

    LOGGER.info(
        INFO.DELETE_PROMOTION_MULTI_VENDOR,
        baseMessage.getVendorId(),
        promotionMultiVendorDeleteRequest.getVendorPromotionIds().size());

    buildBaseMessageAndSend(
        promotionMultiVendorDeleteRequest, baseMessage, vendorId, ApiConstants.DELETE_OPERATION);
  }

  private void buildBaseMessageAndSend(
      final Object payload,
      final BaseMessage baseMessage,
      final String vendorId,
      final String operation) {
    baseMessage.setPayload(payload);
    baseMessage.setVendorId(vendorId);
    baseMessage.setVersion(ApiConstants.API_LABEL_V3_PROMOTIONS);
    baseMessage.setOperation(operation);

    promotionQueueSender.sendMultiVendor(baseMessage);
  }
}
