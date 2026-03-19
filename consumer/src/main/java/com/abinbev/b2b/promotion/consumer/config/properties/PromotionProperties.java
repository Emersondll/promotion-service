package com.abinbev.b2b.promotion.consumer.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "message.promotion")
public class PromotionProperties extends BaseQueueProperties {}
