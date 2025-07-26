package earlybird.earlybird.promotion.email.repository;

import earlybird.earlybird.promotion.email.entity.EmailPromotionCodeIssuance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailPromotionCodeIssuanceRepository extends JpaRepository<EmailPromotionCodeIssuance, Long> {
}
