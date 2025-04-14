package earlybird.earlybird.onboarding.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OnboardingDelayingReasonRepository
        extends JpaRepository<OnboardingDelayingReason, Long> {}
