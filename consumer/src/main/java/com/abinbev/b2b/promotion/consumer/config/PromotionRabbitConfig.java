package com.abinbev.b2b.promotion.consumer.config;

import static java.lang.String.format;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;

import com.abinbev.b2b.promotion.consumer.config.properties.BaseQueueProperties;
import com.abinbev.b2b.promotion.consumer.config.properties.BeesSyncIntegrationDelete;
import com.abinbev.b2b.promotion.consumer.config.properties.BeesSyncIntegrationPost;
import com.abinbev.b2b.promotion.consumer.config.properties.BeesSyncIntegrationProperties;
import com.abinbev.b2b.promotion.consumer.config.properties.PromotionConnectionProperties;
import com.abinbev.b2b.promotion.consumer.config.properties.PromotionMultiVendorProperties;
import com.abinbev.b2b.promotion.consumer.config.properties.PromotionProperties;
import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.exception.ConsumerMessageRecoverer;
import com.abinbev.b2b.promotion.consumer.exception.ListenerExceptionHandler;
import com.abinbev.b2b.promotion.consumer.exception.MapperException;

@Configuration
@EnableRabbit
public class PromotionRabbitConfig {

  private final PromotionProperties promotionProperties;
  private final PromotionConnectionProperties promotionConnectionProperties;
  private final PromotionMultiVendorProperties promotionMultiVendorProperties;
  private final BeesSyncIntegrationProperties beesSyncIntegrationProperties;
  private final BeesSyncIntegrationPost beesSyncIntegrationPost;
  private final BeesSyncIntegrationDelete beesSyncIntegrationDelete;

  private static final String AMQPS_PROTOCOL = "amqps";
  private static final String AMQP_PROTOCOL = "amqp";
  private static final String RABBIT_URI_PATTERN = "%s://%s:%s@%s:%d/%s";

  @Autowired
  public PromotionRabbitConfig(
      final PromotionProperties promotionProperties,
      final PromotionConnectionProperties promotionConnectionProperties,
      final PromotionMultiVendorProperties promotionMultiVendorProperties,
      final BeesSyncIntegrationProperties beesSyncIntegrationProperties, BeesSyncIntegrationPost beesSyncIntegrationPost,
		  BeesSyncIntegrationDelete beesSyncIntegrationDelete) {

    this.promotionProperties = promotionProperties;
    this.promotionConnectionProperties = promotionConnectionProperties;
    this.promotionMultiVendorProperties = promotionMultiVendorProperties;
	this.beesSyncIntegrationProperties = beesSyncIntegrationProperties;
	this.beesSyncIntegrationPost = beesSyncIntegrationPost;
	this.beesSyncIntegrationDelete = beesSyncIntegrationDelete;
  }

  @Bean
  public TopicExchange promotionExchange() {

    return new TopicExchange(promotionProperties.getExchange(), true, false);
  }

  @Bean
  public TopicExchange promotionDeadLetterExchange() {

    return new TopicExchange(
        promotionProperties.getExchange() + ApiConstants.DEAD_LETTER_SUFFIX, true, false);
  }

  @Bean
  public TopicExchange promotionMultiVendorExchange() {

    return new TopicExchange(promotionMultiVendorProperties.getExchange(), true, false);
  }

  @Bean
  public TopicExchange promotionMultiVendorDeadLetterExchange() {

    return new TopicExchange(
        promotionMultiVendorProperties.getExchange() + ApiConstants.DEAD_LETTER_SUFFIX,
        true,
        false);
  }

  @Bean
  public TopicExchange beesSyncExchange() {

    return new TopicExchange(beesSyncIntegrationProperties.getExchange(), true, false);
  }

  @Bean
  public TopicExchange beesSyncIntegrationDeadLetterExchange() {

    return new TopicExchange(
            beesSyncIntegrationProperties.getExchange() + ApiConstants.DEAD_LETTER_SUFFIX,
            true,
            false);
  }

