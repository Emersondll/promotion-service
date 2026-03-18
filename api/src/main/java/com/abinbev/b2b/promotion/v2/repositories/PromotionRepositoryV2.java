package com.abinbev.b2b.promotion.v2.repositories;

import com.abinbev.b2b.promotion.properties.DatabaseCollectionProperties;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.helpers.PromotionQueryHelper;
import com.abinbev.b2b.promotion.v2.vo.PromotionFilterV2;
import com.newrelic.api.agent.Trace;
import java.util.List;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionRepositoryV2 {

  private final MongoOperations mongoOperations;
  private final DatabaseCollectionProperties databaseCollectionProperties;

  public PromotionRepositoryV2(
      MongoOperations mongoOperations, DatabaseCollectionProperties databaseCollectionProperties) {
    this.mongoOperations = mongoOperations;
    this.databaseCollectionProperties = databaseCollectionProperties;
  }

  @Trace
  public List<PromotionMultivendor> findPromotionsByFilters(
      RequestContext requestContext, PromotionFilterV2 promotionFilterV2) {

    final String collectionName = getCollection(requestContext.getCountry().toUpperCase());
    final Long maxQueryTimeout = getMaxQueryTimeout();

    final List<PromotionMultivendor> promotionsMultiVendor;

    promotionsMultiVendor =
        mongoOperations.find(
            PromotionQueryHelper.promotionQueryBuilder(promotionFilterV2, maxQueryTimeout),
            PromotionMultivendor.class,
            collectionName);

    return promotionsMultiVendor;
  }

  private String getCollection(final String country) {
    return databaseCollectionProperties.getBase(country);
  }

  private Long getMaxQueryTimeout() {
    return databaseCollectionProperties.getMaxQueryTimeout();
  }
}
