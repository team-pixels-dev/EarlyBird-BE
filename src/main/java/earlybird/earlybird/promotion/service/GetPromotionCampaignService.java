package earlybird.earlybird.promotion.service;

import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.repository.PromotionCampaignRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetPromotionCampaignService {

    private final PromotionCampaignRepository promotionCampaignRepository;

    public PromotionCampaign findById(Long promotionCampaignId) {
        return promotionCampaignRepository.findById(promotionCampaignId).orElseThrow();
    }
}
