package earlybird.earlybird.promotion.email.service.request;

import earlybird.earlybird.promotion.email.controller.request.AddEmailPromotionAddressDomainRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class AddEmailPromotionAddressDomainServiceRequest {
    private final String domain;
    private final Long promotionCampaignId;

    public static AddEmailPromotionAddressDomainServiceRequest from(
            AddEmailPromotionAddressDomainRequest request) {
        return AddEmailPromotionAddressDomainServiceRequest.builder()
                .domain(request.getDomain())
                .promotionCampaignId(request.getPromotionCampaignId())
                .build();
    }
}
