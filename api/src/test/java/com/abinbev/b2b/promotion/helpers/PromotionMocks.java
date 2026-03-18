package com.abinbev.b2b.promotion.helpers;

import static com.abinbev.b2b.promotion.helpers.PromotionMultiVendorMockBuilder.FR_LANGUAGE;

import com.abinbev.b2b.promotion.domain.model.PromotionType;
import com.abinbev.b2b.promotion.rest.vo.PromotionResponse;
import com.abinbev.b2b.promotion.v2.domain.model.Promotion;
import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.domain.model.Translation;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PromotionMocks {

  public static final String ID = "182";
  public static final String PROMOTION_ID = "PROMO100";
  public static final String ACCOUNT_ID = "AC100";
  public static final String ACCOUNT_ID_2 = "AC200";
  public static final String ACCOUNT_GROUP_ID = "AC1000";
  public static final String SKU_GROUP_ID = "SK1000";
  public static final String COUNTRY_ZA = "ZA";
  public static final String COUNTRY_US = "US";
  public static final String COUNTRY_BR = "BR";
  public static final String COUNTRY_CA = "CA";
  public static final String COUNTRY_DO = "DO";
  public static final Boolean INCLUDE_DISABLED = false;
  public static final Boolean INCLUDE_DISABLED_TRUE = true;
  public static final String VENDOR_ID = "VENDOR-001";
  public static final String VENDOR_ID_2 = "VENDOR-002";
  public static final String PROMOTION_PLATFORM_ID_1 =
      "V0ljTXZIZ0pUaGk2V1poclNaTElRZz09O0RNLTM2MDcw";
  public static final String PROMOTION_PLATFORM_ID_2 =
      "V0ljTXZIZ0pUaGk2V1poclNaTElRZz09O0RNLTM5MTc0";
  public static final String VENDOR_PROMOTION_ID_DISCOUNT =
      "VENDOR-PROMOTION-ID-".concat(PromotionType.DISCOUNT.name());
  public static final String US_PROMOTION_COLLECTION = "US-Promotions";
  public static final String DO_PROMOTION_COLLECTION = "DO-Promotions";
  public static final Integer PAGE = 0;
  public static final Integer PAGE_SIZE = 50;
  public static final String REQUEST_TRACE_ID = "REQUEST-TRACE-ID-PROMOTIONS";
  public static final String ACCEPT_LANGUAGE_EN_US = "en-US";
  public static final String ACCEPT_LANGUAGE_ES_US = "es-US";
  public static final String ACCEPT_LANGUAGE_FR_CA = "fr-CA";

  private static final OffsetDateTime CREATE_AT = OffsetDateTime.now();
  public static final Long UPDATED_SINCE = 1L;

  public static final PromotionType PROMOTION_TYPE = PromotionType.DISCOUNT;
  private static final PromotionType PROMOTION_TYPE_FREEGOOD = PromotionType.FREE_GOOD;
  private static final String TITLE = "title";
  private static final String DESCRIPTION = "desc";
  private static final Integer PROMOTIONS_RANKING = 1;
  public static final Integer QUANTITY_LIMIT = 10;
  private static final String IMAGE =
      "https://www.socioscerveceria.com.do/media/catalog/product//b/a/barcelo_dark_series_350_ml_-_unidad_1_botella_dar_siri_.png";

  public static final String SKU = "123";
  private static final Promotion promotion = new Promotion();

  public static final String GENERIC_PROMOTION_COLLECTION = "-Promotions";

  private static <T> List<T> toList(Set<T> list) {
    return list.stream().collect(Collectors.toList());
  }

  public static Set<String> getAccountGroupIdSet() {
    return new HashSet(Arrays.asList(ACCOUNT_GROUP_ID));
  }

  public static Set<String> getSkuGroupIdSet() {
    return new HashSet(Arrays.asList("SK1000"));
  }

  public static Set<String> getFreeGoodGroupIdSet() {
    return new HashSet(Arrays.asList("FR1000"));
  }

  public static Promotion getPromotion2Mock() {

    promotion.setId(ID);
    promotion.setDeleted(false);
    promotion.setPromotionType(PROMOTION_TYPE_FREEGOOD);
    promotion.setTitle(TITLE);
    promotion.setDescription(DESCRIPTION);
    promotion.setAccountGroups(toList(getAccountGroupIdSet()));
    promotion.setSkuGroups(toList(getSkuGroupIdSet()));
    promotion.setFreeGoodGroups(toList(getFreeGoodGroupIdSet()));
    promotion.setPromotionsRanking(PROMOTIONS_RANKING);
    promotion.getScore().put(ACCOUNT_ID, new BigDecimal(100));
    promotion.setBudget(BigDecimal.ONE);
    promotion.setDisabled(false);
    promotion.setQuantityLimit(QUANTITY_LIMIT);
    promotion.setImage(IMAGE);
    return promotion;
  }

  public static List<PromotionResponse> getPromotionResponseListMock(final String accountId) {

    return PromotionHelper.promotionsModelToPromotionsResponse(
        Arrays.asList(getPromotion2Mock()), accountId);
  }

  public static PromotionMultivendor getPromotionsSingleVendor() {

    PromotionMultivendor promotionMultivendor = new PromotionMultivendor();

    promotionMultivendor.setId(ID);
    promotionMultivendor.setTitle(TITLE);
    promotionMultivendor.setDescription(DESCRIPTION);
    promotionMultivendor.setPromotionType(PromotionType.DISCOUNT);
    promotionMultivendor.setImage(IMAGE);
    promotionMultivendor.setStartDate(OffsetDateTime.now());
    promotionMultivendor.setEndDate(OffsetDateTime.now());
    promotionMultivendor.setUpdateAt(CREATE_AT);
    promotionMultivendor.setTranslations(
        Map.of(
            PromotionMocks.ACCEPT_LANGUAGE_FR_CA,
            new Translation(TITLE.concat(FR_LANGUAGE), DESCRIPTION.concat(FR_LANGUAGE), null)));
    return promotionMultivendor;
  }

  public static PromotionMultivendor getPromotionsMultiVendor() {

    PromotionMultivendor promotionMultivendor = new PromotionMultivendor();

    promotionMultivendor.setId(ID);
    promotionMultivendor.setTitle(TITLE);
    promotionMultivendor.setVendorId(VENDOR_ID);
    promotionMultivendor.setVendorPromotionId(VENDOR_PROMOTION_ID_DISCOUNT);
    promotionMultivendor.setDescription(DESCRIPTION);
    promotionMultivendor.setPromotionType(PromotionType.DISCOUNT);
    promotionMultivendor.setImage(IMAGE);
    promotionMultivendor.setStartDate(OffsetDateTime.now());
    promotionMultivendor.setEndDate(OffsetDateTime.now());
    promotionMultivendor.setUpdateAt(CREATE_AT);

    return promotionMultivendor;
  }
}
