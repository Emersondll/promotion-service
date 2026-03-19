package com.abinbev.b2b.promotion.consumer.helper;

import com.abinbev.b2b.promotion.consumer.exception.MapperException;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountDeleteVo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class PromotionAccountMapperHelper {

  private PromotionAccountMapperHelper() {}

  public static PromotionAccountDeleteVo convertBaseMessageToPromotionAccountDelete(
      @SuppressWarnings("rawtypes") BaseMessage baseMessage) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      String promotionsFromRelay = mapper.writeValueAsString(baseMessage.getPayload());
      return mapper.readValue(
          promotionsFromRelay, new TypeReference<PromotionAccountDeleteVo>() {});

    } catch (IOException e) {
      throw MapperException.cannotDeserializeJson(
          "Error while retrieving following content %s to deserialize into the following PromotionAccountDeleteVo",
          baseMessage);
    }
  }
}
