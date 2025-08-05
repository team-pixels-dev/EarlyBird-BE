package earlybird.earlybird.promotion.service;

import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.entity.PromotionCampaignType;
import earlybird.earlybird.promotion.repository.PromotionCampaignRepository;
import earlybird.earlybird.promotion.service.request.CreatePromotionCampaignServiceRequest;
import earlybird.earlybird.promotion.service.response.CreatePromotionCampaignServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePromotionCampaignServiceTest {

    @InjectMocks
    CreatePromotionCampaignService createPromotionCampaignService;

    @Mock
    PromotionCampaignRepository promotionCampaignRepository;

    @DisplayName("프로모션 캠페인을 생성하고 저장한다")
    @Test
    void create() {
        // given
        CreatePromotionCampaignServiceRequest request = CreatePromotionCampaignServiceRequest.builder()
                .name("테스트 캠페인")
                .description("테스트 캠페인 설명")
                .startTime(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endTime(LocalDateTime.of(2024, 12, 31, 23, 59))
                .promotionCampaignType(PromotionCampaignType.EDU_EMAIL_6_MONTH_FREE)
                .build();

        PromotionCampaign savedCampaign = PromotionCampaign.builder()
                .id(1L)
                .name("테스트 캠페인")
                .description("테스트 캠페인 설명")
                .startTime(LocalDateTime.of(2024, 1, 1, 0, 0))
                .endTime(LocalDateTime.of(2024, 12, 31, 23, 59))
                .promotionCampaignType(PromotionCampaignType.EDU_EMAIL_6_MONTH_FREE)
                .active(true)
                .build();

        when(promotionCampaignRepository.save(any(PromotionCampaign.class)))
                .thenReturn(savedCampaign);

        // when
        CreatePromotionCampaignServiceResponse response = createPromotionCampaignService.create(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.promotionCampaignId()).isEqualTo(1L);
        verify(promotionCampaignRepository).save(any(PromotionCampaign.class));
    }
}