package com.abinbev.b2b.promotion.consumer.listener.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Optional.ofNullable;

@Configuration
@ConfigurationProperties("spring.feedback.layer")
public class FeedbackLayerProperties {

    private List<String> countries;

    public List<String> getCountries() {

        return countries;
    }

    public void setCountries(final List<String> countries) {

        this.countries = countries;
    }

    public boolean isFeedbackLayerIntegrationActiveForCountry(final String country) {

        return ofNullable(countries).orElse(emptyList()).stream().anyMatch(s -> s.equalsIgnoreCase(country));
    }

}
