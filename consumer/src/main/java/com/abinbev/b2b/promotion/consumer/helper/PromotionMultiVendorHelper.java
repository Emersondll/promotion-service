package com.abinbev.b2b.promotion.consumer.helper;

import com.abinbev.b2b.commons.platformId.core.PlatformIdEncoderDecoder;
import com.abinbev.b2b.commons.platformId.core.vo.PromotionPlatformId;
import com.abinbev.b2b.promotion.consumer.domain.PromotionAccountMultiVendorModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionMultiVendorModel;
import com.abinbev.b2b.promotion.consumer.vo.PromotionMultiVendorVO;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PromotionMultiVendorHelper {

  private static final PlatformIdEncoderDecoder platformIdHelper = new PlatformIdEncoderDecoder();

  private PromotionMultiVendorHelper() {}

  public static List<PromotionMultiVendorModel> mapToPromotionMultiVendorModel(
      final List<PromotionMultiVendorVO> promotionMultiVendorVOS, final String vendorId) {
    return promotionMultiVendorVOS.stream()
        .map(
            vo ->
                PromotionMultiVendorModel.newBuilder()
                    .withId(IdHelper.buildId(vo.getVendorPromotionId(), vendorId))
                    .withVendorPromotionId(vo.getVendorPromotionId())
                    .withVendorId(vendorId)
                    .withPromotionPlatformId(
                        platformIdHelper.encodePlatformId(
                            new PromotionPlatformId(vendorId, vo.getVendorPromotionId())))
                    .withPromotionType(vo.getType())
                    .withTitle(vo.getTitle())
                    .withDescription(vo.getDescription())
                    .withImage(vo.getImage())
                    .withBudget(vo.getBudget())
                    .withQuantityLimit(vo.getQuantityLimit())
                    .withStartDate(
                        Optional.ofNullable(vo.getStartDate())
                            .map(OffsetDateTime::parse)
                            .orElse(null))
                    .withEndDate(
                        Optional.ofNullable(vo.getEndDate())
                            .map(OffsetDateTime::parse)
                            .orElse(null))
                    .withDeleted(Boolean.FALSE)
                    .withTranslations(vo.getTranslations())
                    .withDefaultLanguage(vo.getDefaultLanguage())
                    .build())
        .distinct()
        .collect(Collectors.toList());
  }

  public static List<PromotionAccountMultiVendorModel> mapToPromotionAccountMultiVendorModel(
      final List<PromotionMultiVendorModel> promotionMultiVendorModels) {
    return promotionMultiVendorModels.stream()
        .map(
            model ->
                PromotionAccountMultiVendorModel.newBuilder()
                    .withVendorPromotionId(model.getVendorPromotionId())
                    .withVendorId(model.getVendorId())
                    .withInternalId(model.getId())
                    .withPromotionType(model.getPromotionType())
                    .withTitle(model.getTitle())
                    .withDescription(model.getDescription())
                    .withImage(model.getImage())
                    .withBudget(model.getBudget())
                    .withQuantityLimit(model.getQuantityLimit())
                    .withStartDate(model.getStartDate())
                    .withEndDate(model.getEndDate())
                    .withDisabled(Boolean.FALSE)
                    .withDeleted(model.getDeleted())
                    .withTranslations(model.getTranslations())
                    .withDefaultLanguage(model.getDefaultLanguage())
                    .build())
        .collect(Collectors.toList());
  }
}
