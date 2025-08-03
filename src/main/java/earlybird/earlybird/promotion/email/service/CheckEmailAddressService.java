package earlybird.earlybird.promotion.email.service;

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
public class CheckEmailAddressService {

    private final EmailPromotionAddressDomainRepository promotionDomainRepository;
    private final PromotionCampaignRepository promotionCampaignRepository;

    @Transactional
    public Boolean checkValidPromotionEmail(String email, Long promotionCampaignId) {
        checkEmailRegex(email);
        PromotionCampaign promotionCampaign = getPromotionCampaignById(promotionCampaignId);
        return checkDomainName(email, promotionCampaign);
    }

    private void checkEmailRegex(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Invalid email address");
        }
    }

    private PromotionCampaign getPromotionCampaignById(Long promotionCampaignId) {
        Optional<PromotionCampaign> optionalCampaign = promotionCampaignRepository.findById(promotionCampaignId);
        if (optionalCampaign.isEmpty()) {
            throw new IllegalArgumentException("Campaign not found");
        }
        return optionalCampaign.get();
    }

    private Boolean checkDomainName(String email, PromotionCampaign promotionCampaign) {
        List<EmailPromotionAddressDomain> validDomainList
                = promotionDomainRepository.findAllByPromotionCampaign(promotionCampaign);
        return validDomainList.stream()
                .anyMatch(validDomain -> {
                    String validDomainName = validDomain.getDomainName();
                    String requestDomainName = email.split("@")[1];
                    return validDomainName.equals(requestDomainName);
                });
    }
}
