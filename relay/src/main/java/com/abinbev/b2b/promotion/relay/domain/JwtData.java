package com.abinbev.b2b.promotion.relay.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtData {

  @JsonProperty private String vendorId;

  public String getVendorId() {
    return vendorId;
  }

  public void setVendorId(final String vendorId) {
    this.vendorId = vendorId;
  }
}
