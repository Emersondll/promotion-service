package com.abinbev.b2b.promotion.relay.integration.response;

import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import java.util.ArrayList;
import java.util.List;

public class PromotionSingleVendorResponse {

  private String country;
  private String requestTraceId;
  private Long timestamp;
  private String operation;
  private String version;
  private String vendorId;

  private List<PromotionSingleVendor> payload = new ArrayList<>();

  public List<PromotionSingleVendor> getPayload() {
    return payload;
  }

  public void setPayload(List<PromotionSingleVendor> payload) {
    this.payload = payload;
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

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }
}
