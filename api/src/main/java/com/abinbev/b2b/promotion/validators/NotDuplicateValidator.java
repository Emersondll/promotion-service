package com.abinbev.b2b.promotion.validators;

import com.abinbev.b2b.promotion.validators.annotations.NotDuplicate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class NotDuplicateValidator implements ConstraintValidator<NotDuplicate, List<?>> {

  @Override
  public void initialize(NotDuplicate constraintAnnotation) {
    // There is no implementation for initialization
  }

  @Override
  public boolean isValid(List<?> list, ConstraintValidatorContext constraintValidatorContext) {
    return areUnique(list.stream());
  }

  private <T> boolean areUnique(final Stream<T> stream) {
    final Set<T> seen = new HashSet<>();
    return stream.allMatch(seen::add);
  }
}
