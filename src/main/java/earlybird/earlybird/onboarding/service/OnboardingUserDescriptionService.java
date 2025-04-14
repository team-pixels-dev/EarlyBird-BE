package earlybird.earlybird.onboarding.service;

import earlybird.earlybird.onboarding.domain.OnboardingUserDescription;
import earlybird.earlybird.onboarding.domain.OnboardingUserDescriptionRepository;
import earlybird.earlybird.onboarding.service.request.CreateUserDescriptionServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OnboardingUserDescriptionService {

    private final OnboardingUserDescriptionRepository repository;

    @Transactional
    public void create(CreateUserDescriptionServiceRequest request) {
        OnboardingUserDescription userDescription = OnboardingUserDescription.builder()
                .comment(request.getComment())
                .clientId(request.getClientId())
                .createdTimeAtClient(request.getCreatedAt())
                .build();

        repository.save(userDescription);
    }
}
