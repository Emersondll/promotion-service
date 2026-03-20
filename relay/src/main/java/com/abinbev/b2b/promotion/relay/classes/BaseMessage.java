package com.abinbev.b2b.promotion.relay.classes;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseMessage<T> extends GenericLoggableObject {
  @JsonProperty private String country;

  @JsonProperty private String requestTraceId;

  @JsonProperty private String parentRequestTraceId;

  @JsonProperty private Long timestamp;

  @JsonProperty private String operation = "UNDEFINED";

  @JsonProperty private String version;

  @JsonProperty private String vendorId;

  @JsonProperty private T payload;

  public BaseMessage() {
    super();
  }

  public BaseMessage(final String country, final String requestTraceId, final Long timestamp) {
    this.country = country;
    this.requestTraceId = requestTraceId;
    this.timestamp = timestamp;
  }

  public BaseMessage(
      final String country,
      final String requestTraceId,
      final Long timestamp,
      final String parentRequestTraceId) {
    this.country = country;
    this.requestTraceId = requestTraceId;
    this.timestamp = timestamp;
    this.parentRequestTraceId = parentRequestTraceId;
  }

  public void assign(BaseMessage credential) {
    this.country = credential.country;
    this.requestTraceId = credential.requestTraceId;
    this.timestamp = credential.timestamp;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getRequestTraceId() {
    return requestTraceId;
  }

  public void setRequestTraceId(String requestTraceId) {
    this.requestTraceId = requestTraceId;
  }

  public String getParentRequestTraceId() {
    return parentRequestTraceId;
  }

  public void setParentRequestTraceId(String parentRequestTraceId) {
    this.parentRequestTraceId = parentRequestTraceId;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public String getOperation() {
    return operation;
  }

  public void setOperation(String operation) {
    this.operation = operation;
  }

  public String getVersion() {

    return version;
  }

  public void setVersion(String version) {

    this.version = version;
  }

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }
}
