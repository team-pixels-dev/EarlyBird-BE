package earlybird.earlybird.promotion.service.response;

import earlybird.earlybird.promotion.entity.PromotionCampaign;

import lombok.Builder;

@Builder
public record CreatePromotionCampaignServiceResponse(Long promotionCampaignId) {

    public static CreatePromotionCampaignServiceResponse from(PromotionCampaign promotionCampaign) {
        return CreatePromotionCampaignServiceResponse.builder()
                .promotionCampaignId(promotionCampaign.getId())
                .build();
    }
}
