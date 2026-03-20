package com.abinbev.b2b.promotion.relay.queue;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.constants.MessageGatewayConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MessageGateway {

  private final AmqpTemplate amqpTemplate;

  @Autowired
  public MessageGateway(final AmqpTemplate amqpTemplate) {

    this.amqpTemplate = amqpTemplate;
  }

  public void send(final String exchangeName, final String routingKey, final BaseMessage message) {

    Assert.notNull(message, MessageGatewayConstants.EVENT_CANT_BE_NULL);
    Assert.notNull(message.getCountry(), MessageGatewayConstants.COUNTRY_CANT_BE_NULL);
    amqpTemplate.convertAndSend(exchangeName, routingKey, message);
  }

  public void sendToDeadLetter(
      final String exchangeName, final String routingKey, final Message message) {

    amqpTemplate.send(exchangeName, routingKey, message);
  }

  public Message getMessage(final String queueName) {

    return amqpTemplate.receive(queueName);
  }
}
