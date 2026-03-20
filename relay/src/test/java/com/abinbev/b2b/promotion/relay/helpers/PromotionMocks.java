package com.abinbev.b2b.promotion.relay.helpers;

import com.abinbev.b2b.promotion.relay.classes.BaseMessage;
import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import com.abinbev.b2b.promotion.relay.domain.PromotionType;
import com.abinbev.b2b.promotion.relay.domain.Translation;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionMultiVendorRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorDeleteRequest;
import com.abinbev.b2b.promotion.relay.rest.vo.PromotionSingleVendorRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PromotionMocks {

  public static final String HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG =
      "https://www.sociocerveceria.com.do/image.png";
  public static final String END_DATE_2020_03_31T23_59_59_999Z = "2020-03-31T23:59:59.999Z";
  public static final String STAR_DATE_2019_03_31T23_59_59_999Z = "2019-03-31T23:59:59.999Z";
  public static final String DESCRIPTION_BUY_5_GET_7_BUY_10 =
      "Buy 5, get 7 - Buy 10, get 12 or discounts";
  public static final String TITLE_BUY_3_GET_1_FREE = "Buy 3 get 1 free";
  public static final Long TIMESTAMP = System.currentTimeMillis();
  public static final Long TIMESTAMP_10 = 1619784530L;
  public static final Long TIMESTAMP_SECS = Long.parseLong("1572351275");
  public static final String REQUEST_TRACE_ID = "012345";
  public static final String PARENT_REQUEST_TRACE_ID = "0123";
  public static final String MOCKED_REQUEST_TRACE_ID = "123";
  public static final String COUNTRY = "DO";
  public static final String COUNTRY_WITHOUT_DEFAULT_VENDOR = "PY";
  public static final String COUNTRY_US = "US";
  public static final String AUTHORIZATION = "BEARER";
  public static final String AUTHORIZATION_WITH_VENDOR_ID =
      "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJCVVdYcTRLanhPMUwxdTFTaENJVVNrdEk5aXRreFJ0X1Zzb3luelVvVGFFIn0.eyJleHAiOjE2NDgwNDEzMTksImlhdCI6MTY0ODAzNzcxOSwianRpIjoiZTlkMTA3NDktNDc5YS00ZDQzLTg5NjYtYTk5MjE5MThlNzY0IiwiaXNzIjoiaHR0cDovL2tleWNsb2FrLXNlcnZpY2UvYXV0aC9yZWFsbXMvYmVlcy1yZWFsbSIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI1NmQ4MTNhYi01ODkxLTRiOTgtYjMwNS0xNDFiZjExNmI3ZjYiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJjYmQ1ZDIxMC1mYjkxLTQ0NzAtYTUwZC03NjRhMzEyNWRlYzYiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTI3LjAuMC42IiwiY2xpZW50SWQiOiJjYmQ1ZDIxMC1mYjkxLTQ0NzAtYTUwZC03NjRhMzEyNWRlYzYiLCJyb2xlcyI6WyJXcml0ZSIsIlJlYWQiXSwidmVuZG9ySWQiOiI1YmQ1OTkxMC1jMDMyLTQwMDAtOTEwYS1mMWYxNjRkNmMwNjciLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtY2JkNWQyMTAtZmI5MS00NDcwLWE1MGQtNzY0YTMxMjVkZWM2IiwiY2xpZW50QWRkcmVzcyI6IjEyNy4wLjAuNiJ9.chxQk62-i6PvSleqkuA6qGzlRdn2qlnvAiDWZM4Xf_JXEZtEeBIejW_F1KruHTRgtREA7A2gBTfdtEKUnjgS4YQtu0xHpuaNJPkloZk9o5SI5qaNo1J69dt282JiIeGNkJd3gRzobt6WXh43m-wqmTSDeAcQHGcxmq6-m-geQGVi0EyJderhAyS8BAeM3_ms2AmELWTeB5qHoMl_G_NGTfE8P-vSous-wPPvjb9jeOy8bLfEamMiAoPFKs81ck-asRxc9flfaS9ld1HOTjE_PMVrlhwRdtmhIOLpX9Zlo-AeVA4EZ2yoALId57CVuReSB1T_9CaDg2V5gkzFsQef2A";
  public static final String PROMOTION_QUEUE_NAME = "promotion-free-good-group-do";

  public static final String ACCOUNT_GROUP_ROUTING_KEY = "do";
  public static final String ACCOUNT_GROUP_EXCHANGE = "promotion-account-group.exchange";

  public static final String PROMOTION_ID = "123";

  public static final String ACCOUNT_ID = "111111";

  public static final String VENDOR_ID = "2323232";

  public static final String VENDOR_PROMOTION_ID_1 = "VP-ID-001";
  public static final String VENDOR_PROMOTION_ID_2 = "VP-ID-002";
  public static final String VENDOR_ACCOUNT_ID_1 = "VA-ID-001";
  public static final String VENDOR_ACCOUNT_ID_2 = "VA-ID-002";

  public static final BaseMessage BASE_MESSAGE =
      new BaseMessage(COUNTRY, REQUEST_TRACE_ID, TIMESTAMP, PARENT_REQUEST_TRACE_ID);

  public static final BaseMessage BASE_MESSAGE_NO_PARENT_REQUEST_TRACE_ID =
      new BaseMessage(COUNTRY, REQUEST_TRACE_ID, TIMESTAMP);

  public static BaseMessage getPostMessageMock() {
    BASE_MESSAGE.setOperation("POST");
    BASE_MESSAGE.setPayload("");
    return BASE_MESSAGE;
  }

  public static PromotionSingleVendor getPromotionSingleVendorMock() {

    PromotionSingleVendor promotion = new PromotionSingleVendor();

    promotion.setId(UUID.randomUUID().toString());
    promotion.setType(PromotionType.FREE_GOOD);
    promotion.setTitle(TITLE_BUY_3_GET_1_FREE);
    promotion.setDescription(DESCRIPTION_BUY_5_GET_7_BUY_10);
    promotion.setStartDate(STAR_DATE_2019_03_31T23_59_59_999Z);
    promotion.setEndDate(END_DATE_2020_03_31T23_59_59_999Z);
    promotion.setBudget(BigDecimal.ONE);
    promotion.setQuantityLimit(1);
    promotion.setImage(HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG);

    return promotion;
  }

  public static PromotionSingleVendor getPromotionSingleVendorWithoutTitleMock() {

    PromotionSingleVendor promotion = new PromotionSingleVendor();

    promotion.setId(UUID.randomUUID().toString());
    promotion.setType(PromotionType.FREE_GOOD);
    promotion.setDescription(DESCRIPTION_BUY_5_GET_7_BUY_10);
    promotion.setStartDate(STAR_DATE_2019_03_31T23_59_59_999Z);
    promotion.setEndDate(END_DATE_2020_03_31T23_59_59_999Z);
    promotion.setBudget(BigDecimal.ONE);
    promotion.setQuantityLimit(1);
    promotion.setImage(HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG);

    return promotion;
  }

  public static PromotionSingleVendor getPromotionSingleVendorWithoutPromotionTypeMock() {

    PromotionSingleVendor promotion = new PromotionSingleVendor();

    promotion.setId(UUID.randomUUID().toString());
    promotion.setTitle(TITLE_BUY_3_GET_1_FREE);
    promotion.setDescription(DESCRIPTION_BUY_5_GET_7_BUY_10);
    promotion.setStartDate(STAR_DATE_2019_03_31T23_59_59_999Z);
    promotion.setEndDate(END_DATE_2020_03_31T23_59_59_999Z);
    promotion.setBudget(BigDecimal.ONE);
    promotion.setQuantityLimit(1);
    promotion.setImage(HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG);

    return promotion;
  }

  public static PromotionMultiVendor getPromotionMultiVendorMock() {

    PromotionMultiVendor promotion = new PromotionMultiVendor();

    promotion.setVendorPromotionId(UUID.randomUUID().toString());
    promotion.setType(PromotionType.FREE_GOOD);
    promotion.setTitle(TITLE_BUY_3_GET_1_FREE);
    promotion.setDescription(DESCRIPTION_BUY_5_GET_7_BUY_10);
    promotion.setStartDate(STAR_DATE_2019_03_31T23_59_59_999Z);
    promotion.setEndDate(END_DATE_2020_03_31T23_59_59_999Z);
    promotion.setBudget(BigDecimal.ONE);
    promotion.setQuantityLimit(1);
    promotion.setImage(HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG);
    final Map<String, Translation> translations = new HashMap<>();
    translations.put(
        "es-DO",
        new Translation(
            TITLE_BUY_3_GET_1_FREE,
            DESCRIPTION_BUY_5_GET_7_BUY_10,
            HTTPS_WWW_SOCIOCERVECERIA_COM_DO_IMAGE_PNG));
    promotion.setTranslations(translations);
    promotion.setDefaultLanguage("es-DO");

    return promotion;
  }

  public static PromotionSingleVendorRequest getPromotionSingleVendorRequestMock() {

    PromotionSingleVendorRequest request = new PromotionSingleVendorRequest();
    request.getList().add(getPromotionSingleVendorMock());

    return request;
  }

  public static PromotionSingleVendorRequest getPromotionSingleVendorWithoutTitleRequestMock() {

    PromotionSingleVendorRequest request = new PromotionSingleVendorRequest();
    request.getList().add(getPromotionSingleVendorWithoutTitleMock());

    return request;
  }

  public static PromotionSingleVendorRequest
      getPromotionSingleVendorRequestWithoutPromotionTypeMock() {

    PromotionSingleVendorRequest request = new PromotionSingleVendorRequest();
    request.getList().add(getPromotionSingleVendorWithoutPromotionTypeMock());

    return request;
  }

  public static PromotionMultiVendorRequest getPromotionMultiVendorRequestMock() {

    PromotionMultiVendorRequest request = new PromotionMultiVendorRequest();
    request.getList().add(getPromotionMultiVendorMock());

    return request;
  }

  public static PromotionMultiVendorDeleteRequest getPromotionMultiVendorDeleteRequestMock() {
    final PromotionMultiVendorDeleteRequest request = new PromotionMultiVendorDeleteRequest();
    request.setVendorPromotionIds(Arrays.asList(VENDOR_PROMOTION_ID_1, VENDOR_PROMOTION_ID_2));

    return request;
  }

  public static PromotionSingleVendorDeleteRequest getPromotionSingleVendorDeleteRequestMock() {
    PromotionSingleVendorDeleteRequest promotionSingleVendorDeleteRequest =
        new PromotionSingleVendorDeleteRequest();
    promotionSingleVendorDeleteRequest.setPromotions(
        Sets.newHashSet(Lists.newArrayList(PROMOTION_ID)));

    return promotionSingleVendorDeleteRequest;
  }
}
