package com.abinbev.b2b.promotion.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.annotation.Id;

public class PromotionAccountGroup {

  @Id
  @JsonProperty
  @NotBlank
  @Size(max = 255)
  private String accountGroupId;

  @JsonProperty @NotEmpty private List<String> accounts = new ArrayList<>();

  @JsonIgnore private OffsetDateTime createAt;
  @JsonIgnore private OffsetDateTime updateAt;

  @JsonIgnore private boolean deleted;

  public String getAccountGroupId() {

    return accountGroupId;
  }

  public void setAccountGroupId(String accountGroupId) {

    this.accountGroupId = accountGroupId;
  }

  public OffsetDateTime getCreateAt() {

    return createAt;
  }

  public void setCreateAt(OffsetDateTime createAt) {

    this.createAt = createAt;
  }

  public OffsetDateTime getUpdateAt() {

    return updateAt;
  }

  public void setUpdateAt(OffsetDateTime updateAt) {

    this.updateAt = updateAt;
  }

  public boolean isDeleted() {

    return deleted;
  }

  public void setDeleted(boolean deleted) {

    this.deleted = deleted;
  }

  public List<String> getAccounts() {

    return accounts;
  }

  public void setAccounts(List<String> accounts) {

    this.accounts = accounts;
  }
}
