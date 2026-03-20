package com.abinbev.b2b.promotion.relay.integration;

import static com.abinbev.b2b.promotion.relay.integration.config.MockMvcHelper.defaultHeaders;
import static com.abinbev.b2b.promotion.relay.integration.config.MockMvcHelper.headersInvalidAuthorization;
import static com.abinbev.b2b.promotion.relay.integration.config.MockMvcHelper.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import com.abinbev.b2b.promotion.relay.domain.Translation;
import com.abinbev.b2b.promotion.relay.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.relay.integration.config.BaseIntegrationTest;
import com.abinbev.b2b.promotion.relay.integration.config.IntegrationTest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorRequest;
import com.abinbev.b2b.promotion.relay.templates.PromotionMultiVendorRequestTemplate;
import com.abinbev.b2b.promotion.relay.templates.TranslationTemplate;
import java.util.Collections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@IntegrationTest
public class PromotionMultiVendorIntegrationTest extends BaseIntegrationTest {

  @Autowired private MockMvc mockMvc;

  private static final String URL = "/v3/promotions";

  @BeforeAll
  public static void fixtureSetUp() {
    FixtureFactoryLoader.loadTemplates(PromotionMultiVendorRequestTemplate.class.getPackageName());
  }

  @BeforeEach
  public void setup() {
    purge(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY));
    purge(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY_US));
  }

  @Test
  public void shouldDispatchCreatePromotion() throws Exception {
    final PromotionMultiVendor promotion =
        Fixture.from(PromotionMultiVendor.class).gimme(PromotionMultiVendorRequestTemplate.BASE);
    final PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.add(promotion);

    final ResultActions result = mockMvc.perform(post(URL, request).headers(defaultHeaders()));
    result.andExpect(status().isAccepted());

    assertThat(readMessages(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY))).hasSize(1);
  }

  @Test
  public void shouldFailWithInvalidAuthorization() throws Exception {
    final PromotionMultiVendor promotion =
        Fixture.from(PromotionMultiVendor.class).gimme(PromotionMultiVendorRequestTemplate.BASE);
    final PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.add(promotion);

    final ResultActions result =
        mockMvc.perform(post(URL, request).headers(headersInvalidAuthorization()));
    result.andExpect(status().isForbidden());

    assertThat(readMessages(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY_US))).hasSize(0);
  }

  @Test
  public void shouldDispatchCreatePromotionWithTranslations() throws Exception {
    final PromotionMultiVendor promotion =
        Fixture.from(PromotionMultiVendor.class).gimme(PromotionMultiVendorRequestTemplate.BASE);
    final Translation translation =
        Fixture.from(Translation.class).gimme(TranslationTemplate.US_VALID);
    promotion.setTranslations(Collections.singletonMap("en-US", translation));
    final PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.add(promotion);

    final ResultActions result =
        mockMvc.perform(post(URL, request).headers(defaultHeaders(PromotionMocks.COUNTRY_US)));
    result.andExpect(status().isAccepted());

    assertThat(readMessages(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY_US))).hasSize(1);
  }

  @Test
  public void shouldDispatchCreatePromotionWithNonOfficialLanguageInTranslations()
      throws Exception {
    final PromotionMultiVendor promotion =
        Fixture.from(PromotionMultiVendor.class)
            .gimme(PromotionMultiVendorRequestTemplate.WITH_NON_OFFICIAL_DEFAULT_LANGUAGE);
    final Translation translation =
        Fixture.from(Translation.class).gimme(TranslationTemplate.US_VALID);
    promotion.setTranslations(Collections.singletonMap("en-ES", translation));
    final PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.add(promotion);

    final ResultActions result =
        mockMvc.perform(post(URL, request).headers(defaultHeaders(PromotionMocks.COUNTRY_US)));
    result.andExpect(status().isAccepted());

    assertThat(readMessages(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY_US))).hasSize(1);
  }

  @Test
  public void shouldFailWithInvalidTranslations() throws Exception {
    final PromotionMultiVendor promotion =
        Fixture.from(PromotionMultiVendor.class).gimme(PromotionMultiVendorRequestTemplate.BASE);
    final Translation translation =
        Fixture.from(Translation.class).gimme(TranslationTemplate.US_INVALID);
    promotion.setTranslations(Collections.singletonMap("en-US", translation));
    final PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.add(promotion);

    final ResultActions result =
        mockMvc.perform(post(URL, request).headers(defaultHeaders(PromotionMocks.COUNTRY_US)));
    result.andExpect(status().isBadRequest());

    assertThat(readMessages(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY_US))).hasSize(0);
  }

  @Test
  public void shouldPassWithDefaultLanguage() throws Exception {
    final PromotionMultiVendor promotion =
        Fixture.from(PromotionMultiVendor.class)
            .gimme(PromotionMultiVendorRequestTemplate.WITH_VALID_DEFAULT_LANGUAGE);
    final PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.add(promotion);

    final ResultActions result =
        mockMvc.perform(post(URL, request).headers(defaultHeaders(PromotionMocks.COUNTRY_US)));
    result.andExpect(status().isAccepted());

    assertThat(readMessages(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY_US))).hasSize(1);
  }

  @Test
  public void shouldFailWithInvalidDefaultLanguage() throws Exception {
    final PromotionMultiVendor promotion =
        Fixture.from(PromotionMultiVendor.class)
            .gimme(PromotionMultiVendorRequestTemplate.WITH_INVALID_DEFAULT_LANGUAGE);
    final PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.add(promotion);

    final ResultActions result =
        mockMvc.perform(post(URL, request).headers(defaultHeaders(PromotionMocks.COUNTRY_US)));
    result.andExpect(status().isBadRequest());

    assertThat(readMessages(getPromotionMultiVendorQueue(PromotionMocks.COUNTRY_US))).hasSize(0);
  }
}
