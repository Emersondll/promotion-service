package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionSkuGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NotEmpty
public class PromotionSkuGroupRequest extends ArrayList<PromotionSkuGroup> {
  @Valid
  public List<PromotionSkuGroup> getList() {
    return this;
  }
}
