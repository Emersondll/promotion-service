package com.abinbev.b2b.promotion.relay.queue.senders;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.config.properties.PromotionMultiVendorProperties;
import com.abinbev.b2b.promotion.relay.queue.MessageGateway;
import com.newrelic.api.agent.NewRelic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionQueueSender {

  private final PromotionMultiVendorProperties promotionMultiVendorProperties;
  private final MessageGateway messageGateway;

  @Autowired
  public PromotionQueueSender(
      final MessageGateway messageGateway,
      final PromotionMultiVendorProperties promotionMultiVendorProperties) {

    this.messageGateway = messageGateway;
    this.promotionMultiVendorProperties = promotionMultiVendorProperties;
  }

  public void sendMultiVendor(final BaseMessage message) {

    NewRelic.addCustomParameter("sendingToMultiVendorQueue", true);
    NewRelic.addCustomParameter(
        "MultiVendorExchange", promotionMultiVendorProperties.getExchange());
    NewRelic.addCustomParameter("Country", message.getCountry());
    NewRelic.addCustomParameter("Operation", message.getOperation());

    messageGateway.send(
        promotionMultiVendorProperties.getExchange(), message.getCountry().toLowerCase(), message);
  }
}
