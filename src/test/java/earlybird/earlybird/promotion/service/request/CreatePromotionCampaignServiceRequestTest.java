package earlybird.earlybird.promotion.service.request;

import earlybird.earlybird.promotion.controller.request.CreatePromotionCampaignRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static earlybird.earlybird.promotion.entity.PromotionCampaignType.EDU_EMAIL_6_MONTH_FREE;
import static org.assertj.core.api.Assertions.assertThat;

class CreatePromotionCampaignServiceRequestTest {

    @DisplayName("CreatePromotionCampaignRequest 객체로부터 CreatePromotionCampaignServiceRequest 객체를 만든다.")
    @Test
    void from() {
        // given
        String promotionCampaignName = "promotionCampaignName";
        String promotionCampaignDescription = "promotionCampaignDescription";
        LocalDateTime localDateTime = LocalDateTime.of(2025, 8, 4, 0, 0, 0);
        CreatePromotionCampaignRequest request = CreatePromotionCampaignRequest.builder()
                .promotionCampaignName(promotionCampaignName)
                .promotionCampaignDescription(promotionCampaignDescription)
                .startTime(localDateTime)
                .endTime(localDateTime)
                .promotionCampaignType(EDU_EMAIL_6_MONTH_FREE)
                .build();

        // when
        CreatePromotionCampaignServiceRequest result = CreatePromotionCampaignServiceRequest.from(request);

        // then
        assertThat(result).isNotNull();
        assertThat(result.name()).isEqualTo(promotionCampaignName);
        assertThat(result.description()).isEqualTo(promotionCampaignDescription);
        assertThat(result.startTime()).isEqualTo(localDateTime);
        assertThat(result.endTime()).isEqualTo(localDateTime);
        assertThat(result.promotionCampaignType()).isEqualTo(EDU_EMAIL_6_MONTH_FREE);
    }
}