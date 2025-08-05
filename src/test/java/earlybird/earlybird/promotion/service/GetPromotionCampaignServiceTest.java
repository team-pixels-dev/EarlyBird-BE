package earlybird.earlybird.promotion.service;

import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.repository.PromotionCampaignRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetPromotionCampaignServiceTest {

    @InjectMocks
    private GetPromotionCampaignService getPromotionCampaignService;

    @Mock
    private PromotionCampaignRepository promotionCampaignRepository;

    @DisplayName("프로모션 캠페인 ID가 주어지면 해당하는 프로모션 캠페인 객체를 반환한다.")
    @Test
    void findById() {
        // given
        Long promotionCampaignId = 1L;
        PromotionCampaign promotionCampaign = PromotionCampaign.builder().id(promotionCampaignId).build();
        when(promotionCampaignRepository.findById(promotionCampaignId)).thenReturn(Optional.of(promotionCampaign));

        // when
        PromotionCampaign result = getPromotionCampaignService.findById(promotionCampaignId);

        // then
        assertThat(result.getId()).isEqualTo(promotionCampaignId);
        assertThat(result).isEqualTo(promotionCampaign);
    }

    @DisplayName("주어진 프로모션 캠페인 ID에 해당하는 프로모션 캠페인 객체가 없으면 예외를 던진다.")
    @Test
    void findByIdThrowException() {
        // given
        Long promotionCampaignId = 1L;
        when(promotionCampaignRepository.findById(promotionCampaignId)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> getPromotionCampaignService.findById(promotionCampaignId))
                .isInstanceOf(NoSuchElementException.class);
    }
}