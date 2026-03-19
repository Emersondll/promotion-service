package com.abinbev.b2b.promotion.consumer.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "message.bees-sync-integration")
public class BeesSyncIntegrationProperties {

	private String exchange;

	public String getExchange() {

		return exchange;
	}

	public void setExchange(String exchange) {

		this.exchange = exchange;
	}

}
