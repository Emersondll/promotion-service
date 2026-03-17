package com.abinbev.b2b.promotion.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "async")
public class AsyncProperties {

  private Integer corePoolSize;
  private Integer maxPoolSize;

  private Integer queueCapacity;

  public Integer getCorePoolSize() {
    return corePoolSize;
  }

  public void setCorePoolSize(Integer corePoolSize) {
    this.corePoolSize = corePoolSize;
  }

  public Integer getMaxPoolSize() {
    return maxPoolSize;
  }

  public void setMaxPoolSize(Integer maxPoolSize) {
    this.maxPoolSize = maxPoolSize;
  }

  public Integer getQueueCapacity() {
    return queueCapacity;
  }

  public void setQueueCapacity(Integer queueCapacity) {
    this.queueCapacity = queueCapacity;
  }
}
