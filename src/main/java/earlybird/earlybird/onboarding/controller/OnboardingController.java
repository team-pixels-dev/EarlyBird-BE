package earlybird.earlybird.onboarding.controller;

import earlybird.earlybird.onboarding.controller.request.CreateDelayingReasonRequest;
import earlybird.earlybird.onboarding.controller.request.CreateStressAvoidReasonRequest;
import earlybird.earlybird.onboarding.controller.request.CreateUserDescriptionRequest;
import earlybird.earlybird.onboarding.service.OnboardingDelayingReasonService;
import earlybird.earlybird.onboarding.service.OnboardingStressAvoidReasonService;
import earlybird.earlybird.onboarding.service.OnboardingUserDescriptionService;
import earlybird.earlybird.onboarding.service.request.CreateDelayingReasonServiceRequest;
import earlybird.earlybird.onboarding.service.request.CreateStressAvoidReasonServiceRequest;
import earlybird.earlybird.onboarding.service.request.CreateUserDescriptionServiceRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/onboarding")
@RestController
public class OnboardingController {

    private final OnboardingDelayingReasonService delayingReasonService;
    private final OnboardingStressAvoidReasonService stressAvoidReasonService;
    private final OnboardingUserDescriptionService userDescriptionService;

    @PostMapping("/delaying-reason")
    public ResponseEntity<?> delayingReason(@Valid @RequestBody CreateDelayingReasonRequest request) {
        CreateDelayingReasonServiceRequest serviceRequest = CreateDelayingReasonServiceRequest.of(request);
        delayingReasonService.create(serviceRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stress-avoid-reason")
    public ResponseEntity<?> stressAvoidReason(@Valid @RequestBody CreateStressAvoidReasonRequest request) {
        CreateStressAvoidReasonServiceRequest serviceRequest = CreateStressAvoidReasonServiceRequest.of(request);
        stressAvoidReasonService.create(serviceRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user-description")
    public ResponseEntity<?> userDescription(@Valid @RequestBody CreateUserDescriptionRequest request) {
        CreateUserDescriptionServiceRequest serviceRequest = CreateUserDescriptionServiceRequest.of(request);
        userDescriptionService.create(serviceRequest);
        return ResponseEntity.ok().build();
    }
}
