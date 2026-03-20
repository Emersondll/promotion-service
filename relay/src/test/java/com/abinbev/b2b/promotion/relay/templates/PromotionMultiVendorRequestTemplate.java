package com.abinbev.b2b.promotion.relay.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import com.abinbev.b2b.promotion.relay.domain.PromotionType;
import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import java.math.BigDecimal;

public class PromotionMultiVendorRequestTemplate implements TemplateLoader {

  public static final String BASE = "base";
  public static final String NULL_DATES = "null-dates";
  public static final String WITH_VALID_DEFAULT_LANGUAGE = "with-valid-default-language";
  public static final String WITH_INVALID_DEFAULT_LANGUAGE = "with-invalid-default-language";
  public static final String WITH_NON_OFFICIAL_DEFAULT_LANGUAGE =
      "with-non-official-default-language";

  @Override
  public void load() {
    Fixture.of(PromotionMultiVendor.class)
        .addTemplate(
            BASE,
            new Rule() {
              {
                add("vendorPromotionId", PromotionMocks.VENDOR_PROMOTION_ID_1);
                add("title", regex("\\w{8}"));
                add("description", regex("\\w{8}"));
                add("type", PromotionType.DISCOUNT);
                add("startDate", instant("2 years ago").asString("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
                add("endDate", instant("2 years after").asString("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"));
                add("image", "http://${vendorPromotionId}.com");
                add("budget", random(BigDecimal.class, range(0L, 200L)));
                add("quantityLimit", random(Integer.class, range(0L, 200L)));
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
        .addTemplate(WITH_VALID_DEFAULT_LANGUAGE)
        .inherits(
            BASE,
            new Rule() {
              {
                add("defaultLanguage", "en-US");
              }
            })
        .addTemplate(WITH_INVALID_DEFAULT_LANGUAGE)
        .inherits(
            BASE,
            new Rule() {
              {
                add("defaultLanguage", "enn-US");
              }
            })
        .addTemplate(WITH_NON_OFFICIAL_DEFAULT_LANGUAGE)
        .inherits(
            BASE,
            new Rule() {
              {
                add("defaultLanguage", "en-ES");
              }
            });
  }
}
