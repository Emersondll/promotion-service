package com.abinbev.b2b.promotion.v3.repositories;

import com.abinbev.b2b.promotion.properties.DatabaseCollectionProperties;
import com.abinbev.b2b.promotion.v3.vo.Paged;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;

public abstract class BaseRepository {
  protected final MongoOperations mongoOperations;
  protected final DatabaseCollectionProperties databaseCollectionProperties;

  public BaseRepository(
      final MongoOperations mongoOperations,
      final DatabaseCollectionProperties databaseCollectionProperties) {
    this.mongoOperations = mongoOperations;
    this.databaseCollectionProperties = databaseCollectionProperties;
  }

  protected Long getMaxQueryTimeout() {
    return databaseCollectionProperties.getMaxQueryTimeout();
  }

  protected abstract String getCollection(final String country);

  /**
   * Get paged method without use the count operation.
   *
   * <p>This method was created to be used instead of common pagination method. Needed because the
   * count operation show slowness in some cases
   *
   * <p>The search will be made using the pagination +1 in page size, to check if are more registers
   * to find. With this solution we will only have the page number and hasNext property from the
   * Page object.
   *
   * @param query query object
   * @param pageable page request
   * @param clazz class type to be returned
   * @param collection collection to search
   * @return paginated response with the typed pass in clazz param
   */
  protected <T> Paged<T> getPaged(
      final Query query, final Pageable pageable, final Class<T> clazz, final String collection) {
    query.skip(pageable.getOffset());
    query.limit(pageable.getPageSize() + 1);
    final List<T> result = mongoOperations.find(query, clazz, collection);
    if (CollectionUtils.isEmpty(result)) {
      return new Paged<>(pageable.getPageNumber());
    }
    return new Paged<>(
        result.stream().limit(pageable.getPageSize()).collect(Collectors.toList()),
        result.size() > pageable.getPageSize(),
        pageable.getPageNumber());
  }
}
