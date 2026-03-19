package com.abinbev.b2b.promotion.consumer.exception;

import com.abinbev.b2b.promotion.consumer.helper.MessageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ErrorHandler;

public class ListenerExceptionHandler implements ErrorHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ListenerExceptionHandler.class);

  @Autowired private MessageHelper messageHelper;

  @Override
  public void handleError(final Throwable throwable) {

    String errorMessage;
    ListenerExecutionFailedException listenerException;
    MapperException mapperException;

    if (throwable instanceof ListenerExecutionFailedException) {
      listenerException = (ListenerExecutionFailedException) throwable;
      errorMessage = getErrorMessage(listenerException);
    } else if (throwable instanceof MapperException) {
      mapperException = (MapperException) throwable;
      errorMessage = mapperException.getErrorMessage();
    } else {
      errorMessage = "Throwable not an instance of ListenerExecutionFailedException";
    }

    LOGGER.error(errorMessage, throwable);

    throw new AmqpRejectAndDontRequeueException(throwable);
  }

  private String getErrorMessage(final ListenerExecutionFailedException listenerException) {

    String errorMessage;

    try {
      errorMessage =
          String.format(
              "RequestTraceId: %s. Original queue: %s. Failed message: %s. %s",
              messageHelper.getRequestTraceIdFromMessage(listenerException),
              listenerException.getFailedMessage().getMessageProperties().getConsumerQueue(),
              new String(listenerException.getFailedMessage().getBody()),
              listenerException.getFailedMessage().getMessageProperties().toString());

    } catch (final TraceIdException e) {

      LOGGER.error("RequestTraceId not found in queue.", e);
      errorMessage =
          String.format(
              "RequestTraceId not found in queue. Original queue: %s. Failed message: %s. %s",
              listenerException.getFailedMessage().getMessageProperties().getConsumerQueue(),
              new String(listenerException.getFailedMessage().getBody()),
              listenerException.getFailedMessage().getMessageProperties().toString());

    } catch (final Exception e) {
      LOGGER.error("Not able to produce the original error message", e);
      errorMessage =
          String.format("Not able to produce the original error message. Ex: %s", e.getMessage());
    }

    return errorMessage;
  }
}
