package com.abinbev.b2b.promotion.v2.controllers;

import static com.abinbev.b2b.promotion.constants.ApiConstants.COUNTRY_HEADER;

import com.abinbev.b2b.promotion.annotations.JwtValidation;
import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.interceptors.RequestValidation;
import com.abinbev.b2b.promotion.properties.NonOfficialSupportedLanguages;
import com.abinbev.b2b.promotion.rest.vo.RequestContext;
import com.abinbev.b2b.promotion.v2.rest.vo.GetPromotionsVO;
import com.abinbev.b2b.promotion.v2.services.PromotionServiceV2;
import com.abinbev.b2b.promotion.v2.vo.PromotionFilterV2;
import com.newrelic.api.agent.Trace;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestValidation
@RequestMapping("/v2/promotions")
public class PromotionControllerV2 {
  private final NonOfficialSupportedLanguages nonOfficialSupportedLanguages;
  private final PromotionServiceV2 promotionServiceV2;

  @Autowired
  public PromotionControllerV2(final PromotionServiceV2 promotionServiceV2, final NonOfficialSupportedLanguages nonOfficialSupportedLanguages) {

    this.promotionServiceV2 = promotionServiceV2;
    this.nonOfficialSupportedLanguages = nonOfficialSupportedLanguages;
  }

  @RequestValidation(timestamp = false)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @JwtValidation(hasAccount = false)
  @Trace(dispatcher = true, metricName = "/v2/promotions (GET)")
  public ResponseEntity<GetPromotionsVO> getPromotions(
      @RequestHeader(value = COUNTRY_HEADER) final String country,
      @RequestHeader(value = ApiConstants.ACCEPT_LANGUAGE_HEADER, required = false)
          final String acceptLanguage,
      @RequestParam(required = false) final boolean ignoreStartDate,
      @RequestParam(required = false) final Integer page,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final List<String> promotionIds,
      @RequestParam(required = false) final String query,
      @RequestParam(required = false) final List<PromotionType> types,
      @RequestParam(required = false) final String vendorId,
      @RequestParam(required = false) final List<String> vendorPromotionIds) {
    return new ResponseEntity<>(
        promotionServiceV2.getPromotions(
            RequestContext.builder()
                .withCountry(country)
                .withNonOfficialSupportedLanguages(nonOfficialSupportedLanguages.getNonOfficialSupportedLanguages())
                .withAcceptLanguage(acceptLanguage)
                .build(),
            PromotionFilterV2.builder()
                .withIgnoreStartDate(ignoreStartDate)
                .withPage(page)
                .withPageSize(pageSize)
                .withPromotionIds(promotionIds)
                .withQuery(query)
                .withPromotionType(types)
                .withVendorId(vendorId)
                .withVendorPromotionIds(vendorPromotionIds)
                .build()),
        HttpStatus.OK);
  }
}
