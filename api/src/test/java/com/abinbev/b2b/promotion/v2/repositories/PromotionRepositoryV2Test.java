package com.abinbev.b2b.promotion.v2.repositories;

import static com.abinbev.b2b.promotion.helpers.PromotionMocks.COUNTRY_BR;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.COUNTRY_US;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.GENERIC_PROMOTION_COLLECTION;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.PROMOTION_ID;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.VENDOR_ID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.properties.DatabaseCollectionProperties;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.vo.PromotionFilterV2;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromotionRepositoryV2Test {

  @InjectMocks private PromotionRepositoryV2 promotionRepositoryV2;
  @Mock private MongoOperations mongoOperations;
  @Mock private DatabaseCollectionProperties databaseCollectionProperties;

  private static final String COUNTRY_BASE_BR = COUNTRY_BR + GENERIC_PROMOTION_COLLECTION;
  private static final String COUNTRY_BASE_US = COUNTRY_US + GENERIC_PROMOTION_COLLECTION;

  Pagination pagination;

  @Test
  public void testFindPromotionsByFiltersSingleVendor() {

    pagination = new Pagination(0, 50);

    List<PromotionMultivendor> promotions =
        Arrays.asList(PromotionMocks.getPromotionsMultiVendor());

    when(databaseCollectionProperties.getBase(anyString())).thenReturn(COUNTRY_BASE_BR);

    when(mongoOperations.find(
            any(Query.class), eq(PromotionMultivendor.class), eq(COUNTRY_BASE_BR)))
        .thenReturn(promotions);

    List<PromotionMultivendor> promotionsByFilter =
        promotionRepositoryV2.findPromotionsByFilters(
            RequestContext.builder().withCountry(COUNTRY_BR).build(),
            PromotionFilterV2.builder().withIgnoreStartDate(false).build());

    verify(mongoOperations).find(any(), any(), any());
    Assertions.assertEquals(promotions.size(), promotionsByFilter.size());
    Assertions.assertEquals(promotions.get(0).getId(), promotionsByFilter.get(0).getId());
  }

  @Test
  public void testFindPromotionsByFiltersMultiVendor() {

    List<PromotionMultivendor> promotions =
        Arrays.asList(PromotionMocks.getPromotionsMultiVendor());

    when(databaseCollectionProperties.getBase(anyString())).thenReturn(COUNTRY_BASE_US);

    when(mongoOperations.find(
            any(Query.class), eq(PromotionMultivendor.class), eq(COUNTRY_BASE_US)))
        .thenReturn(promotions);

    List<PromotionMultivendor> promotionsByFilter =
        promotionRepositoryV2.findPromotionsByFilters(
            RequestContext.builder().withCountry(COUNTRY_US).build(),
            PromotionFilterV2.builder()
                .withQuery("SOME_STRING")
                .withPromotionType(Arrays.asList(PromotionType.DISCOUNT))
                .withVendorId(VENDOR_ID)
                .withPromotionIds(Arrays.asList(PROMOTION_ID))
                .withIgnoreStartDate(false)
                .build());

    verify(mongoOperations).find(any(), any(), any());
    Assertions.assertEquals(promotions.size(), promotionsByFilter.size());
    Assertions.assertEquals(promotions.get(0).getId(), promotionsByFilter.get(0).getId());
  }
}
