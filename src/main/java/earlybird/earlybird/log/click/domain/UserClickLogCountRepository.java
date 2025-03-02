package earlybird.earlybird.log.click.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface UserClickLogCountRepository extends JpaRepository<UserClickLogCount, Long> {
    Optional<UserClickLogCount> findByClientIdAndClickTypeAndClickDate(String clientId, String clickType, LocalDate clickDate);
}
