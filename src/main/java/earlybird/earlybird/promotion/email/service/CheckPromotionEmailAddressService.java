package earlybird.earlybird.promotion.email.service;

import earlybird.earlybird.email.address.check.CheckEmailAddressService;
import earlybird.earlybird.promotion.email.entity.EmailPromotionAddressDomain;
import earlybird.earlybird.promotion.email.repository.EmailPromotionAddressDomainRepository;
import earlybird.earlybird.promotion.entity.PromotionCampaign;
import earlybird.earlybird.promotion.repository.PromotionCampaignRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CheckPromotionEmailAddressService {

    private final EmailPromotionAddressDomainRepository promotionDomainRepository;
    private final PromotionCampaignRepository promotionCampaignRepository;
    private final CheckEmailAddressService checkEmailAddressService;

    @Transactional
    public Boolean checkValidPromotionEmail(String email, Long promotionCampaignId) {
        if (!checkEmailAddressService.checkEmailRegex(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        PromotionCampaign promotionCampaign = getPromotionCampaignById(promotionCampaignId);
        return checkDomainName(email, promotionCampaign);
    }

    private PromotionCampaign getPromotionCampaignById(Long promotionCampaignId) {
        Optional<PromotionCampaign> optionalCampaign =
                promotionCampaignRepository.findById(promotionCampaignId);
        if (optionalCampaign.isEmpty()) {
            throw new IllegalArgumentException("Campaign not found");
        }
        return optionalCampaign.get();
    }

    private Boolean checkDomainName(String email, PromotionCampaign promotionCampaign) {
        List<EmailPromotionAddressDomain> validDomainList =
                promotionDomainRepository.findAllByPromotionCampaign(promotionCampaign);
        return validDomainList.stream()
                .anyMatch(
                        validDomain -> {
                            String validDomainName = validDomain.getDomainName();
                            String requestDomainName = email.split("@")[1];
                            return validDomainName.equals(requestDomainName);
                        });
    }
}
