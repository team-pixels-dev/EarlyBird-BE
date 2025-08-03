package earlybird.earlybird.promotion.email.repository;

import earlybird.earlybird.promotion.email.entity.PromotionEmailVerification;
import earlybird.earlybird.promotion.entity.PromotionUrlUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromotionEmailVerificationRepository extends JpaRepository<PromotionEmailVerification, Long> {

    Optional<PromotionEmailVerification> findByPromotionUrlUuid(PromotionUrlUuid promotionUrlUuid);
}
