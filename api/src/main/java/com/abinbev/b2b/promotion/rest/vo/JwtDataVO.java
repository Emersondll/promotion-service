package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtDataVO {

  @JsonProperty private List<String> roles;

  @JsonProperty private List<String> accounts;

  @JsonProperty("extension_accountids")
  private List<String> extensionAccountIds;

  @JsonProperty private String country;

  @JsonProperty private String app = ApiConstants.REQUEST_PARAM_B2B;

  @JsonProperty private String accountID;

  @JsonProperty private List<String> supportedCountries;

  public List<String> getExtensionAccountIds() {
    return extensionAccountIds;
  }

  public void setExtensionAccountIds(final String extensionAccountIds) {
    if (extensionAccountIds == null) {
      this.extensionAccountIds = new ArrayList<>();
    } else {
      this.extensionAccountIds =
          Arrays.asList(
              extensionAccountIds.substring(1, extensionAccountIds.length() - 1).split(","));
    }
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(final List<String> roles) {
    this.roles = roles;
  }

  public List<String> getAccounts() {
    return accounts;
  }

  public void setAccounts(final List<String> accounts) {
    this.accounts = accounts;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(final String country) {
    this.country = country;
  }

  public String getApp() {
    return app;
  }

  public void setApp(String app) {
    this.app = app;
  }

  public String getAccountID() {
    return accountID;
  }

  public void setAccountID(String accountID) {
    this.accountID = accountID;
  }

  public List<String> getSupportedCountries() {
    return supportedCountries;
  }

  public void setSupportedCountries(List<String> supportedCountries) {
    this.supportedCountries = supportedCountries;
  }
}
