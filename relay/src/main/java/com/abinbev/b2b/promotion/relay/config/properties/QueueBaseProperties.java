package com.abinbev.b2b.promotion.relay.config.properties;

import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class QueueBaseProperties {

  private String name;
  private String exchange;

  public String getName(final String country) {
    return this.name.concat("-").concat(country.toLowerCase());
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getExchange() {
    if (Objects.nonNull(exchange)) {
      return exchange;
    }
    return this.name.concat(".exchange");
  }

  public void setExchange(final String exchange) {
    this.exchange = exchange;
  }
}
