package earlybird.earlybird.onboarding.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface OnboardingUserDescriptionRepository extends JpaRepository<OnboardingUserDescription, Long> {
}
