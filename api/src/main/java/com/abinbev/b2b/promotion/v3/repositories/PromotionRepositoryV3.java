package com.abinbev.b2b.promotion.v3.repositories;

import com.abinbev.b2b.promotion.properties.DatabaseCollectionProperties;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v3.helpers.PromotionQueryHelper;
import com.abinbev.b2b.promotion.v3.vo.Paged;
import com.abinbev.b2b.promotion.v3.vo.PromotionFilterV3;
import com.newrelic.api.agent.Trace;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionRepositoryV3 extends BaseRepository {

  public PromotionRepositoryV3(
      MongoOperations mongoOperations, DatabaseCollectionProperties databaseCollectionProperties) {
    super(mongoOperations, databaseCollectionProperties);
  }

  @Trace
  public Paged<PromotionMultivendor> getPagedPromotion(
      final RequestContext context, final PromotionFilterV3 filters, final Pageable pageable) {

    final Query query = PromotionQueryHelper.byFilters(filters);
    query.maxTimeMsec(getMaxQueryTimeout());
    return getPaged(
        query,
        pageable,
        PromotionMultivendor.class,
        getCollection(context.getCountry().toUpperCase()));
  }

  protected String getCollection(final String country) {
    return databaseCollectionProperties.getBase(country);
  }
}
