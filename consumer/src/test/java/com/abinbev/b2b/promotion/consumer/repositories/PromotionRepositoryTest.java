package com.abinbev.b2b.promotion.consumer.repositories;

import static com.abinbev.b2b.promotion.consumer.helper.PromotionMocks.GENERIC_PROMOTION_COLLECTION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.consumer.assertion.PromotionModelAssertion;
import com.abinbev.b2b.promotion.consumer.config.properties.DatabaseCollectionProperties;
import com.abinbev.b2b.promotion.consumer.config.properties.PromotionSplitProperties;
import com.abinbev.b2b.promotion.consumer.domain.PromotionModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionMultiVendorModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionType;
import com.abinbev.b2b.promotion.consumer.helper.BulkOperationExceptionHelper;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMocks;
import com.abinbev.b2b.promotion.consumer.repository.PromotionRepository;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountMultiVendorDeleteVO;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PromotionRepositoryTest {

  @InjectMocks private PromotionRepository promotionRepository;

  @Mock private MongoOperations mongoOperations;

  @Mock private BulkOperations bulkOperations;

  @Mock private DatabaseCollectionProperties databaseCollectionProperties;

  @Mock private PromotionSplitProperties promotionSplitProperties;

  private static final String COLLECTION_NAME = "DO-Promotions";
  private static final String OR_CRITERIA = "$or";

  private static final String DO_COUNTRY = "DO";
  private static final String DO_COUNTRY_BASE = DO_COUNTRY + GENERIC_PROMOTION_COLLECTION;

  @BeforeEach
  public void setup() {
    Mockito.when(databaseCollectionProperties.getPromotionCollectionByCountry(anyString()))
        .thenReturn(DO_COUNTRY_BASE);

    when(mongoOperations.bulkOps(
            BulkOperations.BulkMode.UNORDERED, PromotionModel.class, COLLECTION_NAME))
        .thenReturn(bulkOperations);
    when(promotionSplitProperties.getPromotionSplit()).thenReturn(2);
  }

  @Test
  public void testBulkInsert() {
    // init
    List<PromotionModel> promotionModels =
        Arrays.asList(
            PromotionMocks.getPromotionModel(),
            PromotionMocks.getPromotionModel(),
            PromotionMocks.getPromotionModel());
    int promotionsSize = promotionModels.size();

    // act
    promotionRepository.insertBulk(
        promotionModels, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);

    // verify
    verify(bulkOperations, times(promotionsSize)).upsert(any(Query.class), any(Update.class));
  }

  @Test
  public void testBulkInsertMustCatchDuplicateKeyException() {
    // init
    PromotionModel promotionModelOne = PromotionMocks.getPromotionModel();
    PromotionModel promotionModelTwo = PromotionMocks.getPromotionModel();
    List<PromotionModel> promotionModels = Arrays.asList(promotionModelOne, promotionModelTwo);
    int promotionsSize = promotionModels.size();

    when(bulkOperations.execute())
        .thenThrow(BulkOperationExceptionHelper.getBulkOperationException());

    // act
    promotionRepository.insertBulk(
        promotionModels, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);

    // verify
    verify(bulkOperations, times(promotionsSize)).upsert(any(Query.class), any(Update.class));
  }

  @Test
  public void testFindByPromotionIds() {
    PromotionModel promotionModel = PromotionMocks.getPromotionModel();
    List<PromotionModel> promotionListMocked = List.of(promotionModel);

    List<String> ids = Arrays.asList("182", "183");

    when(mongoOperations.find(any(Query.class), eq(PromotionModel.class), eq(COLLECTION_NAME)))
        .thenReturn(promotionListMocked);

    List<PromotionModel> result =
        promotionRepository.findByPromotionIds(ids, PromotionMocks.COUNTRY);

    Assertions.assertEquals(promotionListMocked.size(), result.size());
    PromotionModelAssertion.assertEquals(promotionListMocked, result);
  }

  @Test
  public void testSoftDeleteWithoutDealId() {

    ArgumentCaptor<Query> queryArgumentCaptor = ArgumentCaptor.forClass(Query.class);
    ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);

    promotionRepository.softDeletePromotion(
        PromotionMocks.getPromotionsAccountDeleteVoMock(),
        PromotionMocks.TIMESTAMP,
        PromotionMocks.COUNTRY);

    Mockito.verify(bulkOperations, Mockito.times(3))
        .updateMulti(queryArgumentCaptor.capture(), updateArgumentCaptor.capture());

    Mockito.verify(bulkOperations, Mockito.times(2)).execute();
  }

  @Test
  public void testSoftDeleteWithoutDealIdExecuteBulkOnce() {

    when(promotionSplitProperties.getPromotionSplit()).thenReturn(3);

    ArgumentCaptor<Query> queryArgumentCaptor = ArgumentCaptor.forClass(Query.class);
    ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);

    promotionRepository.softDeletePromotion(
        PromotionMocks.getPromotionsAccountDeleteVoMock(),
        PromotionMocks.TIMESTAMP,
        PromotionMocks.COUNTRY);

    Mockito.verify(bulkOperations, Mockito.times(3))
        .updateMulti(queryArgumentCaptor.capture(), updateArgumentCaptor.capture());

    Mockito.verify(bulkOperations, Mockito.times(1)).execute();
  }

  @Test
  public void testBulkMultiVendorInsert() {
    // init
    PromotionMultiVendorModel.Builder builder =
        PromotionMultiVendorModel.newBuilder()
            .withTitle(PromotionMocks.TITLE)
            .withDescription(PromotionMocks.DESCRIPTION)
            .withStartDate(OffsetDateTime.parse(PromotionMocks.START_DATE))
            .withEndDate(OffsetDateTime.parse(PromotionMocks.END_DATE))
            .withBudget(PromotionMocks.BUDGET)
            .withPromotionType(PromotionType.DISCOUNT)
            .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
            .withImage(PromotionMocks.IMAGE)
            .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT);

    PromotionMultiVendorModel promotionModelOne = builder.build();
    PromotionMultiVendorModel promotionModelTwo = builder.build();
    PromotionMultiVendorModel promotionModelThree = builder.build();

    List<PromotionMultiVendorModel> promotionModels =
        Arrays.asList(promotionModelOne, promotionModelTwo, promotionModelThree);
    int promotionsSize = promotionModels.size();

    // act
    promotionRepository.insertMultiVendorBulk(
        promotionModels, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);

    // verify
    verify(bulkOperations, times(promotionsSize)).upsert(any(Query.class), any(Update.class));
  }

  @Test
  public void testBulkMultiVendorInsertMustCatchDuplicateKeyException() {
    // init
    PromotionMultiVendorModel.Builder builder =
        PromotionMultiVendorModel.newBuilder()
            .withTitle(PromotionMocks.TITLE)
            .withDescription(PromotionMocks.DESCRIPTION)
            .withStartDate(OffsetDateTime.parse(PromotionMocks.START_DATE))
            .withEndDate(OffsetDateTime.parse(PromotionMocks.END_DATE))
            .withBudget(PromotionMocks.BUDGET)
            .withPromotionType(PromotionType.DISCOUNT)
            .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
            .withImage(PromotionMocks.IMAGE)
            .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT);

    PromotionMultiVendorModel promotionModelOne = builder.build();
    PromotionMultiVendorModel promotionModelTwo = builder.build();

    when(bulkOperations.execute())
        .thenThrow(BulkOperationExceptionHelper.getBulkOperationException());

    List<PromotionMultiVendorModel> promotionModels =
        Arrays.asList(promotionModelOne, promotionModelTwo);

    // act
    promotionRepository.insertMultiVendorBulk(
        promotionModels, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);
  }

  @Test
  public void testSoftDeleteMultiVendor() {

    ArgumentCaptor<String> collectionNameArgumentCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Query> queryArgumentCaptor = ArgumentCaptor.forClass(Query.class);
    ArgumentCaptor<Update> updateArgumentCaptor = ArgumentCaptor.forClass(Update.class);

    final PromotionAccountMultiVendorDeleteVO promotionAccountMultiVendorDeleteVO =
        new PromotionAccountMultiVendorDeleteVO();
    promotionAccountMultiVendorDeleteVO.setVendorPromotionIds(
        Collections.singletonList(PromotionMocks.VENDOR_PROMOTION_ID));
    promotionAccountMultiVendorDeleteVO.setVendorAccountIds(
        Collections.singletonList(PromotionMocks.ACCOUNT_ID));

    promotionRepository.softDeleteMultiVendor(
        promotionAccountMultiVendorDeleteVO,
        PromotionMocks.VENDOR_ID,
        PromotionMocks.TIMESTAMP,
        PromotionMocks.COUNTRY);

    Mockito.verify(mongoOperations, Mockito.times(1))
        .updateMulti(
            queryArgumentCaptor.capture(),
            updateArgumentCaptor.capture(),
            collectionNameArgumentCaptor.capture());

    Assertions.assertNull(queryArgumentCaptor.getValue().getQueryObject().get(OR_CRITERIA));
  }

  @Test
  public void testFindByVendorPromotionIds() {
    PromotionMultiVendorModel promotionMultiVendorModel =
        PromotionMocks.getPromotionMultiVendorModel();
    List<PromotionMultiVendorModel> promotionListMocked = List.of(promotionMultiVendorModel);

    List<String> ids = Arrays.asList("182", "183");

    when(mongoOperations.find(
            any(Query.class), eq(PromotionMultiVendorModel.class), eq(COLLECTION_NAME)))
        .thenReturn(promotionListMocked);

    List<PromotionMultiVendorModel> result =
        promotionRepository.findByVendorPromotionIds(
            ids, PromotionMocks.COUNTRY, PromotionMocks.VENDOR_ID_001);

    Assertions.assertEquals(promotionListMocked.size(), result.size());
    Assertions.assertEquals(result, promotionListMocked);
  }
}
