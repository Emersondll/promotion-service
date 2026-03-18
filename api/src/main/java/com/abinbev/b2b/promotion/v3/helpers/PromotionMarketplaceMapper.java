package com.abinbev.b2b.promotion.v3.helpers;

import com.abinbev.b2b.promotion.v2.domain.model.PromotionMultivendor;
import com.abinbev.b2b.promotion.v2.domain.model.Translation;
import com.abinbev.b2b.promotion.v3.vo.PromotionMarketplace;
import com.abinbev.b2b.promotion.v3.vo.PromotionMarketplacePlatformUniqueId;
import com.abinbev.b2b.promotion.v3.vo.PromotionMarketplaceVendorUniqueId;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.Optional;
import org.apache.commons.collections4.MapUtils;

public class PromotionMarketplaceMapper {

  public static PromotionMarketplace map(
      final PromotionMultivendor promotion, String acceptLanguage) {
    final PromotionMarketplace promotionMarketplace = new PromotionMarketplace();
    promotionMarketplace.setImage(promotion.getImage());
    promotionMarketplace.setTitle(promotion.getTitle());
    promotionMarketplace.setDescription(promotion.getDescription());
    if (StringUtils.isNotBlank(acceptLanguage) && !MapUtils.isEmpty(promotion.getTranslations())) {
      final Translation translation = promotion.getTranslations().get(acceptLanguage);
      if (translation != null) {
        promotionMarketplace.setTitle(translation.getTitle());
        promotionMarketplace.setDescription(translation.getDescription());
      }
    }
    promotionMarketplace.setEndDate(Optional.of(promotion.getEndDate().toString()).orElse(null));
    promotionMarketplace.setStartDate(
        Optional.of(promotion.getStartDate().toString()).orElse(null));
    promotionMarketplace.setType(promotion.getPromotionType());
    promotionMarketplace.setPlatformUniqueIds(
        new PromotionMarketplacePlatformUniqueId(
            promotion.getId(), promotion.getPromotionPlatformId()));
    promotionMarketplace.setVendorUniqueIds(
        new PromotionMarketplaceVendorUniqueId(
            promotion.getVendorId(), promotion.getVendorPromotionId()));
    return promotionMarketplace;
  }
}
