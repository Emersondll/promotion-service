package com.abinbev.b2b.promotion.relay.validators;

import com.abinbev.b2b.promotion.relay.config.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.relay.helpers.LocaleHelper;
import com.abinbev.b2b.promotion.relay.validators.annotations.NullableLocale;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class NullableLocaleValidator implements ConstraintValidator<NullableLocale, String> {

  private NonOfficialSupportedLanguages nonOfficialSupportedLanguages;

  @Override
  public void initialize(NullableLocale constraintAnnotation) {}

  @Autowired
  public NullableLocaleValidator(NonOfficialSupportedLanguages nonOfficialSupportedLanguages) {
    this.nonOfficialSupportedLanguages = nonOfficialSupportedLanguages;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    final List<String> nonOfficialSupportedLanguageList =
        nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages();
    return LocaleHelper.isValidOrEmpty(value, nonOfficialSupportedLanguageList);
  }
}
