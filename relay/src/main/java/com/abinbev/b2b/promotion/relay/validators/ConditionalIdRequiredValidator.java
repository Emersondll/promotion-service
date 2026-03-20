package com.abinbev.b2b.promotion.relay.validators;

import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import com.abinbev.b2b.promotion.relay.validators.annotations.ConditionalIdRequired;
import com.google.common.base.Strings;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConditionalIdRequiredValidator
    implements ConstraintValidator<ConditionalIdRequired, PromotionSingleVendor> {

  @Override
  public void initialize(ConditionalIdRequired conditionalIdRequired) {
    // There is no implementation for initialization
  }

  @Override
  public boolean isValid(
      PromotionSingleVendor promotionSingleVendor,
      ConstraintValidatorContext constraintValidatorContext) {
    return !Strings.isNullOrEmpty(promotionSingleVendor.getId())
        || !Strings.isNullOrEmpty(promotionSingleVendor.getPromotionId());
  }
}
