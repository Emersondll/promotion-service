package com.abinbev.b2b.promotion.consumer.listener;

import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.CONTENT_TYPE_HEADER;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.COUNTRY_HEADER;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.METHOD;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.PARENT_REQUEST_TRACE_ID;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.REQUEST_TRACE_ID_HEADER;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.TIMESTAMP;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.VENDOR_ID;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.VERSION;
import static com.abinbev.b2b.promotion.consumer.constant.ApiConstants.X_TIMESTAMP_HEADER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.abinbev.b2b.promotion.consumer.constant.ApiConstants;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMocks;
import com.abinbev.b2b.promotion.consumer.listener.personalization.SyncPersonalizationPromotionMultiVendorListener;
import com.abinbev.b2b.promotion.consumer.service.PromotionMultiVendorService;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountMultiVendorDeleteVO;
import com.abinbev.b2b.promotion.consumer.vo.PromotionMultiVendorVO;

@ExtendWith(MockitoExtension.class)
class SyncPersonalizationPromotionMultiVendorListenerTest {

  @InjectMocks private SyncPersonalizationPromotionMultiVendorListener syncPersonalizationPromotionMultiVendorListener;

  @Mock private PromotionMultiVendorService promotionMultiVendorService;

  @Test
  void testListenerServiceCall() {
    final List<PromotionMultiVendorVO> payload = new ArrayList<>();
    Map<String, String> headersMap = getHeadersMap();

    syncPersonalizationPromotionMultiVendorListener.receivePostMessage(headersMap, payload);
    verify(promotionMultiVendorService, times(1)).save(any(), any());
  }

  @Test
  void testListenerDeleteServiceCall() {
    final PromotionAccountMultiVendorDeleteVO payload = new PromotionAccountMultiVendorDeleteVO();
    Map<String, String> headersMap = getHeadersMap();

    syncPersonalizationPromotionMultiVendorListener.receiveDeleteMessage(headersMap, payload);
    verify(promotionMultiVendorService, times(1))
            .remove(any(), any());
  }

  private static Map<String, String> getHeadersMap() {

    Map<String, String> headers = new HashMap<>();
    headers.put(REQUEST_TRACE_ID_HEADER , PromotionMocks.REQUEST_TRACE_ID);
    headers.put(PARENT_REQUEST_TRACE_ID, PromotionMocks.PARENT_REQUEST_TRACE_ID);
    headers.put(VENDOR_ID, PromotionMocks.VENDOR_ID);
    headers.put(COUNTRY_HEADER, PromotionMocks.COUNTRY);
    headers.put(TIMESTAMP, String.valueOf(PromotionMocks.TIMESTAMP));
    headers.put(METHOD, ApiConstants.POST_OPERATION);
    headers.put(VERSION, ApiConstants.API_PROMOTION_VERSION_V3);
    headers.put(CONTENT_TYPE_HEADER, ApiConstants.CONTENT_TYPE_VALUE);
    headers.put(X_TIMESTAMP_HEADER, String.valueOf(PromotionMocks.TIMESTAMP));
    return headers;
  }

}
