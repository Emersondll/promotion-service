package com.abinbev.b2b.promotion.v3.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.helpers.PromotionMultiVendorMockBuilder;
import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v3.repositories.PromotionRepositoryV3;
import com.abinbev.b2b.promotion.v3.vo.Paged;
import com.abinbev.b2b.promotion.v3.vo.PromotionFilterV3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceV3Test {

  private List<String> nonOfficialSupportedLanguagesList = Collections.singletonList("en-ES");
  @InjectMocks private PromotionServiceV3 service;
  @Mock private PromotionRepositoryV3 repository;

  @Test
  public void shouldGetPromotions() {

    var context =
        RequestContext.builder()
            .withCountry(PromotionMocks.COUNTRY_US)
            .withAcceptLanguage(PromotionMocks.ACCEPT_LANGUAGE_EN_US)
            .build();
    var filter =
        PromotionFilterV3.builder().withVendorIds(Set.of(PromotionMocks.VENDOR_ID)).build();
    var page = PageRequest.of(0, 50);

    when(repository.getPagedPromotion(context, filter, page)).thenReturn(samplePromotions());

    var result = service.getPromotions(context, filter, page);
    assertThat(result.getContent()).hasSize(2);
    assertThat(result.getPagination().getPage()).isEqualTo(0);
    assertThat(result.getPagination().isHasNext()).isEqualTo(false);
    result
        .getContent()
        .forEach(
            promotion -> {
              assertThat(promotion.getPlatformUniqueIds().getPromotionId()).isNotNull();
              assertThat(promotion.getPlatformUniqueIds().getPromotionPlatformId()).isNull();
              assertThat(promotion.getVendorUniqueIds().getVendorId()).isNull();
              assertThat(promotion.getVendorUniqueIds().getVendorPromotionId()).isNotNull();
            });
  }

  @Test
  public void shouldGetPromotionsWithOneNullElement() {

    var context =
        RequestContext.builder()
            .withCountry(PromotionMocks.COUNTRY_US)
            .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList)
            .withAcceptLanguage(PromotionMocks.ACCEPT_LANGUAGE_EN_US)
            .build();
    var filter =
        PromotionFilterV3.builder().withVendorIds(Set.of(PromotionMocks.VENDOR_ID)).build();
    var page = PageRequest.of(0, 50);

    when(repository.getPagedPromotion(context, filter, page))
        .thenReturn(samplePromotionsWithNullElement());

    var result = service.getPromotions(context, filter, page);
    assertThat(result.getContent()).hasSize(1);
    assertThat(result.getPagination().getPage()).isEqualTo(0);
    assertThat(result.getPagination().isHasNext()).isEqualTo(false);
    result
        .getContent()
        .forEach(
            promotion -> {
              assertThat(promotion.getPlatformUniqueIds().getPromotionId()).isNotNull();
              assertThat(promotion.getPlatformUniqueIds().getPromotionPlatformId()).isNull();
              assertThat(promotion.getVendorUniqueIds().getVendorId()).isNull();
              assertThat(promotion.getVendorUniqueIds().getVendorPromotionId()).isNotNull();
            });
  }

  @Test
  public void shouldGetPromotionsWithEmptyResult() {
    var context =
        RequestContext.builder()
            .withCountry(PromotionMocks.COUNTRY_US)
            .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList)
            .withAcceptLanguage(PromotionMocks.ACCEPT_LANGUAGE_EN_US)
            .build();
    var filter =
        PromotionFilterV3.builder().withVendorIds(Set.of(PromotionMocks.VENDOR_ID)).build();
    var page = PageRequest.of(0, 50);

    when(repository.getPagedPromotion(context, filter, page)).thenReturn(Paged.empty());

    var result = service.getPromotions(context, filter, page);
    assertThat(result.getContent()).hasSize(0);
    assertThat(result.getPagination().getPage()).isEqualTo(0);
    assertThat(result.getPagination().isHasNext()).isEqualTo(false);
  }

  private Paged<PromotionMultivendor> samplePromotions() {
    final ArrayList<PromotionMultivendor> promotions = new ArrayList<>();
    promotions.add(PromotionMultiVendorMockBuilder.builder().mock(PromotionType.DISCOUNT).build());
    promotions.add(
        PromotionMultiVendorMockBuilder.builder().mock(PromotionType.STEPPED_DISCOUNT).build());
    return new Paged<>(promotions, false, 0);
  }

  private Paged<PromotionMultivendor> samplePromotionsWithNullElement() {
    final ArrayList<PromotionMultivendor> promotions = new ArrayList<>();
    promotions.add(PromotionMultiVendorMockBuilder.builder().mock(PromotionType.DISCOUNT).build());
    promotions.add(null);
    return new Paged<>(promotions, false, 0);
  }
}
