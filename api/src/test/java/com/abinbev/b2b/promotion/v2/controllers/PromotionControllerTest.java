package com.abinbev.b2b.promotion.v2.controllers;

import static com.abinbev.b2b.promotion.helpers.PromotionMocks.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.services.PromotionServiceV2;
import com.abinbev.b2b.promotion.v2.vo.PromotionFilterV2;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromotionControllerTest {

  @Mock NonOfficialSupportedLanguages nonOfficialSupportedLanguages;
  @Mock PromotionServiceV2 promotionServiceV2;

  @InjectMocks PromotionControllerV2 controller;

  @Test
  public void testGetPromotionWithoutFilters() {
    controller.getPromotions(
        COUNTRY_BR, ACCEPT_LANGUAGE_FR_CA, false, PAGE, PAGE_SIZE, null, null, null, null, null);

    final List<String> nonOfficialSupportedLanguagesList = Collections.singletonList("en-ES");
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
        .thenReturn(nonOfficialSupportedLanguagesList);

    verify(promotionServiceV2)
        .getPromotions(
            RequestContext.builder()
                .withCountry(COUNTRY_BR)
                .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList)
                .withAcceptLanguage(ACCEPT_LANGUAGE_FR_CA)
                .build(),
            PromotionFilterV2.builder()
                .withIgnoreStartDate(false)
                .withPage(PAGE)
                .withPageSize(PAGE_SIZE)
                .build());
  }

  @Test
  public void testGetPromotionWithFilters() {
    controller.getPromotions(
        COUNTRY_CA,
        ACCEPT_LANGUAGE_FR_CA,
        true,
        PAGE,
        PAGE_SIZE,
        Arrays.asList(PROMOTION_ID),
        "SOME_STRING",
        Arrays.asList(PROMOTION_TYPE),
        null,
        Arrays.asList(PROMOTION_ID));

    final List<String> nonOfficialSupportedLanguagesList = Collections.singletonList("en-ES");
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
            .thenReturn(nonOfficialSupportedLanguagesList);

    verify(promotionServiceV2)
        .getPromotions(
            RequestContext.builder()
                .withCountry(COUNTRY_CA)
                .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList)
                .withAcceptLanguage(ACCEPT_LANGUAGE_FR_CA)
                .build(),
            PromotionFilterV2.builder()
                .withIgnoreStartDate(true)
                .withPage(PAGE)
                .withPageSize(PAGE_SIZE)
                .withPromotionIds(Arrays.asList(PROMOTION_ID))
                .withQuery("SOME_STRING")
                .withPromotionType(Arrays.asList(PROMOTION_TYPE))
                .withVendorId(null)
                .withVendorPromotionIds(Arrays.asList(PROMOTION_ID))
                .build());
  }
}
