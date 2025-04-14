package earlybird.earlybird.onboarding.service.request;

import earlybird.earlybird.onboarding.controller.request.CreateStressAvoidReasonRequest;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateStressAvoidReasonServiceRequest {
    private String comment;
    private String clientId;
    private LocalDateTime createdAt;

    @Builder
    private CreateStressAvoidReasonServiceRequest(
            String comment, String clientId, LocalDateTime createdAt) {
        this.comment = comment;
        this.clientId = clientId;
        this.createdAt = createdAt;
    }

    public static CreateStressAvoidReasonServiceRequest of(CreateStressAvoidReasonRequest request) {
        return CreateStressAvoidReasonServiceRequest.builder()
                .comment(request.getComment())
                .clientId(request.getClientId())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
