package com.abinbev.b2b.promotion.relay.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "queues.multi-vendor.promotion")
public class PromotionMultiVendorProperties extends QueueBaseProperties {}
