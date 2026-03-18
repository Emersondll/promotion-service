package com.abinbev.b2b.promotion.v2.helpers;

import com.abinbev.b2b.promotion.v2.vo.PromotionFilterV2;
import io.micrometer.core.instrument.util.StringUtils;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;

public final class PromotionQueryHelper {

  public static final String PROMOTION_END_DATE_FIELD_NAME = "endDate";
  public static final String PROMOTION_START_DATE_FIELD_NAME = "startDate";
  public static final String PROMOTION_TYPE_FIELD_NAME = "promotionType";
  public static final String VENDOR_ID = "vendorId";
  public static final String VENDOR_PROMOTION_ID = "vendorPromotionId";
  public static final String PROMOTION_ID = "_id";
  public static final String DELETED = "deleted";

  private PromotionQueryHelper() {}

  public static Query promotionQueryBuilder(
      PromotionFilterV2 promotionFilterV2, Long maxQueryTimeout) {

    final Query promotionQuery =
        new Query()
            .addCriteria(
                Criteria.where(PROMOTION_END_DATE_FIELD_NAME)
                    .gte(OffsetDateTime.now(ZoneOffset.UTC)))
            .addCriteria(new Criteria().and(DELETED).is(Boolean.FALSE));

    if (!promotionFilterV2.isIgnoreStartDate()) {
      promotionQuery.addCriteria(
          new Criteria()
              .and(PROMOTION_START_DATE_FIELD_NAME)
              .lte(OffsetDateTime.now(ZoneOffset.UTC)));
    }

    if (!Objects.isNull(promotionFilterV2.getPromotionIds())
        && !promotionFilterV2.getPromotionIds().isEmpty()) {
      promotionQuery.addCriteria(
          new Criteria().and(PROMOTION_ID).in(promotionFilterV2.getPromotionIds()));
    }

    if (!Objects.isNull(promotionFilterV2.getTypes()) && !promotionFilterV2.getTypes().isEmpty()) {
      promotionQuery.addCriteria(
          new Criteria().and(PROMOTION_TYPE_FIELD_NAME).in(promotionFilterV2.getTypes()));
    }

    if (StringUtils.isNotBlank(promotionFilterV2.getQuery())) {
      promotionQuery.addCriteria(
          TextCriteria.forDefaultLanguage().matchingPhrase(promotionFilterV2.getQuery()));
    }

    if (StringUtils.isNotBlank(promotionFilterV2.getVendorId())) {
      promotionQuery.addCriteria(new Criteria().and(VENDOR_ID).is(promotionFilterV2.getVendorId()));
    }

    if (!Objects.isNull(promotionFilterV2.getVendorPromotionIds())
        && !promotionFilterV2.getVendorPromotionIds().isEmpty()) {
      promotionQuery.addCriteria(
          new Criteria().and(VENDOR_PROMOTION_ID).in(promotionFilterV2.getVendorPromotionIds()));
    }

    promotionQuery.with(
        PageRequest.of(
            promotionFilterV2.getPagination().getPage(),
            promotionFilterV2.getPagination().getSize()));

    promotionQuery.maxTimeMsec(maxQueryTimeout);

    return promotionQuery;
  }
}
