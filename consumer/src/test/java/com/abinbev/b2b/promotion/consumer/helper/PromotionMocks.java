package com.abinbev.b2b.promotion.consumer.helper;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.API_PROMOTION_VERSION_V2;

import com.abinbev.b2b.promotion.consumer.domain.PromotionAccountModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionAccountMultiVendorModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionModel;
import com.abinbev.b2b.promotion.consumer.domain.PromotionMultiVendorModel;
import com.abinbev.b2b.promotion.consumer.domain.Translation;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import com.abinbev.b2b.promotion.consumer.listener.message.PromotionMultiVendorMessage;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountDeleteVo;
import com.abinbev.b2b.promotion.consumer.vo.PromotionListVo;
import com.abinbev.b2b.promotion.consumer.vo.PromotionVo;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionMocks {

  public static final String ACCOUNT_ID = "AC100";
  public static final String ID = "182";
  public static final String ID2 = "999";
  public static final String PROMOTION_ID = "0001";
  public static final Long TIMESTAMP = System.currentTimeMillis();
  public static final String REQUEST_TRACE_ID = "0123456789";
  public static final String PARENT_REQUEST_TRACE_ID = "01234";
  public static final String COUNTRY = "DO";
  public static final String COUNTRY_DISABLED = "RU";

  public static final String TITLE = "title";
  public static final String DESCRIPTION = "desc";
  public static final OffsetDateTime UPDATE_AT = OffsetDateTime.now();
  public static final BigDecimal BUDGET = BigDecimal.TEN;
  public static final String START_DATE = "2020-01-01T00:00:00Z";
  public static final String END_DATE = "2040-01-01T00:00:00Z";
  public static final String VENDOR_PROMOTION_ID = "promotionVendorId";
  public static final String IMAGE = "image";
  public static final Integer QUANTITY_LIMIT = 10;
  public static final String VENDOR_ID = "vendorId";
  public static final String VENDOR_ID_001 = "001";
  public static final String EN_US = "en-US";

  public static final String MOCKED_REQUEST_TRACE_ID = REQUEST_TRACE_ID;
  public static final String MOCKED_COUNTRY = COUNTRY;

  static final String MOCKED_AUTHORIZATION = "Bearer 1234567890.1234567890.1234567890";

  public static final String GENERIC_PROMOTION_COLLECTION = "-Promotions";
  public static final String GENERIC_PROMOTION_ACCOUNTS_COLLECTION = "_PromotionAccounts";

  public static PromotionModel getPromotionModel() {
    return PromotionModel.newBuilder()
        .withId(ID)
        .withPromotionId(ID)
        .withUpdateAt(UPDATE_AT)
        .withCreateAt(UPDATE_AT)
        .build();
  }

  public static PromotionModel getPromotionModelWithId2() {
    return PromotionModel.newBuilder()
        .withId(ID2)
        .withPromotionId(ID)
        .withUpdateAt(UPDATE_AT)
        .withCreateAt(UPDATE_AT)
        .build();
  }

  public static PromotionMultiVendorModel getPromotionMultiVendorModel() {
    return PromotionMultiVendorModel.newBuilder()
        .withTitle(PromotionMocks.TITLE)
        .withDescription(PromotionMocks.DESCRIPTION)
        .withStartDate(OffsetDateTime.parse(PromotionMocks.START_DATE))
        .withEndDate(OffsetDateTime.parse(PromotionMocks.END_DATE))
        .withBudget(PromotionMocks.BUDGET)
        .withPromotionType(com.abinbev.b2b.promotion.consumer.domain.PromotionType.DISCOUNT)
        .withVendorPromotionId(PromotionMocks.VENDOR_PROMOTION_ID)
        .withImage(PromotionMocks.IMAGE)
        .withQuantityLimit(PromotionMocks.QUANTITY_LIMIT)
        .build();
  }

  public static PromotionAccountModel getPromotionAccountModel() {
    return PromotionAccountModel.newBuilder()
        .withPromotionId(ID)
        .withUpdateAt(UPDATE_AT)
        .withAccountId(ACCOUNT_ID)
        .build();
  }

  public static PromotionAccountMultiVendorModel getPromotionAccountMultiVendorModel() {
    return PromotionAccountMultiVendorModel.newBuilder()
        .withVendorPromotionId(ID)
        .withUpdateAt(UPDATE_AT)
        .withVendorAccountId(ACCOUNT_ID)
        .build();
  }

  public static PromotionAccountDeleteVo getPromotionAccountDeleteVoMock() {

    final List<String> promotions = new ArrayList<>();
    promotions.add("PROMO1");

    final PromotionAccountDeleteVo promotionAccountDeleteVo = new PromotionAccountDeleteVo();
    promotionAccountDeleteVo.setAccounts(Collections.singletonList("ACC1"));
    promotionAccountDeleteVo.setPromotions(promotions);

    return promotionAccountDeleteVo;
  }

  public static PromotionAccountDeleteVo getPromotionsAccountDeleteVoMock() {
    final PromotionAccountDeleteVo promotionAccountDeleteVo = getPromotionAccountDeleteVoMock();
    promotionAccountDeleteVo.getPromotions().add("PROMO2");
    promotionAccountDeleteVo.getPromotions().add("PROMO3");
    return promotionAccountDeleteVo;
  }

  public static PromotionAccountDeleteVo getPromotionAccountDeleteVoMockWithoutAccount() {

    List<String> promotions = new ArrayList<>();
    promotions.add("PROMO1");

    PromotionAccountDeleteVo promotionAccountDeleteVo = new PromotionAccountDeleteVo();
    promotionAccountDeleteVo.setAccounts(null);
    promotionAccountDeleteVo.setPromotions(promotions);

    return promotionAccountDeleteVo;
  }

  public static BaseMessage getDeletePromotionVoListMessageMock() {

    BaseMessage message = new BaseMessage();
    message.setPayload(getPromotionAccountDeleteVoMock());
    message.setVersion(API_PROMOTION_VERSION_V2);
    message.setOperation("DELETE");

    return message;
  }

  public static BaseMessage getDeletePromotionVoListWithoutAccountMessageMock() {

    BaseMessage message = new BaseMessage();
    message.setPayload(getPromotionAccountDeleteVoMockWithoutAccount());
    message.setVersion(API_PROMOTION_VERSION_V2);
    message.setOperation("DELETE");

    return message;
  }

  public static PromotionVo getPromotionVoMock() {

    PromotionVo promotionVo = new PromotionVo();

    promotionVo.setId(ID);
    promotionVo.setPromotionId(PROMOTION_ID);
    promotionVo.setType(com.abinbev.b2b.promotion.consumer.domain.PromotionType.DISCOUNT);
    promotionVo.setTitle(TITLE);
    promotionVo.setDescription(DESCRIPTION);
    promotionVo.setBudget(BigDecimal.ONE);
    promotionVo.setStartDate(UPDATE_AT.toString());
    promotionVo.setEndDate(UPDATE_AT.toString());

    return promotionVo;
  }

  public static PromotionListVo getPromotionVoListMockValidator() {
    PromotionListVo promotionListVo = new PromotionListVo();
    promotionListVo.setPromotionVos(Arrays.asList(getPromotionVoMock()));
    return promotionListVo;
  }

  public static Map<String, Translation> getTranslations() {
    final Map<String, Translation> translations = new HashMap<>();
    translations.put(
        EN_US,
        new Translation(PromotionMocks.TITLE, PromotionMocks.DESCRIPTION, PromotionMocks.IMAGE));
    return translations;
  }
}
