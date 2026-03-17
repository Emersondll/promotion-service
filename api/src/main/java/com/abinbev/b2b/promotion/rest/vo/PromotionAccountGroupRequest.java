package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionAccountGroup;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NotEmpty
public class PromotionAccountGroupRequest extends ArrayList<PromotionAccountGroup> {
  @Valid
  public List<PromotionAccountGroup> getList() {
    return this;
  }
}
