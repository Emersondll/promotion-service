package com.abinbev.b2b.promotion.relay.services;

import static java.util.Optional.ofNullable;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.config.properties.BulkSizeProperties;
import com.abinbev.b2b.promotion.relay.config.properties.SingleToMultiVendorConversionProperties;
import com.abinbev.b2b.promotion.relay.constants.ApiConstants;
import com.abinbev.b2b.promotion.relay.constants.LogConstant.INFO;
import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import com.abinbev.b2b.promotion.relay.mapper.PromotionMapper;
import com.abinbev.b2b.promotion.relay.queue.senders.PromotionQueueSender;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorDeleteRequest;
import com.google.common.collect.Lists;
import com.newrelic.api.agent.Trace;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionSingleVendorService {

  private static final Logger LOGGER = LoggerFactory.getLogger(PromotionSingleVendorService.class);

  private final PromotionQueueSender promotionQueueSender;
  private final BulkSizeProperties bulkSizeProperties;
  private final SingleToMultiVendorConversionProperties conversionProperties;
  private final PromotionMapper mapper;

  @Autowired
  public PromotionSingleVendorService(
      final PromotionQueueSender promotionQueueSender,
      final BulkSizeProperties bulkSizeProperties,
      final SingleToMultiVendorConversionProperties conversionProperties,
      final PromotionMapper mapper) {

    this.promotionQueueSender = promotionQueueSender;
    this.bulkSizeProperties = bulkSizeProperties;
    this.conversionProperties = conversionProperties;
    this.mapper = mapper;
  }

  @Trace
  public void createPromotions(
      @Valid final List<PromotionSingleVendor> requestList, final BaseMessage baseMessage) {

    List<List<PromotionSingleVendor>> bulkList =
        Lists.partition(requestList, bulkSizeProperties.getPromotionBulkSize());

    bulkList.forEach(bulk -> createPromotion(bulk, baseMessage));
  }

  @Trace
  public void deletePromotion(
      final PromotionSingleVendorDeleteRequest promotionSingleVendorDeleteRequest,
      final BaseMessage baseMessage) {

    baseMessage.setVersion(ApiConstants.API_LABEL_V2);
    baseMessage.setOperation(ApiConstants.DELETE_OPERATION);

    baseMessage.setVendorId(conversionProperties.getVendorIdByCountry(baseMessage.getCountry()));

    baseMessage.setPayload(
        new PromotionMultiVendorDeleteRequest(
            setToList(promotionSingleVendorDeleteRequest.getPromotions())));

    LOGGER.info(
        INFO.DELETE_PROMOTION_MULTI_VENDOR,
        baseMessage.getVendorId(),
        promotionSingleVendorDeleteRequest.getPromotions().size());

    promotionQueueSender.sendMultiVendor(baseMessage);
  }

  private ArrayList<String> setToList(final Set<String> set) {
    return Lists.newArrayList(ofNullable(set).orElse(Set.of()));
  }

  private void createPromotion(
      final List<PromotionSingleVendor> bulk, final BaseMessage baseMessage) {
    baseMessage.setVersion(ApiConstants.API_LABEL_V2);
    baseMessage.setOperation(ApiConstants.POST_OPERATION);
    baseMessage.setPayload(
        bulk.stream().map(mapper::singleToMultiVendor).collect(Collectors.toList()));
    baseMessage.setVendorId(conversionProperties.getVendorIdByCountry(baseMessage.getCountry()));
    LOGGER.info(INFO.POST_PROMOTION_MULTI_VENDOR, baseMessage.getVendorId(), bulk.size());
    promotionQueueSender.sendMultiVendor(baseMessage);
  }
}
