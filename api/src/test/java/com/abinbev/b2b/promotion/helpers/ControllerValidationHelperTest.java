package com.abinbev.b2b.promotion.helpers;

import com.abinbev.b2b.promotion.exceptions.BadRequestException;
import com.abinbev.b2b.promotion.exceptions.IssueEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ControllerValidationHelperTest {
  @Test
  public void validHeaderValueShouldNotBeBlank() {

    Assertions.assertThrows(
        BadRequestException.class,
        () -> {
          try {
            ControllerValidationHelper.validateRequiredHeader("requestTraceId", null);
            Assertions.fail("value header is required!");
          } catch (final BadRequestException badRequest) {
            Assertions.assertEquals(
                IssueEnum.REQUEST_HEADER_NOT_VALID.getFormattedMessage("requestTraceId", null),
                badRequest.getIssue().getMessage());
            throw badRequest;
          }
        });
  }

  @Test
  public void validHeaderShouldBeValidatedSuccessfully() {
    ControllerValidationHelper.validateRequiredHeader("requestTraceId", "123");
  }
}
