package com.abinbev.b2b.promotion;

import com.abinbev.b2b.promotion.properties.DatabaseCollectionProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(DatabaseCollectionProperties.class)
@SpringBootApplication
public class PromotionServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(PromotionServiceApplication.class, args);
  }
}
