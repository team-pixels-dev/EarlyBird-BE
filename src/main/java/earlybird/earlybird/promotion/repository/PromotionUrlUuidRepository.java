package earlybird.earlybird.promotion.repository;

import earlybird.earlybird.promotion.entity.PromotionUrlUuid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PromotionUrlUuidRepository extends JpaRepository<PromotionUrlUuid, Long> {

    Optional<PromotionUrlUuid> findByUuid(UUID uuid);
}
