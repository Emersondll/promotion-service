package com.abinbev.b2b.promotion.relay.queue;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.relay.queue.senders.PromotionQueueSender;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.amqp.core.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MessageGatewayTest {

  private static final String BODY = "ok";
  @Mock AmqpTemplate amqpTemplate;
  @InjectMocks MessageGateway messageGateway;
  @InjectMocks PromotionQueueSender queueSender;

  @Test
  public void sendThenOk() {

    final BaseMessage message = PromotionMocks.getPostMessageMock();
    messageGateway.send(
        PromotionMocks.ACCOUNT_GROUP_EXCHANGE, PromotionMocks.ACCOUNT_GROUP_ROUTING_KEY, message);

    Mockito.verify(amqpTemplate)
        .convertAndSend(
            PromotionMocks.ACCOUNT_GROUP_EXCHANGE,
            PromotionMocks.ACCOUNT_GROUP_ROUTING_KEY,
            message);
  }

  @Test
  public void getMessageFromPromotion() throws UnsupportedEncodingException {

    Mockito.when(amqpTemplate.receive(ArgumentMatchers.anyString()))
        .thenReturn(new Message(BODY.getBytes(), new MessageProperties()));
    final Message message = messageGateway.getMessage(PromotionMocks.PROMOTION_QUEUE_NAME);
    Assertions.assertNotNull(message);
  }

  @Test
  public void getMessageFromPromotionThenReturnNull() throws UnsupportedEncodingException {

    Mockito.when(amqpTemplate.receive(ArgumentMatchers.anyString())).thenReturn(null);
    final Message message = messageGateway.getMessage(PromotionMocks.PROMOTION_QUEUE_NAME);
    Assertions.assertNull(message);
  }

  @Test
  public void sendDeadLetter() {

    final Message message = new Message("message".getBytes(), new MessageProperties());
    messageGateway.sendToDeadLetter("exchangeName", "routingKey", message);
    Mockito.verify(amqpTemplate)
        .send(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyString(),
            ArgumentMatchers.any(Message.class));
  }
}
