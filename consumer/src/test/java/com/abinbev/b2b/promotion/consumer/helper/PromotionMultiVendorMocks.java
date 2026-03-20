package com.abinbev.b2b.promotion.consumer.helper;

import com.abinbev.b2b.promotion.consumer.domain.PromotionType;
import com.abinbev.b2b.promotion.consumer.domain.Translation;
import com.abinbev.b2b.promotion.consumer.vo.PromotionMultiVendorVO;
import java.math.BigDecimal;
import java.util.Map;

public class PromotionMultiVendorMocks {

  private PromotionMultiVendorMocks() {}

  public static final class PromotionMultiVendorVOBuilder {
    private String vendorPromotionId;
    private String title;
    private String description;
    private PromotionType type;
    private String startDate;
    private String endDate;
    private String image;
    private BigDecimal budget;
    private Integer quantityLimit;
    private Map<String, Translation> translations;
    private String defaultLanguage;

    private PromotionMultiVendorVOBuilder() {}

    public static PromotionMultiVendorVOBuilder newBuilder() {
      return new PromotionMultiVendorVOBuilder();
    }

    public PromotionMultiVendorVOBuilder withVendorPromotionId(String vendorPromotionId) {
      this.vendorPromotionId = vendorPromotionId;
      return this;
    }

    public PromotionMultiVendorVOBuilder withTitle(String title) {
      this.title = title;
      return this;
    }

    public PromotionMultiVendorVOBuilder withDescription(String description) {
      this.description = description;
      return this;
    }

    public PromotionMultiVendorVOBuilder withType(PromotionType type) {
      this.type = type;
      return this;
    }

    public PromotionMultiVendorVOBuilder withStartDate(String startDate) {
      this.startDate = startDate;
      return this;
    }

    public PromotionMultiVendorVOBuilder withEndDate(String endDate) {
      this.endDate = endDate;
      return this;
    }

    public PromotionMultiVendorVOBuilder withImage(String image) {
      this.image = image;
      return this;
    }

    public PromotionMultiVendorVOBuilder withBudget(BigDecimal budget) {
      this.budget = budget;
      return this;
    }

    public PromotionMultiVendorVOBuilder withQuantityLimit(Integer quantityLimit) {
      this.quantityLimit = quantityLimit;
      return this;
    }

    public PromotionMultiVendorVOBuilder withTranslations(Map<String, Translation> translations) {
      this.translations = translations;
      return this;
    }

    public PromotionMultiVendorVOBuilder withDefaultLanguage(String defaultLanguage) {
      this.defaultLanguage = defaultLanguage;
      return this;
    }

    public PromotionMultiVendorVO build() {
      final PromotionMultiVendorVO promotionMultiVendorVO = new PromotionMultiVendorVO();
      promotionMultiVendorVO.setVendorPromotionId(vendorPromotionId);
      promotionMultiVendorVO.setTitle(title);
      promotionMultiVendorVO.setDescription(description);
      promotionMultiVendorVO.setType(type);
      promotionMultiVendorVO.setStartDate(startDate);
      promotionMultiVendorVO.setEndDate(endDate);
      promotionMultiVendorVO.setImage(image);
      promotionMultiVendorVO.setBudget(budget);
      promotionMultiVendorVO.setQuantityLimit(quantityLimit);
      promotionMultiVendorVO.setTranslations(translations);
      promotionMultiVendorVO.setDefaultLanguage(defaultLanguage);
      return promotionMultiVendorVO;
    }
  }
}
