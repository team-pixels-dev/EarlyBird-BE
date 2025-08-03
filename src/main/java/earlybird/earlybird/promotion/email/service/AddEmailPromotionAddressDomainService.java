package earlybird.earlybird.promotion.email.service;

import earlybird.earlybird.error.exception.PromotionEmailDomainNameIsAlreadyExistsException;
import earlybird.earlybird.promotion.email.entity.EmailPromotionAddressDomain;
import earlybird.earlybird.promotion.email.repository.EmailPromotionAddressDomainRepository;
import earlybird.earlybird.promotion.email.service.request.AddEmailPromotionAddressDomainServiceRequest;
import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.service.GetPromotionCampaignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AddEmailPromotionAddressDomainService {

    private final EmailPromotionAddressDomainRepository domainRepository;
    private final GetPromotionCampaignService  getPromotionCampaignService;

    @Transactional
    public void add(AddEmailPromotionAddressDomainServiceRequest request) {
        PromotionCampaign promotionCampaign = getPromotionCampaignService.findById(request.getPromotionCampaignId());

        Boolean domainIsAlreadyExists =
                domainRepository.existsByDomainNameAndPromotionCampaign(request.getDomain(), promotionCampaign);

        if (domainIsAlreadyExists) {
            throw new PromotionEmailDomainNameIsAlreadyExistsException();
        }

        EmailPromotionAddressDomain emailPromotionAddressDomain =
                EmailPromotionAddressDomain.builder()
                        .domainName(request.getDomain())
                        .promotionCampaign(promotionCampaign)
                        .build();

        domainRepository.save(emailPromotionAddressDomain);
    }
}
