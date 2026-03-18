package com.abinbev.b2b.promotion.helpers;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.domain.model.Translation;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

public class PromotionMultiVendorMockBuilder {

  public static final String ES_LANGUAGE = "-ESPAÑOL";
  public static final String FR_LANGUAGE = "-FRENCH";

  private final PromotionMultivendor promotionMultivendor;

  public PromotionMultiVendorMockBuilder() {
    this.promotionMultivendor = new PromotionMultivendor();
  }

  public static PromotionMultiVendorMockBuilder builder() {
    return new PromotionMultiVendorMockBuilder();
  }

  public PromotionMultiVendorMockBuilder mock(final PromotionType promotionType) {
    base(promotionType);
    return this;
  }

  public PromotionMultiVendorMockBuilder mock(final PromotionType promotionType, final String ref) {
    base(promotionType);
    this.promotionMultivendor.setId(("ID-".concat(promotionType.name().concat(ref))));
    this.promotionMultivendor.setVendorPromotionId(
        "VENDOR-PROMOTION-ID-".concat(promotionType.name().concat(ref)));
    return this;
  }

  public PromotionMultiVendorMockBuilder mockWithAcceptLanguageFr(
      final PromotionType promotionType) {
    base(promotionType);
    this.promotionMultivendor.setTranslations(
        Map.of(
            PromotionMocks.ACCEPT_LANGUAGE_FR_CA,
            new Translation(
                this.promotionMultivendor.getTitle().concat(FR_LANGUAGE),
                this.promotionMultivendor.getDescription().concat(FR_LANGUAGE),
                this.promotionMultivendor.getImage().concat(FR_LANGUAGE))));
    return this;
  }

  private void base(final PromotionType promotionType) {
    final String title = "TITLE-".concat(promotionType.name());
    final String description = "DESCRIPTION-".concat(promotionType.name());
    final String image = "IMAGE-".concat(promotionType.name());

    this.promotionMultivendor.setId(("ID-".concat(promotionType.name())));
    this.promotionMultivendor.setVendorPromotionId(
        "VENDOR-PROMOTION-ID-".concat(promotionType.name()));
    this.promotionMultivendor.setTitle(title);
    this.promotionMultivendor.setDescription(description);
    this.promotionMultivendor.setPromotionType(promotionType);
    this.promotionMultivendor.setBudget(BigDecimal.TEN);
    this.promotionMultivendor.setImage(image);
    this.promotionMultivendor.setQuantityLimit(100);
    this.promotionMultivendor.setStartDate(OffsetDateTime.parse("2021-05-01T00:00:00.000Z"));
    this.promotionMultivendor.setEndDate(OffsetDateTime.parse("2040-05-01T00:00:00.000Z"));
    this.promotionMultivendor.setUpdateAt(OffsetDateTime.parse("2021-05-03T00:00:00.000Z"));
    this.promotionMultivendor.setTranslations(
        Map.of(
            PromotionMocks.ACCEPT_LANGUAGE_ES_US,
            new Translation(
                title.concat(ES_LANGUAGE),
                description.concat(ES_LANGUAGE),
                image.concat(ES_LANGUAGE))));
  }

  public PromotionMultiVendorMockBuilder withVendorId(String vendorId) {
    this.promotionMultivendor.setVendorId(vendorId);
    return this;
  }

  public PromotionMultiVendorMockBuilder addYearsToStartDate(Long yearsToAdd) {
    this.promotionMultivendor.setStartDate(
        this.promotionMultivendor.getStartDate().plusYears(yearsToAdd));
    return this;
  }

  public PromotionMultiVendorMockBuilder withPlatformPromotionId(String platformPromotionId) {
    this.promotionMultivendor.setPromotionPlatformId(platformPromotionId);
    return this;
  }

  public PromotionMultivendor build() {
    return promotionMultivendor;
  }
}
