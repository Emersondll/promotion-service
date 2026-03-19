package com.abinbev.b2b.promotion.consumer.config;

import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.validation.SmartValidator;

@Configuration
public class CustomRabbitListenerConfig implements RabbitListenerConfigurer {

  private SmartValidator validator;

  @Autowired
  public CustomRabbitListenerConfig(final SmartValidator validator) {

    this.validator = validator;
  }

  @Bean
  public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {

    final DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
    factory.setValidator(validator);
    return factory;
  }

  @Override
  public void configureRabbitListeners(
      final RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
  }
}
