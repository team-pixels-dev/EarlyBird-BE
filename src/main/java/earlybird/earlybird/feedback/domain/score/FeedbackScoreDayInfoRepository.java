package earlybird.earlybird.feedback.domain.score;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface FeedbackScoreDayInfoRepository extends JpaRepository<FeedbackScoreDayInfo, Long> {
}
