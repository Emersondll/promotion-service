package com.abinbev.b2b.promotion.relay.rest.vo;

import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NotEmpty
public class PromotionSingleVendorRequest extends ArrayList<PromotionSingleVendor> {

  @Valid
  public List<PromotionSingleVendor> getList() {
    return this;
  }
}
