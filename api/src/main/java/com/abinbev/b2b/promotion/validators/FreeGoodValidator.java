package com.abinbev.b2b.promotion.validators;

import static com.abinbev.b2b.promotion.domain.model.PromotionType.FREE_GOOD;
import static com.abinbev.b2b.promotion.domain.model.PromotionType.STEPPED_FREE_GOOD;

import com.abinbev.b2b.promotion.rest.vo.PromotionVO;
import com.abinbev.b2b.promotion.validators.annotations.ValidFreeGood;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;

public class FreeGoodValidator implements ConstraintValidator<ValidFreeGood, PromotionVO> {

  @Override
  public void initialize(ValidFreeGood constraintAnnotation) {
    // There is no implementation for initialization
  }

  @Override
  public boolean isValid(
      final PromotionVO promotion, ConstraintValidatorContext constraintValidatorContext) {

    if (FREE_GOOD.equals(promotion.getType()) || STEPPED_FREE_GOOD.equals(promotion.getType())) {
      return !CollectionUtils.isEmpty(promotion.getFreeGoodGroupIds());
    }
    return true;
  }
}
