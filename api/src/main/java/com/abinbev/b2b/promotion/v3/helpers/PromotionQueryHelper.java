package com.abinbev.b2b.promotion.v3.helpers;

import com.abinbev.b2b.promotion.v3.vo.PromotionFilterV3;
import io.micrometer.core.instrument.util.StringUtils;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.util.CollectionUtils;

public final class PromotionQueryHelper {

  public static final String END_DATE_FIELD_NAME = "endDate";
  public static final String START_DATE_FIELD_NAME = "startDate";
  public static final String TYPE_FIELD_NAME = "promotionType";
  public static final String VENDOR_ID_FIELD_NAME = "vendorId";
  public static final String PROMOTION_PLATFORM_ID_FIELD_NAME = "promotionPlatformId";
  public static final String DELETED_FIELD_NAME = "deleted";

  private PromotionQueryHelper() {}

  public static Query byFilters(final PromotionFilterV3 filter) {
    final Query query = new Query();

    if (!CollectionUtils.isEmpty(filter.getVendorIds())) {
      query.addCriteria(new Criteria().and(VENDOR_ID_FIELD_NAME).in(filter.getVendorIds()));
    }

    if (!CollectionUtils.isEmpty(filter.getPromotionPlatformIds())) {
      query.addCriteria(
          new Criteria()
              .and(PROMOTION_PLATFORM_ID_FIELD_NAME)
              .in(filter.getPromotionPlatformIds()));
    }

    query.addCriteria(Criteria.where(END_DATE_FIELD_NAME).gte(OffsetDateTime.now(ZoneOffset.UTC)));

    query.addCriteria(new Criteria().and(DELETED_FIELD_NAME).is(Boolean.FALSE));

    if (!filter.isIgnoreStartDate()) {
      query.addCriteria(
          new Criteria().and(START_DATE_FIELD_NAME).lte(OffsetDateTime.now(ZoneOffset.UTC)));
    }

    if (!CollectionUtils.isEmpty(filter.getTypes())) {
      query.addCriteria(new Criteria().and(TYPE_FIELD_NAME).in(filter.getTypes()));
    }

    if (StringUtils.isNotBlank(filter.getQuery())) {
      query.addCriteria(TextCriteria.forDefaultLanguage().matchingPhrase(filter.getQuery()));
    }

    return query;
  }
}
