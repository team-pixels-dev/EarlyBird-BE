package earlybird.earlybird.promotion.email.controller.request;

import earlybird.earlybird.promotion.email.entity.PromotionEmailMessageType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UnivEmailPromotionVerificationRequest {

    @NotBlank
    private String email;

    @NotNull
    private Long promotionCampaignId;

    @NotNull
    private PromotionEmailMessageType promotionEmailMessageType;
}
