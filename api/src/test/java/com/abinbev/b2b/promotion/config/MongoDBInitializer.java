package com.abinbev.b2b.promotion.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;

public class MongoDBInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  private static final MongoDBContainer MONGO_DB_CONTAINER = new MongoDBContainer("mongo:5.0.1");

  @Override
  public void initialize(
      @NotNull final ConfigurableApplicationContext configurableApplicationContext) {
    MONGO_DB_CONTAINER.start();
    TestPropertyValues values =
        TestPropertyValues.of("spring.data.mongodb.uri=" + MONGO_DB_CONTAINER.getReplicaSetUrl());
    values.applyTo(configurableApplicationContext);
  }
}
