package earlybird.earlybird.onboarding.service;

import earlybird.earlybird.onboarding.domain.OnboardingStressAvoidReason;
import earlybird.earlybird.onboarding.domain.OnboardingStressAvoidReasonRepository;
import earlybird.earlybird.onboarding.service.request.CreateStressAvoidReasonServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OnboardingStressAvoidReasonService {
    private final OnboardingStressAvoidReasonRepository repository;

    @Transactional
    public void create(CreateStressAvoidReasonServiceRequest request) {

        OnboardingStressAvoidReason reason = OnboardingStressAvoidReason.builder()
                .comment(request.getComment())
                .clientId(request.getClientId())
                .createdTimeAtClient(request.getCreatedAt())
                .build();

        repository.save(reason);
    }
}
