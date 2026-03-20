package com.abinbev.b2b.promotion.relay.mapper;

import com.abinbev.b2b.promotion.relay.domain.PromotionMultiVendor;
import com.abinbev.b2b.promotion.relay.domain.PromotionSingleVendor;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-09T10:11:33-0300",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class PromotionMapperImpl implements PromotionMapper {

    @Override
    public PromotionMultiVendor singleToMultiVendor(PromotionSingleVendor promotion) {
        if ( promotion == null ) {
            return null;
        }

        PromotionMultiVendor promotionMultiVendor = new PromotionMultiVendor();

        promotionMultiVendor.setTitle( promotion.getTitle() );
        promotionMultiVendor.setDescription( promotion.getDescription() );
        promotionMultiVendor.setType( promotion.getType() );
        promotionMultiVendor.setStartDate( promotion.getStartDate() );
        promotionMultiVendor.setEndDate( promotion.getEndDate() );
        promotionMultiVendor.setImage( promotion.getImage() );
        promotionMultiVendor.setBudget( promotion.getBudget() );
        promotionMultiVendor.setQuantityLimit( promotion.getQuantityLimit() );

        promotionMultiVendor.setVendorPromotionId( org.apache.commons.lang3.ObjectUtils.defaultIfNull(promotion.getPromotionId(), promotion.getId() ) );

        return promotionMultiVendor;
    }
}
