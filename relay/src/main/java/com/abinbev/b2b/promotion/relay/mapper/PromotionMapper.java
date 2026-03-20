package com.abinbev.b2b.promotion.relay.mapper;

import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromotionMapper {
  PromotionMapper INSTANCE = Mappers.getMapper(PromotionMapper.class);

  @Mapping(
      target = "vendorPromotionId",
      expression =
          "java(org.apache.commons.lang3.ObjectUtils.defaultIfNull(promotion.getPromotionId(), promotion.getId() ) )")
  PromotionMultiVendor singleToMultiVendor(PromotionSingleVendor promotion);
}
