package com.abinbev.b2b.promotion.consumer.config.properties;

import java.time.Duration;

public class RabbitConnectionProperties {

  private String host;
  private int port;
  private String username;
  private String password;
  private String virtualHost;
  private ListenerProperties listener;
  private RabbitSslProperties ssl;

  public RabbitSslProperties getSsl() {
    return ssl;
  }

  public void setSsl(RabbitSslProperties ssl) {
    this.ssl = ssl;
  }

  public String getHost() {

    return host;
  }

  public void setHost(final String host) {

    this.host = host;
  }

  public int getPort() {

    return port;
  }

  public void setPort(final int port) {

    this.port = port;
  }

  public String getUsername() {

    return username;
  }

  public void setUsername(final String username) {

    this.username = username;
  }

  public String getPassword() {

    return password;
  }

  public void setPassword(final String password) {

    this.password = password;
  }

  public String getVirtualHost() {

    return virtualHost;
  }

  public void setVirtualHost(final String virtualHost) {

    this.virtualHost = virtualHost;
  }

  public ListenerProperties getListener() {

    return listener;
  }

  public void setListener(final ListenerProperties listener) {

    this.listener = listener;
  }

  public static class ListenerProperties {

    private SimpleListenerProperties simple;

    public SimpleListenerProperties getSimple() {

      return simple;
    }

    public void setSimple(final SimpleListenerProperties simple) {

      this.simple = simple;
    }
  }

  public static class SimpleListenerProperties {

    private int concurrency;
    private int maxConcurrency;
    private boolean autoStartup;
    private boolean defaultRequeueRejected;
    private ListenerRetryProperties retry;
    private Integer prefetch;
    private Integer prefetchStaging;

    public int getConcurrency() {

      return concurrency;
    }

    public void setConcurrency(final int concurrency) {

      this.concurrency = concurrency;
    }

    public int getMaxConcurrency() {

      return maxConcurrency;
    }

    public void setMaxConcurrency(final int maxConcurrency) {

      this.maxConcurrency = maxConcurrency;
    }

    public ListenerRetryProperties getRetry() {

      return retry;
    }

    public void setRetry(final ListenerRetryProperties retry) {

      this.retry = retry;
    }

    public boolean isAutoStartup() {

      return autoStartup;
    }

    public void setAutoStartup(final boolean autoStartup) {

      this.autoStartup = autoStartup;
    }

    public boolean isDefaultRequeueRejected() {

      return defaultRequeueRejected;
    }

    public void setDefaultRequeueRejected(final boolean defaultRequeueRejected) {

      this.defaultRequeueRejected = defaultRequeueRejected;
    }

    public Integer getPrefetch() {

      return prefetch;
    }

    public void setPrefetch(final Integer prefetch) {

      this.prefetch = prefetch;
    }

    public Integer getPrefetchStaging() {

      return prefetchStaging;
    }

    public void setPrefetchStaging(final Integer prefetchStaging) {

      this.prefetchStaging = prefetchStaging;
    }
  }

  public static class ListenerRetryProperties {

    private boolean enabled;
    private Duration initialInterval;
    private int maxAttempts;
    private Duration maxInterval;
    private int multiplier;

    public boolean isEnabled() {

      return enabled;
    }

    public void setEnabled(final boolean enabled) {

      this.enabled = enabled;
    }

    public Duration getInitialInterval() {

      return initialInterval;
    }

    public void setInitialInterval(final Duration initialInterval) {

      this.initialInterval = initialInterval;
    }

    public int getMaxAttempts() {

      return maxAttempts;
    }

    public void setMaxAttempts(final int maxAttempts) {

      this.maxAttempts = maxAttempts;
    }

    public Duration getMaxInterval() {

      return maxInterval;
    }

    public void setMaxInterval(final Duration maxInterval) {

      this.maxInterval = maxInterval;
    }

    public int getMultiplier() {

      return multiplier;
    }

    public void setMultiplier(final int multiplier) {

      this.multiplier = multiplier;
    }
  }

  public static class RabbitSslProperties {

    private Boolean enabled;
    private String algorithm;

    public Boolean getEnabled() {
      return enabled;
    }

    public void setEnabled(Boolean enabled) {
      this.enabled = enabled;
    }

    public String getAlgorithm() {
      return algorithm;
    }

    public void setAlgorithm(String algorithm) {
      this.algorithm = algorithm;
    }
  }
}