  @Bean
  public ApplicationRunner runner(
      final RabbitListenerEndpointRegistry registry, final AmqpAdmin admin) {

    return args -> registry.start();
  }

  @Bean
  public Declarables declarablePromotionBean(final AmqpAdmin admin) {
    final List<Declarable> declarables = new ArrayList<>();

    for (final BaseQueueProperties.QueueProperties queueProperties :
        promotionProperties.getQueues().values()) {
      String deadLetterQueueName = queueProperties.getQueueName() + ApiConstants.DEAD_LETTER_SUFFIX;
      declarables.addAll(
          createQueueWithDeadLetter(
              admin,
              promotionExchange(),
              promotionDeadLetterExchange(),
              queueProperties.getQueueName(),
              queueProperties.getRoutingKey(),
              deadLetterQueueName));
    }

    for (final BaseQueueProperties.QueueProperties queueProperties :
        promotionMultiVendorProperties.getQueues().values()) {
      String deadLetterQueueName = queueProperties.getQueueName() + ApiConstants.DEAD_LETTER_SUFFIX;
      declarables.addAll(
          createQueueWithDeadLetter(
              admin,
              promotionMultiVendorExchange(),
              promotionMultiVendorDeadLetterExchange(),
              queueProperties.getQueueName(),
              queueProperties.getRoutingKey(),
              deadLetterQueueName));
    }

    for(final BaseQueueProperties.QueueProperties queueProperties :
            beesSyncIntegrationPost.getQueues().values()) {
      String deadLetterQueueName = queueProperties.getQueueName() + ApiConstants.DLQ_SUFFIX;
      declarables.addAll(
    			createQueueWithDeadLetter(
    					admin,
    					beesSyncExchange(),
    					beesSyncIntegrationDeadLetterExchange(),
                        queueProperties.getQueueName(),
    					queueProperties.getRoutingKey(),
                        deadLetterQueueName));
    }

    for(final BaseQueueProperties.QueueProperties queueProperties :
            beesSyncIntegrationDelete.getQueues().values()) {
      String deadLetterQueueName = queueProperties.getQueueName() + ApiConstants.DLQ_SUFFIX;
      declarables.addAll(
              createQueueWithDeadLetter(
                      admin,
                      beesSyncExchange(),
                      beesSyncIntegrationDeadLetterExchange(),
                      queueProperties.getQueueName(),
                      queueProperties.getRoutingKey(),
                      deadLetterQueueName));
    }

    return new Declarables(declarables);
  }

  private List<Declarable> createQueueWithDeadLetter(
      final AmqpAdmin admin,
      final TopicExchange exchange,
      final TopicExchange deadLetterExchange,
      final String queueName,
      final String routingKey,
      final String deadLetterQueueName) {

    final List<Declarable> declarables = new ArrayList<>();
    final Queue queue =
        QueueBuilder.durable(queueName)
            .withArgument("x-dead-letter-exchange", deadLetterExchange.getName())
            .withArgument("x-dead-letter-routing-key", routingKey)
            .build();
    final Binding queueBinding = BindingBuilder.bind(queue).to(exchange).with(routingKey);

    final Queue deadLetterQueue =
        QueueBuilder.durable(deadLetterQueueName).build();
    final Binding deadLetterBinding =
        BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(routingKey);

    admin.declareQueue(queue);
    admin.declareBinding(queueBinding);
    admin.declareQueue(deadLetterQueue);
    admin.declareBinding(deadLetterBinding);

    declarables.add(queue);
    declarables.add(queueBinding);
    declarables.add(deadLetterQueue);
    declarables.add(deadLetterBinding);

    return declarables;
  }

  @Bean
  AmqpAdmin admin(
      @Qualifier("promotionConnectionFactory") final ConnectionFactory connectionFactory) {

    return new RabbitAdmin(connectionFactory);
  }

