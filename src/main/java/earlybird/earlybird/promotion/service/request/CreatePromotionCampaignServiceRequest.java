package earlybird.earlybird.promotion.service.request;

import earlybird.earlybird.promotion.controller.request.CreatePromotionCampaignRequest;
import earlybird.earlybird.promotion.entity.PromotionCampaignType;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CreatePromotionCampaignServiceRequest(
        String name,
        String description,
        LocalDateTime startTime,
        LocalDateTime endTime,
        PromotionCampaignType promotionCampaignType) {

    public static CreatePromotionCampaignServiceRequest from(
            CreatePromotionCampaignRequest request) {
        return CreatePromotionCampaignServiceRequest.builder()
                .name(request.getPromotionCampaignName())
                .description(request.getPromotionCampaignDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .promotionCampaignType(request.getPromotionCampaignType())
                .build();
    }
}
