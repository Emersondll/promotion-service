package com.abinbev.b2b.promotion.consumer.service;

import com.abinbev.b2b.promotion.consumer.domain.PromotionModel;
import com.abinbev.b2b.promotion.consumer.helper.PromotionAccountMapperHelper;
import com.abinbev.b2b.promotion.consumer.helper.PromotionHelper;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import com.abinbev.b2b.promotion.consumer.repository.PromotionRepository;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountDeleteVo;
import com.abinbev.b2b.promotion.consumer.vo.PromotionListVo;
import com.newrelic.api.agent.Trace;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class PromotionService {

  private final PromotionRepository promotionRepository;

  @Autowired
  public PromotionService(final PromotionRepository promotionRepository) {

    this.promotionRepository = promotionRepository;
  }

  @Trace
  public void createPromotions(
          final PromotionListVo promotionListVo, final String country, final Long lastModified) {

    if (promotionListVo != null && promotionListVo.getPromotionVos() != null) {

      promotionRepository.insertBulk(
              mapPromotionListVoToPromotionDomainList(promotionListVo), country, lastModified);
    }
  }

  @Trace
  public void deletePromotion(@SuppressWarnings("rawtypes") BaseMessage baseMessage) {

    final PromotionAccountDeleteVo promotionAccountDeleteVo =
            PromotionAccountMapperHelper.convertBaseMessageToPromotionAccountDelete(baseMessage);

    if (CollectionUtils.isEmpty(promotionAccountDeleteVo.getAccounts())) {
      promotionRepository.softDeletePromotion(
              promotionAccountDeleteVo, baseMessage.getTimestamp(), baseMessage.getCountry());
    }
  }

  private List<PromotionModel> mapPromotionListVoToPromotionDomainList(
          PromotionListVo promotionListVo) {

    List<PromotionModel> promotionModels = new ArrayList<>();
    promotionListVo
            .getPromotionVos()
            .forEach(
                    promotionAccountVo ->
                            promotionModels.add(PromotionHelper.mapVoToDomain(promotionAccountVo)));

    return promotionModels.stream().distinct().collect(Collectors.toList());
  }
}
