package earlybird.earlybird.promotion.email.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddEmailPromotionAddressDomainRequest {

    @NotBlank private String domain;

    @NotNull private Long promotionCampaignId;
}
