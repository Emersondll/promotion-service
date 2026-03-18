package com.abinbev.b2b.promotion.config;

import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public abstract class BaseIntegrationTest {

  protected static final String PAGE_PARAM = "page";
  protected static final String PAGE_SIZE_PARAM = "pageSize";

  protected static final Logger LOGGER = LoggerFactory.getLogger(BaseIntegrationTest.class);

  @Autowired private MongoOperations mongoOperations;

  protected void cleanCollections() {
    mongoOperations.dropCollection(PromotionMocks.US_PROMOTION_COLLECTION);
    mongoOperations.createCollection(PromotionMocks.US_PROMOTION_COLLECTION);
  }

  protected void saveUSMultiVendorPromotion(final PromotionMultivendor promotion) {
    var created = mongoOperations.save(promotion, PromotionMocks.US_PROMOTION_COLLECTION);
    mongoOperations.updateMulti(
        new Query(), Update.update("deleted", false), PromotionMocks.US_PROMOTION_COLLECTION);
    LOGGER.info("Promotion {} created", created);
  }
}
