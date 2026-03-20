package com.abinbev.b2b.promotion.relay.rest.vo;

import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NotEmpty
public class PromotionMultiVendorRequest extends ArrayList<PromotionMultiVendor> {

  @Valid
  public List<PromotionMultiVendor> getList() {
    return this;
  }
}
