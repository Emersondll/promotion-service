package com.abinbev.b2b.promotion.consumer.repository;

import com.abinbev.b2b.promotion.consumer.config.properties.DatabaseCollectionProperties;
import org.springframework.data.mongodb.core.MongoOperations;

public class BaseRepository {

  protected final MongoOperations mongoOperations;
  protected final DatabaseCollectionProperties databaseCollectionProperties;

  public BaseRepository(
      final DatabaseCollectionProperties databaseCollectionProperties,
      MongoOperations mongoOperations) {
    this.databaseCollectionProperties = databaseCollectionProperties;
    this.mongoOperations = mongoOperations;
  }

  protected MongoOperations getMongoOperations() {
    return mongoOperations;
  }

  protected String getPromotionCollection(final String country) {
    return databaseCollectionProperties.getPromotionCollectionByCountry(country);
  }
}
