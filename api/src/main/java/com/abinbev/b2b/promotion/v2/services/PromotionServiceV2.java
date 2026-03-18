package com.abinbev.b2b.promotion.v2.services;

import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.mapper.PromotionMapperBuilder;
import com.abinbev.b2b.promotion.v2.repositories.PromotionRepositoryV2;
import com.abinbev.b2b.promotion.v2.rest.vo.GetPromotionsVO;
import com.abinbev.b2b.promotion.v2.rest.vo.PromotionResponse;
import com.abinbev.b2b.promotion.v2.vo.PromotionFilterV2;
import com.newrelic.api.agent.Trace;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceV2 {

  private final PromotionRepositoryV2 promotionRepositoryV2;
  private final PromotionMapperBuilder mapper;

  @Autowired
  public PromotionServiceV2(
      PromotionRepositoryV2 promotionRepositoryV2, PromotionMapperBuilder mapper) {

    this.promotionRepositoryV2 = promotionRepositoryV2;
    this.mapper = mapper;
  }

  @Trace
  public GetPromotionsVO getPromotions(
      RequestContext requestContext, PromotionFilterV2 promotionFilterV2) {

    List<PromotionMultivendor> promotions =
        promotionRepositoryV2.findPromotionsByFilters(requestContext, promotionFilterV2);

    List<PromotionResponse> promotionsResponse =
        promotions.stream()
            .map(
                p ->
                    mapper.mapToMultiVendorResponseForPromotion(
                        p, requestContext.getAcceptLanguage()))
            .collect(Collectors.toList());
    return new GetPromotionsVO(promotionsResponse, promotionFilterV2.getPagination());
  }
}
