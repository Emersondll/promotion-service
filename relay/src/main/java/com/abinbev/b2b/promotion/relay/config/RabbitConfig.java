package com.abinbev.b2b.promotion.relay.config;

import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.DEAD_LETTER_SUFFIX;

import com.abinbev.b2b.promotion.relay.config.properties.QueuesProperties;
import com.abinbev.b2b.promotion.relay.config.properties.ToggleProperties;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Queue settings and their respective dead letters. Standard queue: - Queue: queue-name-country -
 * Routing Key: country - Exchange: queue-name.exchange Dead Letter: - Queue:
 * name-queue-country.deadLetter - Routing Key: country - Exchange: queue-name.exchange.deadLetter
 */
@Configuration
@EnableRabbit
public class RabbitConfig {

  @Autowired private QueuesProperties queuesProperties;
  @Autowired private ToggleProperties toggleProperties;

  @Bean
  public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {

    return new Jackson2JsonMessageConverter();
  }

  private TopicExchange createExchangeByName(final String exchange) {
    return new TopicExchange(exchange, true, false);
  }

  @Bean
  public ApplicationRunner runner(
      final RabbitListenerEndpointRegistry registry, final AmqpAdmin admin) {

    return args -> {
      queuesProperties
          .getMultiVendor()
          .forEach(
              (s, queueBaseProperties) -> {
                final TopicExchange exchange =
                    createExchangeByName(queueBaseProperties.getExchange());
                final TopicExchange deadLetterExchange =
                    createExchangeByName(
                        queueBaseProperties.getExchange().concat(DEAD_LETTER_SUFFIX));
                admin.declareExchange(exchange);
                admin.declareExchange(deadLetterExchange);
                toggleProperties
                    .getSupportedCountries()
                    .forEach(
                        country ->
                            createQueueWithDeadLetter(
                                admin,
                                exchange,
                                deadLetterExchange,
                                queueBaseProperties.getName(country),
                                country.toLowerCase()));
              });

      registry.start();
    };
  }

  private void createQueueWithDeadLetter(
      final AmqpAdmin admin,
      final TopicExchange exchange,
      final TopicExchange deadLetterExchange,
      final String queueName,
      final String routingKey) {

    final Queue queue =
        QueueBuilder.durable(queueName)
            .withArgument("x-dead-letter-exchange", deadLetterExchange.getName())
            .withArgument("x-dead-letter-routing-key", routingKey)
            .build();
    admin.declareQueue(queue);
    admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routingKey));

    createDeadLetter(admin, deadLetterExchange, queueName + DEAD_LETTER_SUFFIX, routingKey);
  }

  private void createDeadLetter(
      final AmqpAdmin admin,
      final TopicExchange deadLetterExchange,
      final String deadLetterQueueName,
      final String routingKey) {
    final Queue deadLetterQueue = QueueBuilder.durable(deadLetterQueueName).build();
    admin.declareQueue(deadLetterQueue);
    admin.declareBinding(
        BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(routingKey));
  }
}
