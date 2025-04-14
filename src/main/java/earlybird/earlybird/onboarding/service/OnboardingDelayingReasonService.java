package earlybird.earlybird.onboarding.service;

import earlybird.earlybird.onboarding.domain.OnboardingDelayingReason;
import earlybird.earlybird.onboarding.domain.OnboardingDelayingReasonRepository;
import earlybird.earlybird.onboarding.service.request.CreateDelayingReasonServiceRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OnboardingDelayingReasonService {

    private final OnboardingDelayingReasonRepository repository;

    @Transactional
    public void create(CreateDelayingReasonServiceRequest request) {
        OnboardingDelayingReason delayingReason =
                OnboardingDelayingReason.builder()
                        .comment(request.getComment())
                        .clientId(request.getClientId())
                        .createdTimeAtClient(request.getCreatedAt())
                        .build();

        repository.save(delayingReason);
    }
}
