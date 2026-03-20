package com.abinbev.b2b.promotion.consumer.listener;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.API_PROMOTION_VERSION_V2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMocks;
import com.abinbev.b2b.promotion.consumer.listener.message.BaseMessage;
import com.abinbev.b2b.promotion.consumer.service.PromotionService;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountDeleteVo;
import com.abinbev.b2b.promotion.consumer.vo.PromotionListVo;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PromotionListenerTest {

  @InjectMocks private PromotionListener promotionListener;

  @Mock private PromotionService promotionService;

  @Test
  public void testListenerCreateServiceCall() {
    final List<PromotionListVo> payload = new ArrayList<>();
    final BaseMessage<List<PromotionListVo>> message = new BaseMessage<>();
    message.setCountry(PromotionMocks.COUNTRY);
    message.setPayload(payload);
    message.setOperation(ApiConstants.POST_OPERATION);
    message.setVersion(API_PROMOTION_VERSION_V2);
    promotionListener.receive(message);
    verify(promotionService, times(1))
        .createPromotions(any(), eq(message.getCountry()), eq(message.getTimestamp()));
  }

  @Test
  public void testListenerDeleteServiceCall() {
    final PromotionAccountDeleteVo payload = new PromotionAccountDeleteVo();
    final BaseMessage<PromotionAccountDeleteVo> message = new BaseMessage<>();
    message.setCountry(PromotionMocks.COUNTRY);
    message.setPayload(payload);
    message.setOperation(ApiConstants.DELETE_OPERATION);
    message.setVersion(API_PROMOTION_VERSION_V2);
    promotionListener.receive(message);
    verify(promotionService, times(1)).deletePromotion(any(BaseMessage.class));
  }

  @Test
  public void testListenerNoOperation() {
    final PromotionAccountDeleteVo payload = new PromotionAccountDeleteVo();
    final BaseMessage<PromotionAccountDeleteVo> message = new BaseMessage<>();
    message.setCountry(PromotionMocks.COUNTRY);
    message.setPayload(payload);
    promotionListener.receive(message);
    verify(promotionService, times(0)).deletePromotion(any(BaseMessage.class));

    verify(promotionService, times(0))
        .createPromotions(any(), eq(message.getCountry()), eq(message.getTimestamp()));
  }
}
