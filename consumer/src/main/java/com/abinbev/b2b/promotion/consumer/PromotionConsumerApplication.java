package com.abinbev.b2b.promotion.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@ConfigurationPropertiesScan
// TODO This is only a workaround. Remove this component Scan after feedback layer java version update
@ComponentScan(basePackages = { "com.abinbev.b2b.data.ingestion.feedback.layer.service", "com.abinbev.b2b.*" })
@SpringBootApplication
public class PromotionConsumerApplication {

  public static void main(final String[] args) {

    SpringApplication.run(PromotionConsumerApplication.class, args);
  }
}
