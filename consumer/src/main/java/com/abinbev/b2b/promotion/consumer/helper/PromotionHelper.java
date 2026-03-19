package com.abinbev.b2b.promotion.consumer.helper;

import com.abinbev.b2b.promotion.consumer.domain.PromotionModel;
import com.abinbev.b2b.promotion.consumer.exception.MapperException;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import com.abinbev.b2b.promotion.consumer.vo.PromotionListVo;
import com.abinbev.b2b.promotion.consumer.vo.PromotionVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class PromotionHelper {

  private PromotionHelper() {}

  public static PromotionListVo mapBaseMessageToPromotionListVo(
      @SuppressWarnings("rawtypes") final BaseMessage baseMessage) {

    try {
      PromotionListVo promotionListVo = new PromotionListVo();
      ObjectMapper mapper = new ObjectMapper();
      String promotionsFromRelay = mapper.writeValueAsString(baseMessage.getPayload());
      List<PromotionVo> promotionAccountList =
          mapper.readValue(promotionsFromRelay, new TypeReference<List<PromotionVo>>() {});
      promotionListVo.setPromotionVos(promotionAccountList);
      return promotionListVo;

    } catch (IOException e) {
      throw MapperException.cannotDeserializeJson(
          "Error while retrieving following content %s to deserialize into the following PromotionVo",
          baseMessage);
    }
  }

  public static PromotionModel mapVoToDomain(final PromotionVo promotionVo) {
    return PromotionModel.newBuilder()
        .withId(
            promotionVo.getPromotionId() != null
                ? promotionVo.getPromotionId()
                : promotionVo.getId())
        .withPromotionId(
            promotionVo.getPromotionId() != null
                ? promotionVo.getPromotionId()
                : promotionVo.getId())
        .withDescription(promotionVo.getDescription())
        .withStartDate(
            Optional.ofNullable(promotionVo.getStartDate()).map(OffsetDateTime::parse).orElse(null))
        .withEndDate(
            Optional.ofNullable(promotionVo.getEndDate()).map(OffsetDateTime::parse).orElse(null))
        .withTitle(promotionVo.getTitle())
        .withBudget(promotionVo.getBudget())
        .withQuantityLimit(promotionVo.getQuantityLimit())
        .withPromotionType(promotionVo.getType())
        .withImage(promotionVo.getImage())
        .withDeleted(Boolean.FALSE)
        .build();
  }
}
