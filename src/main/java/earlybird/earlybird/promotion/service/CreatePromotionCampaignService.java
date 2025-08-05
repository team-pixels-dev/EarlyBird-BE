package earlybird.earlybird.promotion.service;

import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.repository.PromotionCampaignRepository;
import earlybird.earlybird.promotion.service.request.CreatePromotionCampaignServiceRequest;
import earlybird.earlybird.promotion.service.response.CreatePromotionCampaignServiceResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CreatePromotionCampaignService {

    private final PromotionCampaignRepository promotionCampaignRepository;

    @Transactional
    public CreatePromotionCampaignServiceResponse create(
            CreatePromotionCampaignServiceRequest request) {
        PromotionCampaign promotionCampaign = createPromotionCampaign(request);
        PromotionCampaign saved = promotionCampaignRepository.save(promotionCampaign);
        return CreatePromotionCampaignServiceResponse.from(saved);
    }

    private PromotionCampaign createPromotionCampaign(
            CreatePromotionCampaignServiceRequest request) {
        return PromotionCampaign.builder()
                .name(request.name())
                .description(request.description())
                .startTime(request.startTime())
                .endTime(request.endTime())
                .promotionCampaignType(request.promotionCampaignType())
                .active(true)
                .build();
    }
}
