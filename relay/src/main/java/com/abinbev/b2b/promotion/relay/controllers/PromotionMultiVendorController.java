package com.abinbev.b2b.promotion.relay.controllers;

import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.API_LABEL_V3_PROMOTIONS;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.COUNTRY_HEADER;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.PARENT_REQUEST_TRACE_ID_HEADER;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.REQUEST_TRACE_ID_HEADER;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.V3_CREATE_PROMOTION_MULTIVENDOR_POST;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.V3_DELETE_PROMOTION_MULTIVENDOR;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.X_TIMESTAMP_HEADER;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.constants.ApiConstants;
import com.abinbev.b2b.promotion.relay.helpers.HttpContextHelper;
import com.abinbev.b2b.promotion.relay.helpers.SecurityHelper;
import com.abinbev.b2b.promotion.relay.interceptors.RequestValidation;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorRequest;
import com.abinbev.b2b.promotion.relay.services.PromotionMultiVendorService;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({API_LABEL_V3_PROMOTIONS})
public class PromotionMultiVendorController {

  private final PromotionMultiVendorService promotionMultiVendorService;
  private final SecurityHelper securityHelper;
  private final HttpContextHelper httpContextHelper;

  private static final String TOTAL_PROMOTIONS = "totalPromotions";
  private static final String OPERATION = "operation";

  @Autowired
  public PromotionMultiVendorController(
      final PromotionMultiVendorService promotionMultiVendorService,
      final SecurityHelper securityHelper,
      final HttpContextHelper httpContextHelper) {

    super();
    this.promotionMultiVendorService = promotionMultiVendorService;
    this.securityHelper = securityHelper;
    this.httpContextHelper = httpContextHelper;
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @RequestValidation(requestTraceId = true, xtimestampIsRequired = true, xtimestampIsValid = false)
  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Trace(dispatcher = true, metricName = V3_CREATE_PROMOTION_MULTIVENDOR_POST)
  public ResponseEntity<Void> createMultiVendorPromotions(
      @Valid @RequestBody final PromotionMultiVendorRequest promotionMultiVendorRequest,
      @RequestHeader(COUNTRY_HEADER) final String country,
      @RequestHeader(REQUEST_TRACE_ID_HEADER) final String requestTraceId,
      @RequestHeader(X_TIMESTAMP_HEADER) final Long xTimestamp,
      @RequestHeader(value = PARENT_REQUEST_TRACE_ID_HEADER, required = false)
          final String parentRequestTraceId) {

    NewRelic.addCustomParameter(OPERATION, "POST/V3");
    NewRelic.addCustomParameter(TOTAL_PROMOTIONS, promotionMultiVendorRequest.size());

    promotionMultiVendorService.createMultiVendorPromotions(
        promotionMultiVendorRequest.getList(),
        new BaseMessage(
            countryToUpperCase(country),
            requestTraceId,
            timestamp(xTimestamp),
            parentRequestTraceId),
        securityHelper.getValidVendorId(
            httpContextHelper.getServletRequest().getHeader(ApiConstants.AUTHORIZATION_HEADER)));

    NewRelic.addCustomParameter("SUCCESS", true);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  @ResponseStatus(HttpStatus.ACCEPTED)
  @RequestValidation(requestTraceId = true, xtimestampIsRequired = true, xtimestampIsValid = false)
  @DeleteMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @Trace(dispatcher = true, metricName = V3_DELETE_PROMOTION_MULTIVENDOR)
  public ResponseEntity<Void> deleteMultiVendorPromotions(
      @Valid @RequestBody final PromotionMultiVendorDeleteRequest promotionMultiVendorDeleteRequest,
      @RequestHeader(COUNTRY_HEADER) final String country,
      @RequestHeader(REQUEST_TRACE_ID_HEADER) final String requestTraceId,
      @RequestHeader(X_TIMESTAMP_HEADER) final Long xTimestamp,
      @RequestHeader(value = PARENT_REQUEST_TRACE_ID_HEADER, required = false)
          final String parentRequestTraceId) {

    NewRelic.addCustomParameter(OPERATION, "DELETE/V3");
    NewRelic.addCustomParameter(
        TOTAL_PROMOTIONS, promotionMultiVendorDeleteRequest.getVendorPromotionIds().size());

    promotionMultiVendorService.deleteMultiVendorPromotions(
        promotionMultiVendorDeleteRequest,
        new BaseMessage(
            countryToUpperCase(country),
            requestTraceId,
            timestamp(xTimestamp),
            parentRequestTraceId),
        securityHelper.getValidVendorId(
            httpContextHelper.getServletRequest().getHeader(ApiConstants.AUTHORIZATION_HEADER)));

    NewRelic.addCustomParameter("SUCCESS", true);
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }

  private Long timestamp(Long xTimestamp) {

    if (String.valueOf(xTimestamp).length() == 10) {
      return Long.parseLong(String.format("%s000", xTimestamp));
    } else {
      return xTimestamp;
    }
  }

  private String countryToUpperCase(String country) {
    return country.toUpperCase();
  }
}
