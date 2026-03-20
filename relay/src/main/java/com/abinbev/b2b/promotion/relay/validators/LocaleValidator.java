package com.abinbev.b2b.promotion.relay.validators;

import com.abinbev.b2b.promotion.relay.config.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.relay.domain.Translation;
import com.abinbev.b2b.promotion.relay.helpers.LocaleHelper;
import com.abinbev.b2b.promotion.relay.validators.annotations.ValidLocale;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

public class LocaleValidator implements ConstraintValidator<ValidLocale, Map<String, Translation>> {

  private final NonOfficialSupportedLanguages nonOfficialSupportedLanguages;

  @Autowired
  public LocaleValidator(NonOfficialSupportedLanguages nonOfficialSupportedLanguages) {
    this.nonOfficialSupportedLanguages = nonOfficialSupportedLanguages;
  }

  @Override
  public void initialize(ValidLocale constraintAnnotation) {}

  @Override
  public boolean isValid(Map<String, Translation> value, ConstraintValidatorContext context) {
    return CollectionUtils.isEmpty(value)
        || LocaleHelper.isLocaleSetValid(
            value.keySet(), nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages());
  }
}
