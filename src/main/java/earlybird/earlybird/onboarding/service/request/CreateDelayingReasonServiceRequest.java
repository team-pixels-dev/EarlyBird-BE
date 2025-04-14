package earlybird.earlybird.onboarding.service.request;

import earlybird.earlybird.feedback.controller.request.CreateFeedbackCommentRequest;
import earlybird.earlybird.onboarding.controller.request.CreateDelayingReasonRequest;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateDelayingReasonServiceRequest {

    private String comment;
    private String clientId;
    private LocalDateTime createdAt;

    @Builder
    private CreateDelayingReasonServiceRequest(
            String comment, String clientId, LocalDateTime createdAt) {
        this.comment = comment;
        this.clientId = clientId;
        this.createdAt = createdAt;
    }

    public static CreateDelayingReasonServiceRequest of(
            CreateDelayingReasonRequest request) {
        return CreateDelayingReasonServiceRequest.builder()
                .comment(request.getComment())
                .clientId(request.getClientId())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
