package com.abinbev.b2b.promotion.v2.services;

import static com.abinbev.b2b.promotion.helpers.PromotionMocks.ACCEPT_LANGUAGE_ES_US;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.COUNTRY_US;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.PAGE;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.PAGE_SIZE;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.PROMOTION_ID;
import static com.abinbev.b2b.promotion.helpers.PromotionMocks.VENDOR_ID;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.helpers.PromotionMultiVendorMockBuilder;
import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.mapper.PromotionMapperBuilder;
import com.abinbev.b2b.promotion.v2.repositories.PromotionRepositoryV2;
import com.abinbev.b2b.promotion.v2.rest.vo.GetPromotionsVO;
import com.abinbev.b2b.promotion.v2.vo.PromotionFilterV2;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromotionServiceV2Test {

  @Mock private NonOfficialSupportedLanguages nonOfficialSupportedLanguages;
  @InjectMocks private PromotionServiceV2 promotionServiceV2;
  @Mock private PromotionRepositoryV2 promotionRepositoryV2;

  @BeforeEach
  public void setUp() {
    final PromotionMapperBuilder mapper = Mappers.getMapper(PromotionMapperBuilder.class);
    ReflectionTestUtils.setField(promotionServiceV2, "mapper", mapper);
  }

  Pagination pagination;

  @Test
  public void testGetPromotionsMultiVendor() {

    pagination = new Pagination(PAGE, PAGE_SIZE);

    final List<String> nonOfficialSupportedLanguagesList = Collections.singletonList("en-ES");
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
            .thenReturn(nonOfficialSupportedLanguagesList);

    when(promotionRepositoryV2.findPromotionsByFilters(
            RequestContext.builder().withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList).withCountry(COUNTRY_US).build(),
            PromotionFilterV2.builder().withIgnoreStartDate(false).build()))
        .thenReturn(
            Arrays.asList(
                PromotionMultiVendorMockBuilder.builder()
                    .mock(PromotionType.DISCOUNT)
                    .withVendorId(VENDOR_ID)
                    .build(),
                PromotionMultiVendorMockBuilder.builder()
                    .mock(PromotionType.STEPPED_DISCOUNT)
                    .withVendorId(VENDOR_ID)
                    .build()));

    final GetPromotionsVO response =
        promotionServiceV2.getPromotions(
            RequestContext.builder().withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList).withCountry(COUNTRY_US).build(),
            PromotionFilterV2.builder().withIgnoreStartDate(false).build());

    Assertions.assertEquals(2, response.getPromotions().size());
    response.getPromotions().forEach(p -> Assertions.assertEquals(VENDOR_ID, p.getVendorId()));
    response.getPromotions().forEach(p -> Assertions.assertNotNull(p.getVendorId()));
    response
        .getPromotions()
        .forEach(p -> Assertions.assertNotEquals(p.getPromotionId(), p.getVendorPromotionId()));
  }

  @Test
  public void testGetPromotionsMultiVendorWithFilters() {

    pagination = new Pagination(PAGE, PAGE_SIZE);

    final List<String> nonOfficialSupportedLanguagesList = Collections.singletonList("en-ES");
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
            .thenReturn(nonOfficialSupportedLanguagesList);

    when(promotionRepositoryV2.findPromotionsByFilters(
            RequestContext.builder()
                .withCountry(COUNTRY_US)
                .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList)
                .withAcceptLanguage(ACCEPT_LANGUAGE_ES_US)
                .build(),
            PromotionFilterV2.builder()
                .withIgnoreStartDate(true)
                .withPromotionIds(Arrays.asList(PROMOTION_ID))
                .withVendorId(VENDOR_ID)
                .withPromotionType(Arrays.asList(PromotionType.DISCOUNT))
                .withQuery("SOME_STRING")
                .withVendorPromotionIds(Arrays.asList(PROMOTION_ID))
                .build()))
        .thenReturn(
            Arrays.asList(
                PromotionMultiVendorMockBuilder.builder()
                    .mock(PromotionType.DISCOUNT)
                    .withVendorId(VENDOR_ID)
                    .build(),
                PromotionMultiVendorMockBuilder.builder()
                    .mock(PromotionType.STEPPED_DISCOUNT)
                    .withVendorId(VENDOR_ID)
                    .build()));

    final GetPromotionsVO response =
        promotionServiceV2.getPromotions(
            RequestContext.builder()
                .withCountry(COUNTRY_US)
                .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList)
                .withAcceptLanguage(ACCEPT_LANGUAGE_ES_US)
                .build(),
            PromotionFilterV2.builder()
                .withIgnoreStartDate(true)
                .withPromotionIds(Arrays.asList(PROMOTION_ID))
                .withVendorId(VENDOR_ID)
                .withPromotionType(Arrays.asList(PromotionType.DISCOUNT))
                .withQuery("SOME_STRING")
                .withVendorPromotionIds(Arrays.asList(PROMOTION_ID))
                .build());

    Assertions.assertEquals(2, response.getPromotions().size());
    response.getPromotions().forEach(p -> Assertions.assertEquals(VENDOR_ID, p.getVendorId()));
    response.getPromotions().forEach(p -> Assertions.assertNotNull(p.getVendorId()));
    response
        .getPromotions()
        .forEach(p -> Assertions.assertNotEquals(p.getPromotionId(), p.getVendorPromotionId()));
  }
}
