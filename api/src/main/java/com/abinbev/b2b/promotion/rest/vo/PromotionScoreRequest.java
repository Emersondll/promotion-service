package com.abinbev.b2b.promotion.rest.vo;

import com.abinbev.b2b.promotion.domain.model.PromotionScore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@NotEmpty
public class PromotionScoreRequest extends ArrayList<PromotionScore> {
  @Valid
  public List<PromotionScore> getList() {
    return this;
  }
}
