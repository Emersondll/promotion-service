package com.abinbev.b2b.promotion.relay.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.abinbev.b2b.promotion.relay.validators.annotations.ValidDateRangeMockImpl;

public class ValidDateRangeMockImplTemplate implements TemplateLoader {

  public static String ALL_TRUE = "all-true";
  public static String ALL_FALSE = "all-false";
  public static String VALIDATE_TIME = "validate-time";
  public static String START_DATE = "start-date";
  public static String END_DATE = "end-date";
  public static String START_DATE_AND_END_DATE = "start-date-end-date";
  public static String START_DATE_AND_VALIDATE_TIME = "start-date-and-validate-time";
  public static String END_DATE_AND_VALIDATE_TIME = "end-date-and-validate-time";

  @Override
  public void load() {
    Fixture.of(ValidDateRangeMockImpl.class)
        .addTemplate(
            ALL_TRUE,
            new Rule() {
              {
                add("validateTime", true);
                add("startDateNullable", true);
                add("endDateNullable", true);
              }
            })
        .addTemplate(
            ALL_FALSE,
            new Rule() {
              {
                add("validateTime", false);
                add("startDateNullable", false);
                add("endDateNullable", false);
              }
            })
        .addTemplate(VALIDATE_TIME)
        .inherits(
            ALL_FALSE,
            new Rule() {
              {
                add("validateTime", true);
              }
            })
        .addTemplate(START_DATE)
        .inherits(
            ALL_FALSE,
            new Rule() {
              {
                add("startDateNullable", true);
              }
            })
        .addTemplate(END_DATE)
        .inherits(
            ALL_FALSE,
            new Rule() {
              {
                add("endDateNullable", true);
              }
            })
        .addTemplate(START_DATE_AND_END_DATE)
        .inherits(
            ALL_TRUE,
            new Rule() {
              {
                add("validateTime", false);
              }
            })
        .addTemplate(START_DATE_AND_VALIDATE_TIME)
        .inherits(
            ALL_TRUE,
            new Rule() {
              {
                add("endDateNullable", false);
              }
            })
        .addTemplate(END_DATE_AND_VALIDATE_TIME)
        .inherits(
            ALL_TRUE,
            new Rule() {
              {
                add("startDateNullable", false);
              }
            });
  }
}
