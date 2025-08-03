package earlybird.earlybird.promotion.service.request;

import earlybird.earlybird.promotion.controller.request.CreatePromotionCampaignRequest;
import earlybird.earlybird.promotion.entity.PromotionCampaignType;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
public class CreatePromotionCampaignServiceRequest {
    private final String name;
    private final String description;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Long perUserLimit;
    private final Long totalIssueLimit;
    private final PromotionCampaignType promotionCampaignType;

    public static CreatePromotionCampaignServiceRequest from(
            CreatePromotionCampaignRequest request) {
        return CreatePromotionCampaignServiceRequest.builder()
                .name(request.getPromotionCampaignName())
                .description(request.getPromotionCampaignDescription())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .perUserLimit(request.getPerUserLimit())
                .totalIssueLimit(request.getTotalIssueLimit())
                .promotionCampaignType(request.getPromotionCampaignType())
                .build();
    }
}
