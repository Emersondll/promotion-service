package com.abinbev.b2b.promotion.consumer.repository;

import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.BUDGET;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.CREATED_AT;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.DEFAULT_LANGUAGE;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.DELETED;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.DELETED_AT;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.DESCRIPTION;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.END_DATE;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.ID;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.IMAGE;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.PROMOTION_ID;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.PROMOTION_PLATFORM_ID;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.PROMOTION_TYPE;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.QUANTITY_LIMIT;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.START_DATE;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.TITLE;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.TRANSLATIONS;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.UPDATE_AT;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.VENDOR_ID;
import static com.abinbev.b2b.promotion.consumer.constant.DatabaseKey.VENDOR_PROMOTION_ID;

import com.abinbev.b2b.promotion.consumer.config.properties.DatabaseCollectionProperties;
import com.abinbev.b2b.promotion.consumer.config.properties.PromotionSplitProperties;
import com.abinbev.b2b.promotion.consumer.domain.PromotionBaseModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionMultiVendorModel;
import com.abinbev.b2b.promotion.consumer.helper.DuplicateKeyHelper;
import com.abinbev.b2b.promotion.consumer.helper.OffsetDateTimeHelper;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountDeleteVo;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountMultiVendorDeleteVO;
import com.newrelic.api.agent.Trace;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.BulkOperationException;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionRepository extends BaseRepository {

  private final PromotionSplitProperties promotionProperties;

  @Autowired
  public PromotionRepository(
      DatabaseCollectionProperties databaseCollectionProperties,
      MongoOperations mongoOperations,
      PromotionSplitProperties promotionProperties) {
    super(databaseCollectionProperties, mongoOperations);
    this.promotionProperties = promotionProperties;
  }

  @Trace
  public void insertBulk(
      final List<PromotionModel> promotionModelList, final String country, final Long timestamp) {
    final String collectionName = getPromotionCollection(country.toUpperCase());

    BulkOperations bulkOperations =
        getMongoOperations()
            .bulkOps(BulkOperations.BulkMode.UNORDERED, PromotionModel.class, collectionName);

    final OffsetDateTime updatedAt = OffsetDateTimeHelper.fromTimestamp(timestamp);

    int count = 0;
    for (final PromotionModel promotionModel : promotionModelList) {
      final Update update = createInsertBulkUpdate(promotionModel, updatedAt);
      final Query query =
          createPromotionAccountAndTimestampBulkQuery(promotionModel.getId(), updatedAt);
      bulkOperations.upsert(query, update);
      if (++count == promotionProperties.getPromotionSplit()) {
        count = 0;
        executeBulkOperations(bulkOperations);
      }
    }

    if (count > 0) {
      executeBulkOperations(bulkOperations);
    }
  }

  private void executeBulkOperations(final BulkOperations bulkOperations) {
    try {
      bulkOperations.execute();
    } catch (final BulkOperationException e) {
      DuplicateKeyHelper.validateDuplicateKeyException(e);
    }
  }

  private Update createInsertBulkUpdate(
      final PromotionModel promotion, final OffsetDateTime updatedAt) {
    final Update update = new Update();

    update.set(PROMOTION_ID, promotion.getPromotionId());
    fillPromotionData(promotion, updatedAt, update);
    return update;
  }

  @Trace
  public void insertMultiVendorBulk(
      final List<PromotionMultiVendorModel> promotionMultiVendorModelList,
      final String country,
      final Long timestamp) {
    final String collectionName = getPromotionCollection(country.toUpperCase());

    final BulkOperations bulkOperations =
        getMongoOperations()
            .bulkOps(BulkOperations.BulkMode.UNORDERED, PromotionModel.class, collectionName);

    final OffsetDateTime updatedAt = OffsetDateTimeHelper.fromTimestamp(timestamp);

    int count = 0;

    for (final PromotionMultiVendorModel promotionMultiVendorModel :
        promotionMultiVendorModelList) {
      final Update update = createInsertBulkUpdateMultiVendor(promotionMultiVendorModel, updatedAt);
      final Query query =
          byVendorPromotionIdVendorIdAndUpdateAt(
              promotionMultiVendorModel.getVendorPromotionId(),
              promotionMultiVendorModel.getVendorId(),
              updatedAt);
      bulkOperations.upsert(query, update);

      if (++count == promotionProperties.getPromotionSplit()) {
        count = 0;
        executeBulkOperations(bulkOperations);
      }
    }

    if (count > 0) {
      executeBulkOperations(bulkOperations);
    }
  }

  @Trace
  protected Query byVendorPromotionIdVendorIdAndUpdateAt(
      final String vendorPromotionId, final String vendorId, final OffsetDateTime updatedAt) {
    final Criteria vendorPromotionIdEqualsVendorPromotionId =
        Criteria.where(VENDOR_PROMOTION_ID).is(vendorPromotionId);
    final Criteria vendorIdEqualsVendorId = Criteria.where(VENDOR_ID).is(vendorId);
    final Criteria updatedAtLte = Criteria.where(UPDATE_AT).lte(updatedAt);
    return new Query()
        .addCriteria(vendorPromotionIdEqualsVendorPromotionId)
        .addCriteria(vendorIdEqualsVendorId)
        .addCriteria(updatedAtLte);
  }

  private Update createInsertBulkUpdateMultiVendor(
      final PromotionMultiVendorModel promotion, final OffsetDateTime updatedAt) {
    final Update update = new Update();
    update.setOnInsert(CREATED_AT, OffsetDateTime.now());
    update.set(VENDOR_PROMOTION_ID, promotion.getVendorPromotionId());
    update.set(VENDOR_ID, promotion.getVendorId());
    update.set(PROMOTION_PLATFORM_ID, promotion.getPromotionPlatformId());
    update.set(TRANSLATIONS, promotion.getTranslations());
    update.set(DEFAULT_LANGUAGE, promotion.getDefaultLanguage());
    update.unset(DELETED_AT);
    fillPromotionData(promotion, updatedAt, update);
    return update;
  }

  private void fillPromotionData(
      PromotionBaseModel promotion, OffsetDateTime updatedAt, Update update) {
    update.set(PROMOTION_TYPE, promotion.getPromotionType());
    update.set(TITLE, promotion.getTitle());
    update.set(DESCRIPTION, promotion.getDescription());
    update.set(BUDGET, promotion.getBudget());
    update.set(QUANTITY_LIMIT, promotion.getQuantityLimit());
    update.set(START_DATE, promotion.getStartDate());
    update.set(END_DATE, promotion.getEndDate());
    update.set(UPDATE_AT, updatedAt);
    update.set(IMAGE, promotion.getImage());
    update.set(DELETED, promotion.getDeleted());
  }

  @Trace
  public void softDeletePromotion(
      PromotionAccountDeleteVo promotionAccountDeleteVo, Long timestamp, String country) {

    final String collectionName = getPromotionCollection(country.toUpperCase());
    final OffsetDateTime updatedAt = OffsetDateTimeHelper.fromTimestamp(timestamp);

    final BulkOperations bulkOperations =
        getMongoOperations()
            .bulkOps(BulkOperations.BulkMode.UNORDERED, PromotionModel.class, collectionName);

    int count = 0;
    for (final String promotion : promotionAccountDeleteVo.getPromotions()) {
      final Query query = deleteQuery(updatedAt, promotion);
      final Update update = deleteUpdate(updatedAt);
      bulkOperations.updateMulti(query, update);

      if (++count == promotionProperties.getPromotionSplit()) {
        count = 0;
        bulkOperations.execute();
      }
    }

    if (count > 0) {
      bulkOperations.execute();
    }
  }

  @Trace
  protected Query createPromotionAccountAndTimestampBulkQuery(String id, OffsetDateTime updatedAt) {
    Criteria promotionEqualsId = Criteria.where(ID).is(id);
    Criteria updatedAtLte = Criteria.where(UPDATE_AT).lte(updatedAt);
    return new Query(promotionEqualsId.andOperator(updatedAtLte));
  }

  @Trace
  public List<PromotionModel> findByPromotionIds(List<String> promotionIds, String country) {
    final String collectionName = getPromotionCollection(country.toUpperCase());
    Query query = new Query().addCriteria(Criteria.where(ID).in(promotionIds));
    return getMongoOperations().find(query, PromotionModel.class, collectionName);
  }

  @Trace
  public List<PromotionMultiVendorModel> findByVendorPromotionIds(
      List<String> promotionIds, String country, String vendorId) {
    final String collectionName = getPromotionCollection(country.toUpperCase());
    Query query = new Query().addCriteria(Criteria.where(VENDOR_PROMOTION_ID).in(promotionIds));
    query.addCriteria(Criteria.where(VENDOR_ID).is(vendorId));
    return getMongoOperations().find(query, PromotionMultiVendorModel.class, collectionName);
  }

  private Query deleteQuery(final OffsetDateTime updatedAt, final String promotion) {
    final Criteria promotionIdEquals = Criteria.where(PROMOTION_ID).is(promotion);
    final Criteria updateAtLessThan = Criteria.where(UPDATE_AT).lt(updatedAt);
    return new Query(promotionIdEquals.andOperator(updateAtLessThan));
  }

  private Update deleteUpdate(OffsetDateTime updatedAt) {
    Update update = new Update();
    update.set(DELETED, Boolean.TRUE);
    update.set(UPDATE_AT, updatedAt);
    return update;
  }

  @Trace
  public void softDeleteMultiVendor(
      final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO,
      final String vendorId,
      final Long timestamp,
      final String country) {
    final String collectionName = getPromotionCollection(country.toUpperCase());
    final OffsetDateTime offsetDateTime = OffsetDateTimeHelper.fromTimestamp(timestamp);
    final Query query =
        createDeleteVendorQuery(
            offsetDateTime, vendorId, promotionAccountMultiVendorDeleteVO.getVendorPromotionIds());
    final Update update = createDeleteVendorQuery(offsetDateTime);
    getMongoOperations().updateMulti(query, update, collectionName);
  }

  private Query createDeleteVendorQuery(
      final OffsetDateTime offsetDateTime, final String vendorId, final List<String> promotions) {
    final Query query = new Query();
    query.addCriteria(Criteria.where(VENDOR_PROMOTION_ID).in(promotions));
    query.addCriteria(Criteria.where(VENDOR_ID).is(vendorId));
    query.addCriteria(Criteria.where(UPDATE_AT).lt(offsetDateTime));
    return query;
  }

  private Update createDeleteVendorQuery(final OffsetDateTime timestamp) {
    final Update update = new Update();
    update.set(DELETED, Boolean.TRUE);
    update.set(UPDATE_AT, timestamp);
    update.set(DELETED_AT, timestamp);
    return update;
  }
}
