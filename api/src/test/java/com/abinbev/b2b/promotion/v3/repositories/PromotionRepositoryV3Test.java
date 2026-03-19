package com.abinbev.b2b.promotion.v3.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.helpers.PromotionMultiVendorMockBuilder;
import com.abinbev.b2b.promotion.properties.DatabaseCollectionProperties;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v3.vo.PromotionFilterV3;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

@ExtendWith(MockitoExtension.class)
public class PromotionRepositoryV3Test {

  private static final Long QUERY_TIMEOUT = 1000L;

  @InjectMocks private PromotionRepositoryV3 repository;
  @Mock private MongoOperations mongoOperations;
  @Mock private DatabaseCollectionProperties databaseCollectionProperties;

  @BeforeEach
  public void setUp() {
    when(databaseCollectionProperties.getMaxQueryTimeout()).thenReturn(QUERY_TIMEOUT);
    when(databaseCollectionProperties.getBase(PromotionMocks.COUNTRY_DO))
        .thenReturn(PromotionMocks.DO_PROMOTION_COLLECTION);
  }

  @Test
  public void shouldFindByVendorIds() {
    var queryCaptor = ArgumentCaptor.forClass(Query.class);
    var filter =
        PromotionFilterV3.builder().withVendorIds(Set.of(PromotionMocks.VENDOR_ID)).build();
    var context = RequestContext.builder().withCountry(PromotionMocks.COUNTRY_DO).build();
    var pageable = PageRequest.of(0, 50);

    when(mongoOperations.find(
            any(), eq(PromotionMultivendor.class), eq(PromotionMocks.DO_PROMOTION_COLLECTION)))
        .thenReturn(new ArrayList<>());

    repository.getPagedPromotion(context, filter, pageable);

    verify(mongoOperations)
        .find(
            queryCaptor.capture(),
            eq(PromotionMultivendor.class),
            eq(PromotionMocks.DO_PROMOTION_COLLECTION));

    var query = queryCaptor.getValue();
    assertThat(query.getQueryObject()).hasSize(4);
    assertThat(query.getQueryObject()).containsKey("endDate");
    assertThat(query.getQueryObject()).containsKey("startDate");
    assertThat(query.getQueryObject()).containsKey("deleted");
    assertThat(query.getQueryObject()).containsKey("vendorId");
    assertThat(query.getLimit()).isEqualTo(pageable.getPageSize() + 1);
    assertThat(query.getSkip()).isEqualTo(pageable.getPageNumber());
    assertThat(query.getMeta().getMaxTimeMsec()).isEqualTo(QUERY_TIMEOUT);
  }

  @Test
  public void shouldFindByPlatformIds() {
    var queryCaptor = ArgumentCaptor.forClass(Query.class);
    var filter =
        PromotionFilterV3.builder()
            .withPromotionPlatformIds(Set.of(PromotionMocks.PROMOTION_ID))
            .build();
    var context = RequestContext.builder().withCountry(PromotionMocks.COUNTRY_DO).build();
    var pageable = PageRequest.of(0, 10);

    when(mongoOperations.find(
            any(), eq(PromotionMultivendor.class), eq(PromotionMocks.DO_PROMOTION_COLLECTION)))
        .thenReturn(new ArrayList<>());

    repository.getPagedPromotion(context, filter, pageable);

    verify(mongoOperations)
        .find(
            queryCaptor.capture(),
            eq(PromotionMultivendor.class),
            eq(PromotionMocks.DO_PROMOTION_COLLECTION));

    var query = queryCaptor.getValue();
    assertThat(query.getQueryObject()).hasSize(4);
    assertThat(query.getQueryObject()).containsKey("endDate");
    assertThat(query.getQueryObject()).containsKey("startDate");
    assertThat(query.getQueryObject()).containsKey("deleted");
    assertThat(query.getQueryObject()).containsKey("promotionPlatformId");
    assertThat(query.getLimit()).isEqualTo(pageable.getPageSize() + 1);
    assertThat(query.getSkip()).isEqualTo(pageable.getPageNumber());
    assertThat(query.getMeta().getMaxTimeMsec()).isEqualTo(QUERY_TIMEOUT);
  }

  @Test
  public void shouldFindByAllFilters() {
    var queryCaptor = ArgumentCaptor.forClass(Query.class);
    var filter =
        PromotionFilterV3.builder()
            .withPromotionPlatformIds(Set.of(PromotionMocks.PROMOTION_ID))
            .withVendorIds(Set.of(PromotionMocks.VENDOR_ID))
            .withIgnoreStartDate(true)
            .withTypes(Set.of(PromotionType.DISCOUNT))
            .build();
    var context = RequestContext.builder().withCountry(PromotionMocks.COUNTRY_DO).build();
    var pageable = PageRequest.of(0, 2);

    when(mongoOperations.find(
            any(), eq(PromotionMultivendor.class), eq(PromotionMocks.DO_PROMOTION_COLLECTION)))
        .thenReturn(samplePromotions());

    var result = repository.getPagedPromotion(context, filter, pageable);

    assertThat(result.getContent()).hasSize(2);

    verify(mongoOperations)
        .find(
            queryCaptor.capture(),
            eq(PromotionMultivendor.class),
            eq(PromotionMocks.DO_PROMOTION_COLLECTION));

    var query = queryCaptor.getValue();
    assertThat(query.getQueryObject()).hasSize(5);
    assertThat(query.getQueryObject()).containsKey("endDate");
    assertThat(query.getQueryObject()).containsKey("deleted");
    assertThat(query.getQueryObject()).containsKey("promotionPlatformId");
    assertThat(query.getQueryObject()).containsKey("vendorId");
    assertThat(query.getQueryObject()).containsKey("promotionType");
    assertThat(query.getLimit()).isEqualTo(pageable.getPageSize() + 1);
    assertThat(query.getSkip()).isEqualTo(pageable.getPageNumber());
    assertThat(query.getMeta().getMaxTimeMsec()).isEqualTo(QUERY_TIMEOUT);
  }

  private List<PromotionMultivendor> samplePromotions() {
    final ArrayList<PromotionMultivendor> promotions = new ArrayList<>();
    promotions.add(PromotionMultiVendorMockBuilder.builder().mock(PromotionType.DISCOUNT).build());
    promotions.add(
        PromotionMultiVendorMockBuilder.builder().mock(PromotionType.STEPPED_DISCOUNT).build());
    return promotions;
  }
}
