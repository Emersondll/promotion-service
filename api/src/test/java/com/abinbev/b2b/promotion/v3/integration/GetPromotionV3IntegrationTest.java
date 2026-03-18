package com.abinbev.b2b.promotion.v3.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.abinbev.b2b.promotion.config.BaseIntegrationTest;
import com.abinbev.b2b.promotion.config.IntegrationTest;
import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.helpers.HeaderBuilder;
import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.helpers.PromotionMultiVendorMockBuilder;
import com.abinbev.b2b.promotion.v3.services.PromotionServiceV3;
import com.abinbev.b2b.promotion.v3.vo.PromotionMarketplaceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@IntegrationTest
public class GetPromotionV3IntegrationTest extends BaseIntegrationTest {

  private static final String URL = "/v3/promotions";
  private static final String IGNORE_START_DATE_PARAM = "ignoreStartDate";
  private static final String TYPES_PARAM = "types";
  private static final String VENDOR_IDS_PARAM = "vendorIds";
  private static final String PROMOTION_PLATFORM_IDS_PARAM = "promotionPlatformIds";

  @Autowired private MockMvc mockMvc;

  @SpyBean private PromotionServiceV3 promotionService;

  @BeforeEach
  public void setup() {
    cleanCollections();

    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder().mock(PromotionType.DISCOUNT, "1").build());
    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder().mock(PromotionType.FREE_GOOD, "2").build());
    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder()
            .mock(PromotionType.FREE_GOOD, "3")
            .withVendorId(PromotionMocks.VENDOR_ID)
            .addYearsToStartDate(60L)
            .build());
    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder()
            .mock(PromotionType.FREE_GOOD, "4")
            .withVendorId(PromotionMocks.VENDOR_ID)
            .build());
    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder()
            .mock(PromotionType.DISCOUNT, "5")
            .withVendorId(PromotionMocks.VENDOR_ID)
            .build());
    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder()
            .mock(PromotionType.FREE_GOOD, "6")
            .withVendorId(PromotionMocks.VENDOR_ID)
            .withPlatformPromotionId(PromotionMocks.PROMOTION_PLATFORM_ID_1)
            .build());
    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder()
            .mock(PromotionType.STEPPED_DISCOUNT, "7")
            .withVendorId(PromotionMocks.VENDOR_ID_2)
            .withPlatformPromotionId(PromotionMocks.PROMOTION_PLATFORM_ID_2)
            .build());
    saveUSMultiVendorPromotion(
        PromotionMultiVendorMockBuilder.builder()
            .mock(PromotionType.STEPPED_DISCOUNT, "8")
            .withVendorId(PromotionMocks.VENDOR_ID_2)
            .withPlatformPromotionId(PromotionMocks.PROMOTION_PLATFORM_ID_1)
            .build());
  }

  @Test
  public void shouldGetUnpagedPromotionsWithDefaultPageSizeByVendorIds() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, PromotionMocks.VENDOR_ID)
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk()).andExpect(status().is(200));

    final PromotionMarketplaceResponse response = getValidResponse(result);

    assertThat(response).isNotNull();
    assertThat(response.getContent()).hasSize(3);
    response
        .getContent()
        .forEach(
            promotion ->
                assertThat(promotion.getVendorUniqueIds().getVendorId())
                    .isEqualTo(PromotionMocks.VENDOR_ID));
    assertThat(response.getPagination().getPage()).isEqualTo(0);
    assertThat(response.getPagination().isHasNext()).isEqualTo(false);
  }

  @Test
  public void shouldGetPagedPromotionsByVendorIds() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, PromotionMocks.VENDOR_ID)
                .param(PAGE_PARAM, "0")
                .param(PAGE_SIZE_PARAM, "2")
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk()).andExpect(status().is(200));

    final PromotionMarketplaceResponse response = getValidResponse(result);

    assertThat(response).isNotNull();
    assertThat(response.getContent()).hasSize(2);
    response
        .getContent()
        .forEach(
            promotion ->
                assertThat(promotion.getVendorUniqueIds().getVendorId())
                    .isEqualTo(PromotionMocks.VENDOR_ID));
    assertThat(response.getPagination().getPage()).isEqualTo(0);
    assertThat(response.getPagination().isHasNext()).isEqualTo(true);
  }

  @Test
  public void shouldGetPromotionsByVendorIdsAndTypes() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, PromotionMocks.VENDOR_ID)
                .param(TYPES_PARAM, PromotionType.DISCOUNT.name())
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk()).andExpect(status().is(200));

    final PromotionMarketplaceResponse response = getValidResponse(result);

    assertThat(response).isNotNull();
    assertThat(response.getContent()).hasSize(1);
    response
        .getContent()
        .forEach(
            promotion ->
                assertThat(promotion.getVendorUniqueIds().getVendorId())
                    .isEqualTo(PromotionMocks.VENDOR_ID));
    assertThat(response.getPagination().getPage()).isEqualTo(0);
    assertThat(response.getPagination().isHasNext()).isEqualTo(false);
  }

  @Test
  public void shouldGetEmptyWithPageHigherThanElementsAmount() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, PromotionMocks.VENDOR_ID)
                .param(PAGE_PARAM, "2")
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk()).andExpect(status().is(200));

    final PromotionMarketplaceResponse response = getValidResponse(result);

    assertThat(response).isNotNull();
    assertThat(response.getContent()).isEmpty();
    assertThat(response.getPagination().getPage()).isEqualTo(2);
    assertThat(response.getPagination().isHasNext()).isEqualTo(false);
  }

  @Test
  public void shouldGetPromotionsWithStartDateNotAvailable() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, PromotionMocks.VENDOR_ID)
                .param(IGNORE_START_DATE_PARAM, "true")
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk()).andExpect(status().is(200));

    final PromotionMarketplaceResponse response = getValidResponse(result);

    assertThat(response).isNotNull();
    assertThat(response.getContent()).hasSize(4);
    response
        .getContent()
        .forEach(
            promotion ->
                assertThat(promotion.getVendorUniqueIds().getVendorId())
                    .isEqualTo(PromotionMocks.VENDOR_ID));
    assertThat(response.getPagination().getPage()).isEqualTo(0);
    assertThat(response.getPagination().isHasNext()).isEqualTo(false);
  }

  @Test
  public void shouldGetPromotionsFilteringByVendorIdsAndPromotionPlatformIds() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, PromotionMocks.VENDOR_ID)
                .param(PROMOTION_PLATFORM_IDS_PARAM, PromotionMocks.PROMOTION_PLATFORM_ID_1)
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk()).andExpect(status().is(200));

    final PromotionMarketplaceResponse response = getValidResponse(result);

    assertThat(response).isNotNull();
    assertThat(response.getContent()).hasSize(1);
    response
        .getContent()
        .forEach(
            promotion -> {
              assertThat(promotion.getVendorUniqueIds().getVendorId())
                  .isEqualTo(PromotionMocks.VENDOR_ID);
              assertThat(promotion.getPlatformUniqueIds().getPromotionPlatformId())
                  .isEqualTo(PromotionMocks.PROMOTION_PLATFORM_ID_1);
            });
    assertThat(response.getPagination().getPage()).isEqualTo(0);
    assertThat(response.getPagination().isHasNext()).isEqualTo(false);
  }

  @Test
  public void shouldGetEmptyPromotionsByNonExistentVendorIds() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, "INVALID")
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk()).andExpect(status().is(200));

    final PromotionMarketplaceResponse response = getValidResponse(result);

    assertThat(response).isNotNull();
    assertThat(response.getContent()).isEmpty();
    assertThat(response.getPagination().getPage()).isEqualTo(0);
    assertThat(response.getPagination().isHasNext()).isEqualTo(false);
  }

  @Test
  public void shouldThrowErrorByInvalidSizeVendorIdsParam() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, generateRandomChars51Times())
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isBadRequest());
  }

  @Test
  public void shouldThrowBadRequestByInvalidSizePromotionPlatformIdsParam() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(PROMOTION_PLATFORM_IDS_PARAM, generateRandomChars51Times())
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isBadRequest());
  }

  @Test
  public void shouldThrowBadRequestByEmptyFilters() throws Exception {
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isBadRequest());
  }

  @Test
  public void shouldThrowInternalServerErrorByServiceError() throws Exception {
    doThrow(new RuntimeException()).when(promotionService).getPromotions(any(), any(), any());
    final ResultActions result =
        mockMvc.perform(
            MockMvcRequestBuilders.get(URL)
                .headers(
                    HeaderBuilder.builder()
                        .withCountry(PromotionMocks.COUNTRY_US)
                        .withRequestTraceId(PromotionMocks.REQUEST_TRACE_ID)
                        .build())
                .param(VENDOR_IDS_PARAM, PromotionMocks.VENDOR_ID)
                .contentType(MediaType.APPLICATION_JSON));

    result.andExpect(status().isInternalServerError());
  }

  private PromotionMarketplaceResponse getValidResponse(final ResultActions resultActions)
      throws Exception {
    return new ObjectMapper()
        .readValue(
            resultActions.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(),
            PromotionMarketplaceResponse.class);
  }

  private String generateRandomChars51Times() {
    var stringBuffer = new StringBuilder();
    var x = 0;
    var times = 51;
    while (x < times) {
      stringBuffer.append(",").append(UUID.randomUUID());
      x++;
    }
    return stringBuffer.toString();
  }
}
