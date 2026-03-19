package com.abinbev.b2b.promotion.consumer.listener.personalization;

import java.util.List;
import java.util.Map;

import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.abinbev.b2b.promotion.consumer.listener.GenericListener;
import com.abinbev.b2b.promotion.consumer.listener.message.PromotionMultiVendorMessage;
import com.abinbev.b2b.promotion.consumer.service.PromotionMultiVendorService;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountMultiVendorDeleteVO;
import com.abinbev.b2b.promotion.consumer.vo.PromotionMultiVendorVO;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

import jakarta.validation.Valid;

@Component
public class SyncPersonalizationPromotionMultiVendorListener extends GenericListener {

	private final PromotionMultiVendorService promotionMultiVendorService;

	public SyncPersonalizationPromotionMultiVendorListener(final PromotionMultiVendorService promotionMultiVendorService) {

		this.promotionMultiVendorService = promotionMultiVendorService;
	}

	@RabbitListener(
			queues = { "#{beesSyncIntegrationPost.getAllQueueNames()}" },
			containerFactory = "simpleContainerFactory")
	@Trace(dispatcher = true, metricName = "syncPersonalizationPromotionUpdated")
	public void receivePostMessage(
			@Headers Map<String, String> headers,
			@Valid @Payload List<PromotionMultiVendorVO> message) {

		try {
			NewRelic.addCustomParameter("receivedSyncPersonalizationPromotionUpdated", true);

			PromotionMultiVendorMessage<?> promotionMultiVendorMessage = new PromotionMultiVendorMessage<>(headers);
			loggingSetMDC(promotionMultiVendorMessage);

			promotionMultiVendorService.save(message, promotionMultiVendorMessage);
			NewRelic.addCustomParameter("SuccessfullySyncPersonalizationPromotionUpdated", true);

		} finally {
			MDC.clear();
		}
	}

	@RabbitListener(
			queues = { "#{beesSyncIntegrationDelete.getAllQueueNames()}" },
			containerFactory = "simpleContainerFactory")
	@Trace(dispatcher = true, metricName = "syncPersonalizationPromotionDeleted")
	public void receiveDeleteMessage(@Headers Map<String, String> headers,
			@Valid @Payload PromotionAccountMultiVendorDeleteVO message) {

		try {
			NewRelic.addCustomParameter("receivedSyncPersonalizationPromotionDeleted", true);

			PromotionMultiVendorMessage<?> promotionMultiVendorMessage = new PromotionMultiVendorMessage<>(headers);
			loggingSetMDC(promotionMultiVendorMessage);

			promotionMultiVendorService.remove(message, promotionMultiVendorMessage);
			NewRelic.addCustomParameter("SuccessfullySyncPersonalizationPromotionDeleted", true);

		} finally {
			MDC.clear();
		}
	}

}
