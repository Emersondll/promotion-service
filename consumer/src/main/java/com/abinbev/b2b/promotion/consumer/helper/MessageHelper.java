package com.abinbev.b2b.promotion.consumer.helper;

import com.abinbev.b2b.promotion.consumer.constant.LogConstant.ERROR;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageHelper.class);

  private final Jackson2JsonMessageConverter jackson2JsonMessageConverter;

  @Autowired
  public MessageHelper(Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
    this.jackson2JsonMessageConverter = jackson2JsonMessageConverter;
  }

  public String getRequestTraceIdFromMessage(final Throwable throwable) {

    String traceId;

    if (throwable instanceof ListenerExecutionFailedException) {
      final ListenerExecutionFailedException listenerException =
          (ListenerExecutionFailedException) throwable;
      try {
        @SuppressWarnings("rawtypes")
        final BaseMessage message =
            (BaseMessage)
                jackson2JsonMessageConverter.fromMessage(listenerException.getFailedMessage());
        traceId = message.getRequestTraceId();
      } catch (final Exception e) {
        LOGGER.warn(ERROR.GET_REQUEST_TRACE_ID_FAILED, e);
        traceId = "NOT SET";
      }
    } else {
      traceId = "NOT A ListenerExecutionFailedException";
    }

    return traceId;
  }

  public String getCountryCodeOfFailedMessage(final Message message) {

    return message.getMessageProperties().getReceivedRoutingKey();
  }
}
