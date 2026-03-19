package com.abinbev.b2b.promotion.consumer.listener.message;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.COUNTRY_HEADER;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.HEADER_RETRIES;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.METHOD;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.PARENT_REQUEST_TRACE_ID;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.REQUEST_TRACE_ID_HEADER;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.TIMESTAMP;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.VERSION;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BaseMessage<T> extends GenericLoggableObject {
  @JsonProperty private String country;

  @JsonProperty private String requestTraceId;

  @JsonProperty private String parentRequestTraceId;

  @JsonProperty private Long timestamp;

  @JsonProperty private String operation = "UNDEFINED";

  @JsonProperty private T payload;

  @JsonProperty private String version;

  @JsonProperty private int retries;

  public BaseMessage() {
    super();
  }

  public BaseMessage(final String country, final String requestTraceId, final Long timestamp) {
    this.country = country;
    this.requestTraceId = requestTraceId;
    this.timestamp = timestamp;
  }

  public BaseMessage(
      final String country, final String requestTraceId, final Long timestamp, int retries) {
    this.country = country;
    this.requestTraceId = requestTraceId;
    this.timestamp = timestamp;
    this.retries = retries;
  }

  public BaseMessage(Map<String, String> headers) {
    if (headers != null) {
      this.country = headers.getOrDefault(COUNTRY_HEADER, null);
      this.requestTraceId = headers.getOrDefault(REQUEST_TRACE_ID_HEADER, null);
      this.parentRequestTraceId = headers.getOrDefault(PARENT_REQUEST_TRACE_ID, null);
      this.timestamp = Long.parseLong(String.valueOf(headers.getOrDefault(TIMESTAMP, "0")));
      this.operation = headers.getOrDefault(METHOD, null);
      this.version = headers.getOrDefault(VERSION, null);
      this.retries = Integer.parseInt(headers.getOrDefault(HEADER_RETRIES, "0"));
    }
  }

  public void assign(@SuppressWarnings("rawtypes") BaseMessage credential) {
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

  public T getPayload() {
    return payload;
  }

  public void setPayload(T payload) {
    this.payload = payload;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public int getRetries() {

    return retries;
  }

  public void setRetries(int retries) {

    this.retries = retries;
  }
}
