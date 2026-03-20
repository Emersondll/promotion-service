package com.abinbev.b2b.promotion.relay.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.abinbev.b2b.promotion.relay.validators.annotations.DateRangeRequestMockImpl;
import java.time.OffsetDateTime;

public class DateRangeRequestMockImplTemplate implements TemplateLoader {

  public static String BASE = "base";
  public static String BASE_ONLY_DATE = "base-only-date";
  public static final String NULL_DATES = "null-dates";
  public static final String NULL_START_DATE = "null-start-date";
  public static final String WRONG_START_DATE_FORMAT = "wrong-start-date-format";
  public static final String START_DATE_BEFORE_EPOCH = "start-date-before-epoch";
  public static final String NULL_END_DATE = "null-end-date";
  public static final String WRONG_END_DATE_FORMAT = "wrong-end-date-format";
  public static final String END_DATE_EQUALS_TO_START_DATE = "end-date-equals-to-start-date";
  public static final String END_DATE_BEFORE_START_DATE = "end-date-before-start-date";

  @Override
  public void load() {
    Fixture.of(DateRangeRequestMockImpl.class)
        .addTemplate(
            BASE,
            new Rule() {
              {
                add("startDate", instant("2 years ago").asString("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
                add("endDate", instant("2 years after").asString("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
              }
            })
        .addTemplate(
            BASE_ONLY_DATE,
            new Rule() {
              {
                add("startDate", instant("2 years ago").asString("yyyy-MM-dd"));
                add("endDate", instant("2 years after").asString("yyyy-MM-dd"));
              }
            })
        .addTemplate(NULL_DATES)
        .inherits(
            BASE,
            new Rule() {
              {
                add("startDate", null);
                add("endDate", null);
              }
            })
        .addTemplate(NULL_START_DATE)
        .inherits(
            BASE,
            new Rule() {
              {
                add("startDate", null);
              }
            })
        .addTemplate(WRONG_START_DATE_FORMAT)
        .inherits(
            BASE,
            new Rule() {
              {
                add("startDate", instant("2 years ago").asString("yyyy-MM-ddHH:mm:ss.SSSXXX"));
              }
            })
        .addTemplate(START_DATE_BEFORE_EPOCH)
        .inherits(
            BASE,
            new Rule() {
              {
                add("startDate", OffsetDateTime.MIN.toString());
              }
            })
        .addTemplate(NULL_END_DATE)
        .inherits(
            BASE,
            new Rule() {
              {
                add("endDate", null);
              }
            })
        .addTemplate(WRONG_END_DATE_FORMAT)
        .inherits(
            BASE,
            new Rule() {
              {
                add("endDate", instant("2 years after").asString("yyyy-MM-ddHH:mm:ss.SSSXXX"));
              }
            })
        .addTemplate(END_DATE_EQUALS_TO_START_DATE)
        .inherits(
            BASE,
            new Rule() {
              {
                add("endDate", "${startDate}");
              }
            })
        .addTemplate(END_DATE_BEFORE_START_DATE)
        .inherits(
            BASE,
            new Rule() {
              {
                add("endDate", instant("10 years before").asString("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
              }
            });
  }
}
