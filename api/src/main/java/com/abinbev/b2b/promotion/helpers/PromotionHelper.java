package com.abinbev.b2b.promotion.helpers;

import com.abinbev.b2b.promotion.rest.vo.PromotionResponse;
import com.abinbev.b2b.promotion.v2.domain.model.Promotion;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class PromotionHelper {

  private PromotionHelper() {}

  public static List<PromotionResponse> promotionsModelToPromotionsResponse(
      final List<Promotion> promotions, final String accountId) {
    final List<PromotionResponse> promotionsResponse = new ArrayList<>();

    for (Promotion promotion : promotions) {
      promotionsResponse.add(promotionModelToPromotionResponse(promotion, accountId, null));
    }

    return promotionsResponse;
  }

  public static PromotionResponse promotionModelToPromotionResponse(
      final Promotion promotion, final String accountId, final Set<String> skus) {
    final BigDecimal score = accountId == null ? null : promotion.getScore().get(accountId);
    return PromotionResponse.newBuilder()
        .withId(promotion.getId())
        .withTitle(promotion.getTitle())
        .withType(promotion.getPromotionType().name())
        .withDescription(promotion.getDescription())
        .withSkus(Optional.ofNullable(skus).orElse(Collections.emptySet()))
        .withPromotionsRanking(promotion.getPromotionsRanking())
        .withScore(Optional.ofNullable(score).orElse(BigDecimal.ZERO))
        .withBudget(promotion.getBudget())
        .withPromotionId(promotion.getPromotionId())
        .withInternalId(promotion.getId())
        .withQuantityLimit(promotion.getQuantityLimit())
        .withUpdatedAt(
            Optional.ofNullable(promotion.getUpdateAt()).map(OffsetDateTime::toString).orElse(null))
        .withEndDate(
            Optional.ofNullable(promotion.getEndDate()).map(OffsetDateTime::toString).orElse(null))
        .build();
  }
}
