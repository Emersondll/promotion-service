package com.abinbev.b2b.promotion.consumer.helper;

import com.mongodb.ErrorCategory;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.BulkOperationException;

public class DuplicateKeyHelper {

  private static final Logger LOGGER = LoggerFactory.getLogger(DuplicateKeyHelper.class);

  public static void validateDuplicateKeyException(final BulkOperationException e) {
    final boolean duplicateKeyError =
        Objects.nonNull(e.getErrors())
            && e.getErrors().stream()
                .anyMatch(
                    i -> ErrorCategory.fromErrorCode(i.getCode()) == ErrorCategory.DUPLICATE_KEY);
    if (duplicateKeyError) LOGGER.error(e.getMessage());
    else throw e;
  }
}
