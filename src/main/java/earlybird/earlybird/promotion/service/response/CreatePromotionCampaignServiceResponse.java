package earlybird.earlybird.promotion.service.response;

import earlybird.earlybird.promotion.entity.PromotionCampaign;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CreatePromotionCampaignServiceResponse {
    private final Long PromotionCampaignId;

    public static CreatePromotionCampaignServiceResponse from(PromotionCampaign promotionCampaign) {
        return CreatePromotionCampaignServiceResponse.builder()
                .PromotionCampaignId(promotionCampaign.getId())
                .build();
    }
}
