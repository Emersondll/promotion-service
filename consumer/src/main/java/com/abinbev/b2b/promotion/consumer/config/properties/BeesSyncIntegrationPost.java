package com.abinbev.b2b.promotion.consumer.config.properties;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.API_PROMOTION_VERSION_V3;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.UNDERSCORE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeesSyncIntegrationPost extends BaseQueueProperties {

	@Value("${message.bees-sync-integration.enabled-countries}")
	private final Set<String> enabledCountries = new HashSet<>();

	@Value("${message.bees-sync-integration.post.queue-name}")
	private String queueName;

	private Map<String, QueueProperties> queuesProperties = new HashMap<>();

	private static final String BEES_SYNC_ROUTING_KEY_PATTERN = "%s.%s.%s.post";

	public Set<String> getEnabledCountries() {
		return enabledCountries;
	}

	@Override
	public Map<String, QueueProperties> getQueues() {

		if (queuesProperties.isEmpty()) {
			queuesProperties = createQueues();
		}
		return queuesProperties;
	}

	@Override
	public QueueProperties getQueue(final String country) {

		return getQueues().get(country.toLowerCase());
	}

	private Map<String, QueueProperties> createQueues() {
		HashMap<String, QueueProperties> queuePropertiesHashMap = new HashMap<>();
		for (String country : getEnabledCountries()) {
			String countryLowerCase = country.toLowerCase();
			QueueProperties queueProperties = new QueueProperties();
			queueProperties.setQueueName(countryLowerCase + UNDERSCORE + queueName);
			queueProperties.setRoutingKey(String.format(BEES_SYNC_ROUTING_KEY_PATTERN, countryLowerCase, "*", API_PROMOTION_VERSION_V3));
			queuePropertiesHashMap.put(countryLowerCase, queueProperties);
		}
		return queuePropertiesHashMap;
	}

}
