package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionFreeGoodGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NotEmpty
public class PromotionFreeGoodGroupRequest extends ArrayList<PromotionFreeGoodGroup> {
  @Valid
  public List<PromotionFreeGoodGroup> getList() {
    return this;
  }
}
