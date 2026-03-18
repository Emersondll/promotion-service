package com.abinbev.b2b.promotion.rest.vo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NotEmpty
public class PromotionVOList extends ArrayList<PromotionVO> {
  @Valid
  public List<PromotionVO> getList() {
    return this;
  }
}
