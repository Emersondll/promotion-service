package com.abinbev.b2b.promotion.validators;

import com.abinbev.b2b.promotion.constants.LogConstant;
import com.abinbev.b2b.promotion.validators.annotations.MaxSizeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

public class MaxSizeConstraintValidator
    implements ConstraintValidator<MaxSizeConstraint, Collection<?>> {

  protected static final Logger LOGGER = LoggerFactory.getLogger(MaxSizeConstraintValidator.class);

  @Autowired private Environment environment;

  private int maxSize = -1;

  @Override
  public boolean isValid(Collection<?> values, ConstraintValidatorContext context) {
    return CollectionUtils.isEmpty(values) || maxSize == -1 || maxSize >= values.size();
  }

  @Override
  public void initialize(final MaxSizeConstraint annotation) {
    try {
      var property = environment.getProperty(annotation.property());
      if (Objects.isNull(property)) {
        return;
      }
      maxSize = Integer.parseInt(property);
    } catch (Exception ex) {
      LOGGER.error(LogConstant.ERROR.INVALID_MAX_SIZE_PROPERTY_VALUE, annotation.property(), ex);
    }
  }
}
