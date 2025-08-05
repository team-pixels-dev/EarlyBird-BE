package earlybird.earlybird.promotion.controller.response;

import earlybird.earlybird.promotion.service.response.CreatePromotionCampaignServiceResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreatePromotionCampaignResponse {
    private final Long promotionCampaignId;

    public static CreatePromotionCampaignResponse from(
            CreatePromotionCampaignServiceResponse response) {
        return CreatePromotionCampaignResponse.builder()
                .promotionCampaignId(response.promotionCampaignId())
                .build();
    }
}
