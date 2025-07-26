package earlybird.earlybird.promotion.email.repository;

import earlybird.earlybird.promotion.email.entity.PromotionEmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionEmailVerificationRepository extends JpaRepository<PromotionEmailVerification, Long> {
}
