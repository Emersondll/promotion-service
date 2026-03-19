package com.abinbev.b2b.promotion.consumer.listener.message;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.VENDOR_ID;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PromotionMultiVendorMessage<T> extends BaseMessage<T> {

  @JsonProperty private String vendorId;

  public PromotionMultiVendorMessage() {}

  public PromotionMultiVendorMessage(String country, String requestTraceId, Long timestamp) {
    super(country, requestTraceId, timestamp);
  }

  public PromotionMultiVendorMessage(
      String country, String requestTraceId, Long timestamp, int retries) {
    super(country, requestTraceId, timestamp, retries);
  }

  public PromotionMultiVendorMessage(Map<String, String> headers) {
    super(headers);
    this.vendorId = headers.getOrDefault(VENDOR_ID, null);
  }

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(String vendorId) {
    this.vendorId = vendorId;
  }
}
