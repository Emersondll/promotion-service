package com.abinbev.b2b.promotion.consumer.exception;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.COUNTRY_HEADER;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.HEADER_RETRIES;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.REQUEST_TRACE_ID_HEADER;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.X_EXCEPTION_CODE;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.X_EXCEPTION_ROOT_CAUSE;
import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_EXCEPTION_MESSAGE;
import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_EXCEPTION_STACKTRACE;
import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_ORIGINAL_EXCHANGE;
import static org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer.X_ORIGINAL_ROUTING_KEY;

import com.abinbev.b2b.promotion.consumer.config.properties.PromotionConnectionProperties;
import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.constant.LogConstant.ERROR;
import com.abinbev.b2b.promotion.consumer.constant.LogConstant.WARN;
import com.abinbev.b2b.promotion.consumer.helper.MessageHelper;
import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoCommandException;
import com.mongodb.MongoSocketReadException;
import com.mongodb.MongoWriteException;
import com.newrelic.api.agent.NewRelic;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.UncategorizedMongoDbException;

public class ConsumerMessageRecoverer implements MessageRecoverer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerMessageRecoverer.class);
  private static final String NEW_RELIC_RECORD_METRIC_KEY_MESSAGE_DLQ = "Custom/MessageSentDlq";
  private static final String NEW_RELIC_CUSTOM_PARAMETER_KEY_MESSAGE_DLQ = "messageSentDlq";
  private static final String NEW_RELIC_CUSTOM_PARAMETER_KEY_REQUEST_TRACE_ID = "requestTraceId";
  private static final String NEW_RELIC_CUSTOM_PARAMETER_COUNTRY_KEY = "country";

  private final AmqpTemplate amqpTemplate;

  @Autowired private ThrowableIssueFactory throwableIssue;
  @Autowired private PromotionConnectionProperties rabbitConnectionProperties;
  @Autowired private MessageHelper messageHelper;

  public ConsumerMessageRecoverer(final AmqpTemplate amqpTemplate) {

    this.amqpTemplate = amqpTemplate;
  }

  private final Collection<Class<? extends Throwable>> alwaysRetryableExceptions =
      Arrays.asList(
          OptimisticLockingFailureException.class,
          MongoCommandException.class,
          MongoWriteException.class,
          UncategorizedMongoDbException.class,
          MongoSocketReadException.class,
          MongoBulkWriteException.class);

  @Override
  public void recover(final Message message, final Throwable throwable) {

    LOGGER.error(ERROR.PROCESS_MESSAGE_FAIL, throwable.getMessage(), throwable);
    final Issue issue = throwableIssue.createIssueBasedOnThrowable(throwable);
    addHeader(message, throwable, issue);
    final String routingKey = message.getMessageProperties().getReceivedRoutingKey();
    boolean needToRequeue = false;
    final int messageRetries =
        (int) message.getMessageProperties().getHeaders().get(HEADER_RETRIES);
    String exchangeName = message.getMessageProperties().getReceivedExchange();
    String exchangeNameDlq = exchangeName.concat(ApiConstants.DEAD_LETTER_SUFFIX);
    final ErrorMessage errorMessage = getErrorMessage(throwable);
    final String requestTraceId = errorMessage.getRequestTraceId();
    for (final Class<? extends Throwable> t : alwaysRetryableExceptions) {
      needToRequeue |= isCausedBy(throwable, t);
    }
    MDC.put(REQUEST_TRACE_ID_HEADER, requestTraceId);
    MDC.put(COUNTRY_HEADER, routingKey.toUpperCase());
    if (needToRequeue
        && (messageRetries
            < rabbitConnectionProperties.getListener().getSimple().getRetry().getMaxAttempts())) {
      LOGGER.warn(WARN.RETRYING_MESSAGE, issue.getCode(), exchangeName);
      amqpTemplate.send(exchangeName, routingKey, message);
    } else {
      LOGGER.warn(WARN.RETRYING_MESSAGE_EXHAUSTED, issue.getCode(), exchangeNameDlq);
      LOGGER.error(ERROR.PROCESS_MESSAGE_FAIL, errorMessage.getMessage(), throwable);
      amqpTemplate.send(exchangeNameDlq, routingKey, message);
    }
    sendInformationNewRelic(requestTraceId, message);
  }

  private boolean isCausedBy(
      final Throwable caught, final Class<? extends Throwable> isOfOrCausedBy) {

    if (caught == null) {
      return false;
    } else if (isOfOrCausedBy.isAssignableFrom(caught.getClass())) {
      return true;
    }

    return isCausedBy(caught.getCause(), isOfOrCausedBy);
  }

  private ErrorMessage getErrorMessage(final Throwable throwable) {

    final ListenerExecutionFailedException listenerException =
        (ListenerExecutionFailedException) throwable;
    final Message failedMessage = listenerException.getFailedMessage();
    final MessageProperties messageProperties = failedMessage.getMessageProperties();
    final String requestTraceId = messageHelper.getRequestTraceIdFromMessage(throwable);
    final StringBuilder stringBuilder = new StringBuilder();

    final String message =
        stringBuilder
            .append("RequestTraceId: ")
            .append(requestTraceId)
            .append(". Original queue: ")
            .append(messageProperties.getConsumerQueue())
            .append(". Failed message: ")
            .append(new String(failedMessage.getBody()))
            .append(". ")
            .append(messageProperties.toString())
            .toString();

    return new ErrorMessage(requestTraceId, message);
  }

  private void sendInformationNewRelic(final String requestTraceId, final Message message) {

    final String countryCode = messageHelper.getCountryCodeOfFailedMessage(message);

    NewRelic.recordMetric(NEW_RELIC_RECORD_METRIC_KEY_MESSAGE_DLQ, 1);
    NewRelic.addCustomParameter(NEW_RELIC_CUSTOM_PARAMETER_KEY_MESSAGE_DLQ, 1);
    NewRelic.addCustomParameter(NEW_RELIC_CUSTOM_PARAMETER_KEY_REQUEST_TRACE_ID, requestTraceId);
    NewRelic.addCustomParameter(NEW_RELIC_CUSTOM_PARAMETER_COUNTRY_KEY, countryCode);
  }

  private void addHeader(final Message message, final Throwable throwable, final Issue issue) {
    final Map<String, Object> headers = message.getMessageProperties().getHeaders();

    headers.put(X_EXCEPTION_CODE, issue.getCode());
    headers.put(X_EXCEPTION_MESSAGE, issue.getMessage());
    headers.put(X_EXCEPTION_ROOT_CAUSE, ExceptionUtils.getRootCauseMessage(throwable));
    headers.put(X_EXCEPTION_STACKTRACE, ExceptionUtils.getStackTrace(throwable));
    headers.put(X_ORIGINAL_EXCHANGE, message.getMessageProperties().getReceivedExchange());
    headers.put(X_ORIGINAL_ROUTING_KEY, message.getMessageProperties().getReceivedRoutingKey());

    int retries = 0;
    if (headers.containsKey(HEADER_RETRIES)) {
      retries = (int) headers.get(HEADER_RETRIES);
      retries++;
    }

    headers.put(HEADER_RETRIES, retries);
  }

  private static class ErrorMessage {

    private final String requestTraceId;
    private final String message;

    private ErrorMessage(final String requestTraceId, final String message) {

      this.requestTraceId = requestTraceId;
      this.message = message;
    }

    private String getRequestTraceId() {

      return requestTraceId;
    }

    private String getMessage() {

      return message;
    }
  }
}
