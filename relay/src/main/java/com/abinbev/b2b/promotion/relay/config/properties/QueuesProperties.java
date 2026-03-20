package com.abinbev.b2b.promotion.relay.config.properties;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "queues")
public class QueuesProperties {

  private Map<String, QueueBaseProperties> multiVendor;

  public Map<String, QueueBaseProperties> getMultiVendor() {
    return multiVendor;
  }

  public void setMultiVendor(Map<String, QueueBaseProperties> multiVendor) {
    this.multiVendor = multiVendor;
  }
}
