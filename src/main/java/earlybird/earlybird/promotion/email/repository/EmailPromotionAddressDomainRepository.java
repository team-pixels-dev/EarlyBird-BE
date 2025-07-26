package earlybird.earlybird.promotion.email.repository;

import earlybird.earlybird.promotion.email.entity.EmailPromotionAddressDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailPromotionAddressDomainRepository extends JpaRepository<EmailPromotionAddressDomain, Long> {
}
