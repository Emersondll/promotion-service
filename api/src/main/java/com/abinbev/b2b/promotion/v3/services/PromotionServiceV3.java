package com.abinbev.b2b.promotion.v3.services;

import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v3.helpers.PromotionMarketplaceMapper;
import com.abinbev.b2b.promotion.v3.repositories.PromotionRepositoryV3;
import com.abinbev.b2b.promotion.v3.vo.Paged;
import com.abinbev.b2b.promotion.v3.vo.PromotionFilterV3;
import com.abinbev.b2b.promotion.v3.vo.PromotionMarketplaceResponse;
import com.newrelic.api.agent.Trace;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceV3 {

  private final PromotionRepositoryV3 repository;

  public PromotionServiceV3(PromotionRepositoryV3 repository) {
    this.repository = repository;
  }

  @Trace
  public PromotionMarketplaceResponse getPromotions(
      final RequestContext context, final PromotionFilterV3 filters, final Pageable pageable) {

    final Paged<PromotionMultivendor> page =
        repository.getPagedPromotion(context, filters, pageable);

    return new PromotionMarketplaceResponse(
        page.getContent().stream()
            .filter(Objects::nonNull)
            .map(
                promotionMultivendor ->
                    PromotionMarketplaceMapper.map(
                        promotionMultivendor, context.getAcceptLanguage()))
            .collect(Collectors.toList()),
        page.getPagination());
  }
}
