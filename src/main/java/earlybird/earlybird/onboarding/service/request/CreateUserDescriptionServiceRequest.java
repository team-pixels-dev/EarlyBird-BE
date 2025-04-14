package earlybird.earlybird.onboarding.service.request;

import earlybird.earlybird.onboarding.controller.request.CreateUserDescriptionRequest;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateUserDescriptionServiceRequest {

    private String comment;
    private String clientId;
    private LocalDateTime createdAt;

    @Builder
    private CreateUserDescriptionServiceRequest(
            String comment, String clientId, LocalDateTime createdAt) {
        this.comment = comment;
        this.clientId = clientId;
        this.createdAt = createdAt;
    }

    public static CreateUserDescriptionServiceRequest of(CreateUserDescriptionRequest request) {
        return CreateUserDescriptionServiceRequest.builder()
                .comment(request.getComment())
                .clientId(request.getClientId())
                .createdAt(request.getCreatedAt())
                .build();
    }
}
