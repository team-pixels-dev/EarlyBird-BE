package earlybird.earlybird.promotion.email.repository;

import earlybird.earlybird.promotion.email.entity.EmailPromotionAddressDomain;
import earlybird.earlybird.promotion.entity.PromotionCampaign;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailPromotionAddressDomainRepository
        extends JpaRepository<EmailPromotionAddressDomain, Long> {

    List<EmailPromotionAddressDomain> findAllByPromotionCampaign(
            PromotionCampaign promotionCampaign);

    Boolean existsByDomainNameAndPromotionCampaign(
            String domainName, PromotionCampaign promotionCampaign);
}