  @Bean
  @Primary
  public ConnectionFactory promotionConnectionFactory() throws URISyntaxException {

    final boolean isSslEnabled =
        promotionConnectionProperties.getSsl() != null
            && Boolean.TRUE.equals(promotionConnectionProperties.getSsl().getEnabled());
    final String protocol = isSslEnabled ? AMQPS_PROTOCOL : AMQP_PROTOCOL;
    return new CachingConnectionFactory(
        new URI(
            format(
                RABBIT_URI_PATTERN,
                protocol,
                promotionConnectionProperties.getUsername(),
                promotionConnectionProperties.getPassword(),
                promotionConnectionProperties.getHost(),
                promotionConnectionProperties.getPort(),
                promotionConnectionProperties.getVirtualHost())));
  }

  @Bean
  public SimpleRabbitListenerContainerFactory simpleContainerFactory(
      @Qualifier("promotionConnectionFactory") final ConnectionFactory connectionFactory,
      final RabbitProperties rabbitProperties,
      final AmqpTemplate amqpTemplate) {

    return createContainerFactory(connectionFactory, rabbitProperties, amqpTemplate);
  }

  public SimpleRabbitListenerContainerFactory createContainerFactory(
      final ConnectionFactory connectionFactory,
      final RabbitProperties rabbitProperties,
      final AmqpTemplate amqpTemplate) {

    final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory);
    factory.setConcurrentConsumers(
        promotionConnectionProperties.getListener().getSimple().getConcurrency());
    factory.setMaxConcurrentConsumers(
        promotionConnectionProperties.getListener().getSimple().getMaxConcurrency());
    factory.setDefaultRequeueRejected(
        promotionConnectionProperties.getListener().getSimple().isDefaultRequeueRejected());
    factory.setAutoStartup(promotionConnectionProperties.getListener().getSimple().isAutoStartup());
    factory.setPrefetchCount(promotionConnectionProperties.getListener().getSimple().getPrefetch());

    factory.setErrorHandler(listenerExceptionHandler());
    factory.setMessageConverter(jackson2JsonMessageConverter());
    factory.setAdviceChain(rabbitSourceRetryInterceptor(rabbitProperties, amqpTemplate));

    return factory;
  }

  @Bean
  public RetryOperationsInterceptor rabbitSourceRetryInterceptor(
      final RabbitProperties rabbitProperties, final AmqpTemplate amqpTemplate) {

    final RabbitProperties.SimpleContainer simpleContainer =
        rabbitProperties.getListener().getSimple();
    return RetryInterceptorBuilder.stateless()
        .recoverer(consumerMessageRecoverer(amqpTemplate))
        .retryPolicy(simpleRetryPolicy(rabbitProperties))
        .backOffOptions(
            simpleContainer.getRetry().getInitialInterval().toMillis(),
            simpleContainer.getRetry().getMultiplier(),
            simpleContainer.getRetry().getMaxInterval().toMillis())
        .build();
  }

  @Bean
  public SimpleRetryPolicy simpleRetryPolicy(final RabbitProperties rabbitProperties) {

    final RabbitProperties.SimpleContainer simpleContainer =
        rabbitProperties.getListener().getSimple();
    return new SimpleRetryPolicy(
        simpleContainer.getRetry().getMaxAttempts(), getUnretryableExceptions(), true, true);
  }

  private Map<Class<? extends Throwable>, Boolean> getUnretryableExceptions() {

    final Map<Class<? extends Throwable>, Boolean> unretryableExceptions = new HashMap<>();

    unretryableExceptions.put(MethodArgumentNotValidException.class, false);
    unretryableExceptions.put(MessageConversionException.class, false);
    unretryableExceptions.put(NullPointerException.class, false);
    unretryableExceptions.put(MapperException.class, false);

    return unretryableExceptions;
  }

  @Bean
  public ConsumerMessageRecoverer consumerMessageRecoverer(final AmqpTemplate amqpTemplate) {

    return new ConsumerMessageRecoverer(amqpTemplate);
  }

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {

    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public ListenerExceptionHandler listenerExceptionHandler() {

    return new ListenerExceptionHandler();
  }
}
