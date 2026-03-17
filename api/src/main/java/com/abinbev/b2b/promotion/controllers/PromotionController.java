package com.abinbev.b2b.promotion.controllers;

import static com.abinbev.b2b.promotion.constants.ApiConstants.COUNTRY_HEADER;

import com.abinbev.b2b.promotion.annotations.JwtValidation;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.interceptors.RequestValidation;
import com.abinbev.b2b.promotion.rest.vo.AccountPromotionVO;
import com.abinbev.b2b.promotion.rest.vo.GetPromotionsGroupedByAccountVO;
import com.abinbev.b2b.promotion.rest.vo.GetPromotionsVO;
import com.newrelic.api.agent.Trace;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestValidation
@RequestMapping("/")
public class PromotionController {

  @Autowired
  public PromotionController() {
    super();
  }

  /** @deprecated Deprecated since: Sprint 92 - 07/2022 - BEESCS-21257 ) */
  @RequestValidation(timestamp = false)
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @JwtValidation(position = 0)
  @Deprecated(forRemoval = true, since = "Sprint 92 - 07/2022 - BEESCS-21257")
  @Trace(dispatcher = true, metricName = "/v1/promotions (GET)")
  public ResponseEntity<GetPromotionsVO> getPromotions(
      @RequestParam(name = "accountId", required = false) final String accountId,
      @RequestParam(name = "updatedSince", required = false) final Long updatedSince,
      @RequestHeader(value = COUNTRY_HEADER) final String country,
      @RequestParam(required = false) final Integer page,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false, defaultValue = "false") final boolean includeDisabled,
      @RequestParam(name = "types", required = false) final List<PromotionType> types,
      @RequestParam(required = false) final String sku) {
    return new ResponseEntity<>(HttpStatus.GONE);
  }

  /** @deprecated Deprecated since: Sprint 92 - 07/2022 - BEESCS-21257 ) */
  @RequestValidation(timestamp = false)
  @GetMapping(value = "/accounts", produces = MediaType.APPLICATION_JSON_VALUE)
  @JwtValidation(position = 0, isMultiple = true)
  @Deprecated(forRemoval = true, since = "Sprint 92 - 07/2022 - BEESCS-21257")
  @Trace(dispatcher = true, metricName = "/v1/accounts (GET)")
  public ResponseEntity<GetPromotionsGroupedByAccountVO> getPromotionsByAccount(
      @RequestParam(name = "accounts", required = false) final Set<String> accounts,
      @RequestParam(name = "updatedSince", required = false) final Long updatedSince,
      @RequestHeader(COUNTRY_HEADER) @NotEmpty final String country,
      @RequestParam(required = false) final Integer page,
      @RequestParam(required = false) final Integer pageSize,
      @RequestParam(required = false, defaultValue = "false") final boolean includeDisabled) {
    return new ResponseEntity<>(HttpStatus.GONE);
  }

  /** @deprecated Deprecated since: Sprint 92 - 07/2022 - BEESCS-21257 ) */
  @RequestValidation(timestamp = false)
  @GetMapping(value = "/{accountId}/{promotionId}", produces = MediaType.APPLICATION_JSON_VALUE)
  @JwtValidation(position = 0)
  @Deprecated(forRemoval = true, since = "Sprint 92 - 07/2022 - BEESCS-21257")
  @Trace(dispatcher = true, metricName = "/v1/{accountId}/{promotionId} (GET)")
  public ResponseEntity<AccountPromotionVO> getByAccountIdAndPromotionId(
      @PathVariable final String accountId,
      @PathVariable final String promotionId,
      @RequestHeader(COUNTRY_HEADER) @NotEmpty final String country,
      @RequestParam(name = "types", required = false) final List<PromotionType> promotionTypeList,
      @Valid final String sku) {
    return new ResponseEntity<>(HttpStatus.GONE);
  }
}
