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

public class PromotionSkuGroup {

  @Id
  @JsonProperty
  @NotBlank
  @Size(max = 255)
  private String skuGroupId;

  @JsonProperty @NotEmpty private List<String> skus = new ArrayList<>();

  @JsonIgnore private OffsetDateTime createAt;
  @JsonIgnore private OffsetDateTime updateAt;
  @JsonIgnore private boolean deleted;

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

  public String getSkuGroupId() {

    return skuGroupId;
  }

  public void setSkuGroupId(String skuGroupId) {

    this.skuGroupId = skuGroupId;
  }

  public List<String> getSkus() {

    return skus;
  }

  public void setSkus(List<String> skus) {

    this.skus = skus;
  }

  public boolean isDeleted() {

    return deleted;
  }

  public void setDeleted(boolean deleted) {

    this.deleted = deleted;
  }
}
