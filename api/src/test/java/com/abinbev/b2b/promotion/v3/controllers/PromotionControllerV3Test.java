package com.abinbev.b2b.promotion.v3.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.abinbev.b2b.promotion.exceptions.BadRequestException;
import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v3.services.PromotionServiceV3;
import com.abinbev.b2b.promotion.v3.vo.PromotionFilterV3;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class PromotionControllerV3Test {

  @Mock private NonOfficialSupportedLanguages nonOfficialSupportedLanguages;
  @InjectMocks private PromotionControllerV3 controller;
  @Mock private PromotionServiceV3 service;

  @Test
  public void shouldGetByVendorIds() {

    var contextCaptor = ArgumentCaptor.forClass(RequestContext.class);
    var filterCaptor = ArgumentCaptor.forClass(PromotionFilterV3.class);
    var pageCaptor = ArgumentCaptor.forClass(Pageable.class);

    final List<String> nonOfficialSupportedLanguagesList = Collections.singletonList("en-ES");
    when(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
            .thenReturn(nonOfficialSupportedLanguagesList);

    controller.getPromotions(
        PromotionMocks.COUNTRY_US,
        PromotionMocks.ACCEPT_LANGUAGE_EN_US,
        false,
        "",
        new HashSet<>(),
        Set.of(PromotionMocks.VENDOR_ID),
        new HashSet<>(),
        PageRequest.of(0, 20));

    verify(service)
        .getPromotions(contextCaptor.capture(), filterCaptor.capture(), pageCaptor.capture());

    var context = contextCaptor.getValue();
    var filter = filterCaptor.getValue();
    var page = pageCaptor.getValue();

    assertThat(context)
        .usingRecursiveComparison()
        .isEqualTo(
            RequestContext.builder()
                .withCountry(PromotionMocks.COUNTRY_US)
                .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguagesList)
                .withAcceptLanguage(PromotionMocks.ACCEPT_LANGUAGE_EN_US)
                .build());
    assertThat(filter)
        .usingRecursiveComparison()
        .isEqualTo(
            PromotionFilterV3.builder()
                .withVendorIds(Set.of(PromotionMocks.VENDOR_ID))
                .withIgnoreStartDate(false)
                .withPromotionPlatformIds(new HashSet<>())
                .withQuery("")
                .withTypes(new HashSet<>())
                .build());
    assertThat(page).usingRecursiveComparison().isEqualTo(PageRequest.of(0, 20));
  }

  @Test
  public void shouldNotPassFilter() {

    Assertions.assertThrows(
        BadRequestException.class,
        () ->
            controller.getPromotions(
                PromotionMocks.COUNTRY_US,
                PromotionMocks.ACCEPT_LANGUAGE_EN_US,
                false,
                "",
                new HashSet<>(),
                null,
                new HashSet<>(),
                PageRequest.of(0, 20)));
  }
}
