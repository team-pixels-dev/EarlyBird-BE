package earlybird.earlybird.promotion.apple;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface ApplePromotionUrlRepository extends JpaRepository<ApplePromotionUrl, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ApplePromotionUrl> findAllByIsUsedFalseAndPromotionCampaignId(Long promotionCampaignId);

    Optional<ApplePromotionUrl> findByUrl(String url);
}
