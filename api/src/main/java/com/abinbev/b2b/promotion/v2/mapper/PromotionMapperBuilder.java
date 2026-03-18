package com.abinbev.b2b.promotion.v2.mapper;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.domain.model.Translation;
import com.abinbev.b2b.promotion.v2.rest.vo.PromotionResponse;
import io.micrometer.core.instrument.util.StringUtils;
import java.time.OffsetDateTime;
import org.apache.commons.collections4.MapUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PromotionMapperBuilder {

  @Mapping(source = "id", target = "promotionId")
  @Mapping(source = "id", target = "vendorPromotionId")
  @Mapping(source = "vendorId", target = "vendorId")
  @Mapping(source = "promotionType", target = "type", qualifiedByName = "setPromotionType")
  @Mapping(
      source = "startDate",
      target = "startDate",
      qualifiedByName = "setOffsetDateTimeToString")
  @Mapping(source = "endDate", target = "endDate", qualifiedByName = "setOffsetDateTimeToString")
  @Mapping(source = "image", target = "image")
  @Mapping(source = "updateAt", target = "updatedAt", qualifiedByName = "setOffsetDateTimeToString")
  PromotionResponse mapToSingleVendorResponseForPromotion(
      PromotionMultivendor promotion, @Context String acceptLanguage);

  @Mapping(source = "id", target = "promotionId")
  @Mapping(source = "promotionType", target = "type", qualifiedByName = "setPromotionType")
  @Mapping(
      source = "startDate",
      target = "startDate",
      qualifiedByName = "setOffsetDateTimeToString")
  @Mapping(source = "endDate", target = "endDate", qualifiedByName = "setOffsetDateTimeToString")
  @Mapping(source = "image", target = "image")
  @Mapping(source = "updateAt", target = "updatedAt", qualifiedByName = "setOffsetDateTimeToString")
  PromotionResponse mapToMultiVendorResponseForPromotion(
      PromotionMultivendor promotion, @Context String acceptLanguage);

  @AfterMapping
  default void setFieldsFromTranslation(
      PromotionMultivendor promotionMultivendor,
      @MappingTarget PromotionResponse promotionResponse,
      @Context String acceptLanguage) {
    if (StringUtils.isNotBlank(acceptLanguage)
        && !MapUtils.isEmpty(promotionMultivendor.getTranslations())) {
      final Translation translation = promotionMultivendor.getTranslations().get(acceptLanguage);
      if (translation != null) {
        promotionResponse.setTitle(translation.getTitle());
        promotionResponse.setDescription(translation.getDescription());
      }
    }
  }

  @Named("setOffsetDateTimeToString")
  static String setOffsetDateTimeToString(final OffsetDateTime offsetDateTime) {
    if (offsetDateTime != null) {
      return offsetDateTime.toString();
    }
    return null;
  }

  @Named("setPromotionType")
  static String setPromotionType(final PromotionType promotionType) {
    if (promotionType != null) {
      return promotionType.name();
    }
    return null;
  }
}
