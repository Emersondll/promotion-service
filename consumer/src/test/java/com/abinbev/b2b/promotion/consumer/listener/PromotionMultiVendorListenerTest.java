package com.abinbev.b2b.promotion.consumer.listener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMocks;
import com.abinbev.b2b.promotion.consumer.listener.message.PromotionMultiVendorMessage;
import com.abinbev.b2b.promotion.consumer.service.PromotionMultiVendorService;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountMultiVendorDeleteVO;
import com.abinbev.b2b.promotion.consumer.vo.PromotionMultiVendorVO;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PromotionMultiVendorListenerTest {

  @InjectMocks private PromotionMultiVendorListener promotionMultiVendorListener;

  @Mock private PromotionMultiVendorService promotionMultiVendorService;

  @Test
  public void testListenerServiceCall() {
    final List<PromotionMultiVendorVO> payload = new ArrayList<>();
    final PromotionMultiVendorMessage<List<PromotionMultiVendorVO>> message =
        new PromotionMultiVendorMessage<>();

    message.setRequestTraceId(PromotionMocks.REQUEST_TRACE_ID);
    message.setParentRequestTraceId(PromotionMocks.PARENT_REQUEST_TRACE_ID);
    message.setVendorId(PromotionMocks.VENDOR_ID);
    message.setCountry(PromotionMocks.COUNTRY);
    message.setPayload(payload);
    message.setOperation(ApiConstants.POST_OPERATION);
    message.setVersion(ApiConstants.API_PROMOTION_VERSION_V2);

    promotionMultiVendorListener.receive(message);
    verify(promotionMultiVendorService, times(1)).save(anyList(), eq(message));
  }

  @Test
  public void testListenerDeleteServiceCall() {
    final PromotionAccountMultiVendorDeleteVO payload = new PromotionAccountMultiVendorDeleteVO();

    final PromotionMultiVendorMessage<PromotionAccountMultiVendorDeleteVO> message =
        new PromotionMultiVendorMessage<>();
    message.setVendorId(PromotionMocks.VENDOR_ID);
    message.setCountry(PromotionMocks.COUNTRY);
    message.setPayload(payload);
    message.setOperation(ApiConstants.DELETE_OPERATION);

    promotionMultiVendorListener.receive(message);
    verify(promotionMultiVendorService, times(1))
            .remove(any(PromotionAccountMultiVendorDeleteVO.class), eq(message));
  }

  @Test
  public void testListenerNoOperation() {
    final PromotionAccountMultiVendorDeleteVO payload = new PromotionAccountMultiVendorDeleteVO();
    final PromotionMultiVendorMessage<PromotionAccountMultiVendorDeleteVO> message =
        new PromotionMultiVendorMessage<>();
    message.setVendorId(PromotionMocks.VENDOR_ID);
    message.setCountry(PromotionMocks.COUNTRY);
    message.setPayload(payload);

    promotionMultiVendorListener.receive(message);
    verify(promotionMultiVendorService, times(0))
        .remove(any(PromotionAccountMultiVendorDeleteVO.class), eq(message));
    verify(promotionMultiVendorService, times(0)).save(anyList(), eq(message));
  }
}
