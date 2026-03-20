package com.abinbev.b2b.promotion.relay.controllers;

import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.API_LABEL_V2;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.COUNTRY_HEADER;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.REQUEST_TRACE_ID_HEADER;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.V2_CREATE_PROMOTIONS_POST;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.V2_DELETE_PROMOTIONS;
import static com.abinbev.b2b.promotion.relay.constants.ApiConstants.X_TIMESTAMP_HEADER;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.interceptors.RequestValidation;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorRequest;
import com.abinbev.b2b.promotion.relay.services.PromotionSingleVendorService;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestValidation
@RequestMapping({API_LABEL_V2})
public class PromotionSingleVendorController {

  private PromotionSingleVendorService promotionSingleVendorService;

  private static final String TOTAL_PROMOTIONS = "totalPromotions";
  private static final String OPERATION = "operation";

  @Autowired
  public PromotionSingleVendorController(
      final PromotionSingleVendorService promotionSingleVendorService) {

    super();
    this.promotionSingleVendorService = promotionSingleVendorService;
  }

  @ResponseStatus(ACCEPTED)
  @RequestValidation(requestTraceId = true, xtimestampIsRequired = true, xtimestampIsValid = false)
  @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Trace(dispatcher = true, metricName = V2_CREATE_PROMOTIONS_POST)
  public ResponseEntity<Void> createPromotions(
      @Valid @RequestBody final PromotionSingleVendorRequest promotionSingleVendorRequest,
      @RequestHeader(COUNTRY_HEADER) @NotEmpty final String country,
      @RequestHeader(REQUEST_TRACE_ID_HEADER) @NotEmpty final String requestTraceId,
      @RequestHeader(X_TIMESTAMP_HEADER) @NotEmpty final Long xTimestamp) {

    NewRelic.addCustomParameter(TOTAL_PROMOTIONS, promotionSingleVendorRequest.getList().size());
    NewRelic.addCustomParameter(OPERATION, "POST/V2");

    promotionSingleVendorService.createPromotions(
        promotionSingleVendorRequest.getList(),
        new BaseMessage(countryToUpperCase(country), requestTraceId, timestamp(xTimestamp)));

    NewRelic.addCustomParameter("SUCCESS", true);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ResponseStatus(ACCEPTED)
  @DeleteMapping(produces = APPLICATION_JSON_VALUE)
  @Trace(dispatcher = true, metricName = V2_DELETE_PROMOTIONS)
  public ResponseEntity<Void> deletePromotion(
      @Valid @RequestBody
          final PromotionSingleVendorDeleteRequest promotionSingleVendorDeleteRequest,
      @RequestHeader(COUNTRY_HEADER) @NotEmpty final String country,
      @RequestHeader(X_TIMESTAMP_HEADER) @NotEmpty final Long xTimestamp,
      @RequestHeader(REQUEST_TRACE_ID_HEADER) @NotEmpty final String requestTraceId) {

    NewRelic.addCustomParameter(
        TOTAL_PROMOTIONS, emptyIfNull(promotionSingleVendorDeleteRequest.getPromotions()).size());
    NewRelic.addCustomParameter(OPERATION, "DELETE/V2");

    promotionSingleVendorService.deletePromotion(
        promotionSingleVendorDeleteRequest,
        new BaseMessage(countryToUpperCase(country), requestTraceId, timestamp(xTimestamp)));

    NewRelic.addCustomParameter("SUCCESS", true);
    return new ResponseEntity<>(ACCEPTED);
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
