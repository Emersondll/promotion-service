package com.abinbev.b2b.promotion.relay.integration;

import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.COUNTRY;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.COUNTRY_WITHOUT_DEFAULT_VENDOR;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.DESCRIPTION_BUY_5_GET_7_BUY_10;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.END_DATE_2020_03_31T23_59_59_999Z;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.STAR_DATE_2019_03_31T23_59_59_999Z;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.getPromotionSingleVendorRequestMock;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.getPromotionSingleVendorRequestWithoutPromotionTypeMock;
import static com.abinbev.b2b.promotion.relay.helpers.PromotionMocks.getPromotionSingleVendorWithoutTitleRequestMock;
import static com.abinbev.b2b.promotion.relay.integration.config.MockMvcHelper.post;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import com.abinbev.b2b.promotion.relay.domain.PromotionType;
import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.relay.integration.config.BaseIntegrationTest;
import com.abinbev.b2b.promotion.relay.integration.config.HeaderBuilder;
import com.abinbev.b2b.promotion.relay.integration.config.IntegrationTest;
import com.abinbev.b2b.promotion.relay.integration.response.PromotionSingleVendorResponse;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@IntegrationTest
public class PromotionSingleVendorIntegrationTest extends BaseIntegrationTest {

  private static final String URL_V2 = "/v2";

  @Autowired private MockMvc mockMvc;

  private static final ObjectMapper objectMapper =
      new ObjectMapper().configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

  @BeforeEach
  public void setup() {
    purge(getPromotionMultiVendorQueue(COUNTRY));
  }

  @Test
  public void createPromotionThenReturnOk() throws Exception {

    final PromotionSingleVendorRequest request = getPromotionSingleVendorRequestMock();

    final ResultActions result =
        mockMvc.perform(post(URL_V2, request).headers(buildHeaders(COUNTRY)));

    result.andExpect(status().isOk());

    final List<Message> messages = readMessages(getPromotionMultiVendorQueue(COUNTRY));

    Assertions.assertEquals(1, messages.size());
    PromotionSingleVendorResponse promotionRequest =
        objectMapper.readValue(
            new String(messages.get(0).getBody()), PromotionSingleVendorResponse.class);

    PromotionSingleVendor promotionSingleVendor = promotionRequest.getPayload().get(0);
    Assertions.assertEquals(PromotionType.FREE_GOOD, promotionSingleVendor.getType());
    Assertions.assertEquals(
        HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG, promotionSingleVendor.getImage());
    Assertions.assertEquals(
        STAR_DATE_2019_03_31T23_59_59_999Z, promotionSingleVendor.getStartDate());
    Assertions.assertEquals(END_DATE_2020_03_31T23_59_59_999Z, promotionSingleVendor.getEndDate());
    Assertions.assertEquals(
        PromotionMocks.TITLE_BUY_3_GET_1_FREE, promotionSingleVendor.getTitle());
    Assertions.assertEquals(DESCRIPTION_BUY_5_GET_7_BUY_10, promotionSingleVendor.getDescription());
  }

  @Test
  public void createNotPromotionWithoutPromotionTypeThenReturnBadRequest() throws Exception {

    final PromotionSingleVendorRequest request =
        getPromotionSingleVendorRequestWithoutPromotionTypeMock();

    final ResultActions result =
        mockMvc.perform(post(URL_V2, request).headers(buildHeaders(COUNTRY)));

    result.andExpect(status().isBadRequest());
  }

  @Test
  public void createNotPromotionWithoutTitleThenReturnBadRequest() throws Exception {

    final PromotionSingleVendorRequest request = getPromotionSingleVendorWithoutTitleRequestMock();

    final ResultActions result =
        mockMvc.perform(post(URL_V2, request).headers(buildHeaders(COUNTRY)));

    result.andExpect(status().isBadRequest());
  }

  @Test
  public void shouldFailWithInvalidCountry() throws Exception {

    final PromotionSingleVendorRequest request = getPromotionSingleVendorRequestMock();

    final ResultActions result =
        mockMvc.perform(post(URL_V2, request).headers(buildHeaders("INVALID")));

    result.andExpect(status().isNotFound());
  }

  @Test
  public void shouldFailWithCountryWithoutDefaultVendor() throws Exception {

    final PromotionSingleVendorRequest request = getPromotionSingleVendorRequestMock();

    final ResultActions result =
        mockMvc.perform(
            post(URL_V2, request).headers(buildHeaders(COUNTRY_WITHOUT_DEFAULT_VENDOR)));

    result.andExpect(status().isBadRequest());
  }

  private static HttpHeaders buildHeaders(final String country) {
    return HeaderBuilder.builder()
        .withRequestTraceId(PromotionMocks.MOCKED_REQUEST_TRACE_ID)
        .withCountry(country)
        .withTimesTamp(String.valueOf(PromotionMocks.TIMESTAMP))
        .build();
  }
}
