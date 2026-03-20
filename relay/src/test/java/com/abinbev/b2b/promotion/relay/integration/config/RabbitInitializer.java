package com.abinbev.b2b.promotion.relay.integration.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.RabbitMQContainer;

public class RabbitInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final RabbitMQContainer RABBIT_CONTAINER =
      new RabbitMQContainer("rabbitmq:3.6-management-alpine");

  @Override
  public void initialize(@NotNull final ConfigurableApplicationContext applicationContext) {
    RABBIT_CONTAINER.withExposedPorts(5672, 15672).start();
    final TestPropertyValues properties =
        TestPropertyValues.of(
            "spring.rabbitmq.host=" + RABBIT_CONTAINER.getContainerIpAddress(),
            "spring.rabbitmq.port=" + RABBIT_CONTAINER.getMappedPort(5672));
    properties.applyTo(applicationContext);
  }
}
