package com.abinbev.b2b.promotion.controllers;

import com.abinbev.b2b.promotion.annotations.JwtValidation;
import com.abinbev.b2b.promotion.constants.ApiConstants;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.interceptors.RequestValidation;
import com.abinbev.b2b.promotion.rest.vo.PromotionMultiVendorResponseVO;
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
@RequestMapping("/promotions")
public class PromotionMultiVendorController {

  @Autowired
  public PromotionMultiVendorController() {}

  /** @deprecated Deprecated since: Sprint 92 - 07/2022 - BEESCS-21257 ) */
  @RequestValidation(timestamp = false)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @JwtValidation(position = 0)
  @Deprecated(forRemoval = true, since = "Sprint 92 - 07/2022 - BEESCS-21257")
  @Trace(dispatcher = true, metricName = "/v1/promotions multi vendor (GET)")
  public ResponseEntity<PromotionMultiVendorResponseVO> getPromotions(
      @RequestParam final String vendorAccountId,
      @RequestHeader final String country,
      @RequestHeader final String requestTraceId,
      @RequestHeader(value = ApiConstants.ACCEPT_LANGUAGE_HEADER, required = false)
          final String acceptLanguage,
      @RequestParam final String vendorId,
      @RequestParam(required = false) final Integer page,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false) final List<PromotionType> types,
      @RequestParam(required = false) final List<String> vendorItemIds,
      @RequestParam(required = false) final List<String> vendorPromotionIds) {
    return new ResponseEntity<>(HttpStatus.GONE);
  }
}
