package earlybird.earlybird.promotion.service.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import earlybird.earlybird.promotion.entity.PromotionCampaign;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CreatePromotionCampaignServiceResponseTest {

    @DisplayName("PromotionCampaign 객체를 통해 CreatePromotionCampaignServiceResponse 객체를 만든다.")
    @Test
    void from() {
        // given
        PromotionCampaign promotionCampaign = PromotionCampaign.builder().id(1L).build();

        // when
        CreatePromotionCampaignServiceResponse response =
                CreatePromotionCampaignServiceResponse.from(promotionCampaign);

        // then
        assertThat(response).isNotNull();
        assertThat(response.promotionCampaignId()).isEqualTo(promotionCampaign.getId());
    }
}
