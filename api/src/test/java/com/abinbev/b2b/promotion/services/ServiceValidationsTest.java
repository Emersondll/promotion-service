package com.abinbev.b2b.promotion.services;

import com.abinbev.b2b.promotion.domain.model.Pagination;
import com.abinbev.b2b.promotion.exceptions.NotFoundException;
import com.abinbev.b2b.promotion.helpers.PromotionMocks;
import com.abinbev.b2b.promotion.rest.vo.GetPromotionsVO;
import com.abinbev.b2b.promotion.rest.vo.PromotionResponse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ServiceValidationsTest {

  @Test
  public void testConstructor()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
          InstantiationException {

    final Constructor<ServiceValidations> constructor =
        ServiceValidations.class.getDeclaredConstructor();
    constructor.setAccessible(true);
    Assertions.assertNotNull(constructor.newInstance());
  }

  @Test
  public void testValidatePromotionsValid() {

    final List<PromotionResponse> promotionResponseList =
        PromotionMocks.getPromotionResponseListMock(null);
    final GetPromotionsVO getPromotionsVO =
        new GetPromotionsVO(promotionResponseList, new Pagination(1, 10));
    ServiceValidations.validatePromotionsNotFound(getPromotionsVO);
  }

  @Test
  public void testValidatePromotionsNotFound() {

    Assertions.assertThrows(
        NotFoundException.class,
        () -> {
          final List<PromotionResponse> promotionResponseList = new ArrayList<>();
          final GetPromotionsVO getPromotionsVO =
              new GetPromotionsVO(promotionResponseList, new Pagination(1, 10));
          ServiceValidations.validatePromotionsNotFound(getPromotionsVO);
        });
  }
}
