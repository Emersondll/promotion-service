package com.abinbev.b2b.promotion.v2.mapper;

import static com.abinbev.b2b.promotion.helpers.PromotionMocks.ACCEPT_LANGUAGE_FR_CA;
import static com.abinbev.b2b.promotion.helpers.PromotionMultiVendorMockBuilder.FR_LANGUAGE;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.rest.vo.PromotionResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PromotionMapperTest {

  private PromotionMapperBuilder mapper;

  @BeforeEach
  public void setUp() {
    mapper = Mappers.getMapper(PromotionMapperBuilder.class);
  }

  @Test
  public void shouldMapToSingleVendorResponseForPromotion() {

    final PromotionMultivendor promotionMultivendor = PromotionMocks.getPromotionsSingleVendor();

    final PromotionResponse promotionMultiVendorResponse =
        mapper.mapToSingleVendorResponseForPromotion(promotionMultivendor, null);

    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getPromotionId());
    Assertions.assertEquals(
        promotionMultivendor.getTitle(), promotionMultiVendorResponse.getTitle());
    Assertions.assertEquals(
        promotionMultivendor.getDescription(), promotionMultiVendorResponse.getDescription());
    Assertions.assertEquals(PromotionType.DISCOUNT, promotionMultivendor.getPromotionType());
    Assertions.assertEquals(
        promotionMultivendor.getImage(), promotionMultiVendorResponse.getImage());
    Assertions.assertEquals(
        promotionMultivendor.getEndDate().toString(), promotionMultiVendorResponse.getEndDate());
    Assertions.assertEquals(
        promotionMultivendor.getUpdateAt().toString(), promotionMultiVendorResponse.getUpdatedAt());
    Assertions.assertEquals(
        promotionMultivendor.getStartDate().toString(),
        promotionMultiVendorResponse.getStartDate());
    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getVendorPromotionId());
    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getPromotionId());
    Assertions.assertEquals(
        promotionMultiVendorResponse.getPromotionId(),
        promotionMultiVendorResponse.getVendorPromotionId());
    Assertions.assertNull(promotionMultiVendorResponse.getVendorId());
  }

  @Test
  public void shouldMapToSingleVendorResponseForPromotionWithAcceptLanguage() {

    final PromotionMultivendor promotionMultivendor = PromotionMocks.getPromotionsSingleVendor();

    final PromotionResponse promotionMultiVendorResponse =
        mapper.mapToSingleVendorResponseForPromotion(promotionMultivendor, ACCEPT_LANGUAGE_FR_CA);

    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getPromotionId());
    Assertions.assertNotEquals(
        promotionMultivendor.getTitle(), promotionMultiVendorResponse.getTitle());
    Assertions.assertNotEquals(
        promotionMultivendor.getDescription(), promotionMultiVendorResponse.getDescription());
    Assertions.assertEquals(PromotionType.DISCOUNT, promotionMultivendor.getPromotionType());
    Assertions.assertEquals(
        promotionMultivendor.getEndDate().toString(), promotionMultiVendorResponse.getEndDate());
    Assertions.assertEquals(
        promotionMultivendor.getUpdateAt().toString(), promotionMultiVendorResponse.getUpdatedAt());
    Assertions.assertEquals(
        promotionMultivendor.getStartDate().toString(),
        promotionMultiVendorResponse.getStartDate());
    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getVendorPromotionId());
    Assertions.assertTrue(promotionMultiVendorResponse.getDescription().contains(FR_LANGUAGE));
    Assertions.assertTrue(promotionMultiVendorResponse.getTitle().contains(FR_LANGUAGE));
    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getVendorPromotionId());
    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getPromotionId());
    Assertions.assertEquals(
        promotionMultiVendorResponse.getPromotionId(),
        promotionMultiVendorResponse.getVendorPromotionId());
    Assertions.assertNull(promotionMultiVendorResponse.getVendorId());
  }

  @Test
  public void shouldMapToMultiVendorResponseForPromotion() {

    final PromotionMultivendor promotionMultivendor = PromotionMocks.getPromotionsMultiVendor();

    final PromotionResponse promotionMultiVendorResponse =
        mapper.mapToMultiVendorResponseForPromotion(promotionMultivendor, null);

    Assertions.assertEquals(
        promotionMultivendor.getId(), promotionMultiVendorResponse.getPromotionId());
    Assertions.assertEquals(
        promotionMultivendor.getTitle(), promotionMultiVendorResponse.getTitle());
    Assertions.assertEquals(
        promotionMultivendor.getDescription(), promotionMultiVendorResponse.getDescription());
    Assertions.assertEquals(PromotionType.DISCOUNT, promotionMultivendor.getPromotionType());
    Assertions.assertEquals(
        promotionMultivendor.getImage(), promotionMultiVendorResponse.getImage());
    Assertions.assertEquals(
        promotionMultivendor.getEndDate().toString(), promotionMultiVendorResponse.getEndDate());
    Assertions.assertEquals(
        promotionMultivendor.getUpdateAt().toString(), promotionMultiVendorResponse.getUpdatedAt());
    Assertions.assertEquals(
        promotionMultivendor.getStartDate().toString(),
        promotionMultiVendorResponse.getStartDate());
    Assertions.assertEquals(
        promotionMultivendor.getVendorPromotionId(),
        promotionMultiVendorResponse.getVendorPromotionId());
    Assertions.assertEquals(
        promotionMultivendor.getVendorId(), promotionMultiVendorResponse.getVendorId());
  }
}
