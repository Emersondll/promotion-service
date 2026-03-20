package com.abinbev.b2b.promotion.relay.integration.config;

import com.abinbev.b2b.promotion.relay.config.properties.PromotionMultiVendorProperties;
import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseIntegrationTest {

  @Autowired private RabbitAdmin rabbitAdmin;
  @Autowired private RabbitTemplate rabbitTemplate;
  @Autowired private PromotionMultiVendorProperties promotionMultiVendorProperties;

  protected List<Message> readMessages(final String queue) {
    final List<Message> messages = new ArrayList<>();
    Message message = rabbitTemplate.receive(queue);
    while (message != null) {
      messages.add(message);
      message = rabbitTemplate.receive(queue);
    }
    return messages;
  }

  protected void purge(final String queue) {
    rabbitAdmin.purgeQueue(queue);
  }

  protected String getPromotionMultiVendorQueue(final String country) {
    return promotionMultiVendorProperties.getName(country);
  }
}
