package com.abinbev.b2b.promotion.consumer.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class ThrowableIssueFactory {

  public Issue createIssueBasedOnThrowable(final Throwable throwable) {
    Issue returnIssue;
    final Throwable rootCause = ExceptionUtils.getRootCause(throwable);

    try {

      if (rootCause instanceof HttpClientErrorException) {
        returnIssue = parseHttpClientErrorException((HttpClientErrorException) rootCause);
      } else if (rootCause instanceof GlobalException) {
        returnIssue = ((GlobalException) rootCause).getIssue();
      } else if (rootCause instanceof MongoException) {
        returnIssue = mongoException(throwable);
      } else if (rootCause instanceof DataAccessException) {
        returnIssue = dataAccessException(throwable);
      } else {
        returnIssue = throwable(throwable);
      }

    } catch (final JsonProcessingException exception) {
      returnIssue = throwable(throwable);
    }
    return returnIssue;
  }

  private static Issue parseHttpClientErrorException(final HttpClientErrorException ex)
      throws JsonProcessingException {
    final ObjectMapper mapper = new ObjectMapper();

    return mapper.readValue(ex.getResponseBodyAsString(), Issue.class);
  }

  public Issue mongoException(final Throwable throwable) {
    return new Issue(
        IssueEnum.MONGO_ERROR,
        ExceptionUtils.getRootCauseMessage(throwable),
        ExceptionUtils.getMessage(throwable));
  }

  public Issue dataAccessException(final Throwable throwable) {
    return new Issue(
        IssueEnum.DATA_ACCESS_ERROR,
        ExceptionUtils.getRootCauseMessage(throwable),
        ExceptionUtils.getMessage(throwable));
  }

  public Issue throwable(final Throwable throwable) {
    return new Issue(
        IssueEnum.GENERIC_ERROR,
        ExceptionUtils.getRootCauseMessage(throwable),
        ExceptionUtils.getStackTrace(throwable));
  }
}
