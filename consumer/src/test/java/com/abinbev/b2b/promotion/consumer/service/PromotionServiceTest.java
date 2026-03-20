package com.abinbev.b2b.promotion.consumer.service;

import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.abinbev.b2b.promotion.consumer.domain.PromotionModel;
import com.abinbev.b2b.promotion.consumer.helper.PromotionMocks;
import com.abinbev.b2b.promotion.consumer.repository.PromotionRepository;
import com.abinbev.b2b.promotion.consumer.vo.PromotionAccountDeleteVo;
import com.abinbev.b2b.promotion.consumer.vo.PromotionListVo;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class PromotionServiceTest {

  @InjectMocks private PromotionService promotionService;

  @Mock private PromotionRepository promotionRepository;

  @Test
  public void testCreatePromotion() {
    PromotionListVo promotionListMock = PromotionMocks.getPromotionVoListMockValidator();
    promotionService.createPromotions(
            promotionListMock, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);
    final ArgumentCaptor<List<PromotionModel>> requestCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<String> countryCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
    verify(promotionRepository)
            .insertBulk(requestCaptor.capture(), countryCaptor.capture(), timestampCaptor.capture());
  }

  @Test
  public void testCreatePromotionWithNullList() {
    promotionService.createPromotions(null, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);
    ArgumentCaptor<String> countryCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
    verify(promotionRepository, times(0))
            .insertBulk(isNull(), countryCaptor.capture(), timestampCaptor.capture());
  }

  @Test
  public void testCreatePromotionWithEmptyList() {
    PromotionListVo promotionListVo = new PromotionListVo();
    promotionService.createPromotions(
            promotionListVo, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);
    final ArgumentCaptor<List<PromotionModel>> requestCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<String> countryCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
    verify(promotionRepository, times(0))
            .insertBulk(requestCaptor.capture(), countryCaptor.capture(), timestampCaptor.capture());
  }

  @Test
  public void softDeletePromotion() {
    final ArgumentCaptor<PromotionAccountDeleteVo> requestCaptor =
            ArgumentCaptor.forClass(PromotionAccountDeleteVo.class);
    ArgumentCaptor<String> countryCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);

    promotionService.deletePromotion(
            PromotionMocks.getDeletePromotionVoListWithoutAccountMessageMock());

    Mockito.verify(promotionRepository, Mockito.times(1))
            .softDeletePromotion(
                    requestCaptor.capture(), timestampCaptor.capture(), countryCaptor.capture());
  }

  @Test
  public void testCreatePromotionWithMetaDataDeactivated() {
    PromotionListVo promotionListMock = PromotionMocks.getPromotionVoListMockValidator();
    promotionService.createPromotions(
            promotionListMock, PromotionMocks.COUNTRY, PromotionMocks.TIMESTAMP);
    final ArgumentCaptor<List<PromotionModel>> requestCaptor = ArgumentCaptor.forClass(List.class);

    ArgumentCaptor<String> countryCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<Long> timestampCaptor = ArgumentCaptor.forClass(Long.class);
    verify(promotionRepository, times(1))
            .insertBulk(requestCaptor.capture(), countryCaptor.capture(), timestampCaptor.capture());
  }
}
