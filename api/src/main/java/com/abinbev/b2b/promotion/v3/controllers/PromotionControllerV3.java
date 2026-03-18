package com.abinbev.b2b.promotion.v3.controllers;

import static com.abinbev.b2b.promotion.constants.ApiConstants.COUNTRY_HEADER;

import com.abinbev.b2b.promotion.annotations.JwtValidation;
import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.interceptors.RequestValidation;
import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v3.services.PromotionServiceV3;
import com.abinbev.b2b.promotion.v3.vo.PromotionFilterV3;
import com.abinbev.b2b.promotion.v3.vo.PromotionMarketplaceResponse;
import com.abinbev.b2b.promotion.validators.annotations.MaxSizeConstraint;
import com.newrelic.api.agent.Trace;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestValidation
@RequestMapping("/v3/promotions")
public class PromotionControllerV3 {
    private final NonOfficialSupportedLanguages nonOfficialSupportedLanguages;
  private final PromotionServiceV3 promotionService;

  @Autowired
  public PromotionControllerV3(final PromotionServiceV3 promotionService, final NonOfficialSupportedLanguages nonOfficialSupportedLanguages) {

      this.promotionService = promotionService;
      this.nonOfficialSupportedLanguages = nonOfficialSupportedLanguages;
  }

  @RequestValidation(timestamp = false)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @JwtValidation(hasAccount = false)
  @Trace(dispatcher = true, metricName = "/v3/promotions (GET)")
  public ResponseEntity<PromotionMarketplaceResponse> getPromotions(
      @RequestHeader(value = COUNTRY_HEADER) final String country,
      @RequestHeader(value = ApiConstants.ACCEPT_LANGUAGE_HEADER, required = false)
          final String acceptLanguage,
      @RequestParam(required = false) final boolean ignoreStartDate,
      @RequestParam(required = false) final String query,
      @RequestParam(required = false) final Set<PromotionType> types,
      @RequestParam(required = false)
          final @MaxSizeConstraint(property = "parameters.vendor-ids-max-size") Set<String>
              vendorIds,
      @RequestParam(required = false)
          final @MaxSizeConstraint(property = "parameters.promotion-platform-ids-max-size") Set<
                  String>
              promotionPlatformIds,
      Pageable pageable) {

    return new ResponseEntity<>(
        promotionService.getPromotions(
            RequestContext.builder()
                .withCountry(country)
                .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
                .withAcceptLanguage(acceptLanguage)
                .build(),
            PromotionFilterV3.builder()
                .withIgnoreStartDate(ignoreStartDate)
                .withQuery(query)
                .withTypes(types)
                .withVendorIds(vendorIds)
                .withPromotionPlatformIds(promotionPlatformIds)
                .build(),
            pageable),
        HttpStatus.OK);
  }
}
