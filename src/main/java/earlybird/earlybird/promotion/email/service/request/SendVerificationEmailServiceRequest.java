package earlybird.earlybird.promotion.email.service.request;

import earlybird.earlybird.promotion.email.controller.request.UnivEmailPromotionVerificationRequest;
import earlybird.earlybird.promotion.email.entity.PromotionEmailMessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SendVerificationEmailServiceRequest {
    private final String email;
    private final Long promotionCampaignId;
    private final PromotionEmailMessageType promotionEmailMessageType;

    public static SendVerificationEmailServiceRequest from(UnivEmailPromotionVerificationRequest request) {
        return SendVerificationEmailServiceRequest.builder()
                .email(request.getEmail())
                .promotionCampaignId(request.getPromotionCampaignId())
                .promotionEmailMessageType(request.getPromotionEmailMessageType())
                .build();
    }
}
