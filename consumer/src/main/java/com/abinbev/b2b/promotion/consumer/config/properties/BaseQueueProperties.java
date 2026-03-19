package com.abinbev.b2b.promotion.consumer.config.properties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

public class BaseQueueProperties {

  private String exchange;
  private Map<String, QueueProperties> queues;

  @Value("${zones.activated}")
  private List<String> activated;

  @Value("${zones.deactivated}")
  private List<String> deactivated;

  public Map<String, QueueProperties> getQueues() {

    return queues;
  }

  public void setQueues(final Map<String, QueueProperties> queues) {

    this.queues = queues;
  }

  public QueueProperties getQueue(final String country) {

    return queues.get(country.toLowerCase());
  }

  public String getExchange() {

    return exchange;
  }

  public void setExchange(final String exchange) {

    this.exchange = exchange;
  }

  public static class QueueProperties {

    private String queueName;
    private String routingKey;

    public String getQueueName() {

      return queueName;
    }

    public void setQueueName(final String queueName) {

      this.queueName = queueName;
    }

    public String getRoutingKey() {

      return routingKey;
    }

    public void setRoutingKey(final String routingKey) {

      this.routingKey = routingKey;
    }
  }

  public String[] getAllQueueNames() {
    final List<String> queueList = new ArrayList<>();
    if (CollectionUtils.isEmpty(this.activated)) {
      List<QueueProperties> queuePropertiesList =
          this.getQueues().values().stream()
              .filter(
                  queueValue ->
                      !this.deactivated.contains(
                          queueValue.getRoutingKey().toUpperCase().substring(0, 2)))
              .collect(Collectors.toList());
      for (final QueueProperties properties : queuePropertiesList) {
        queueList.add(properties.getQueueName());
      }

    } else {
      this.activated.forEach(
          activatedZone -> queueList.add(this.getQueue(activatedZone).getQueueName()));
    }
    return queueList.toArray(new String[0]);
  }
}
