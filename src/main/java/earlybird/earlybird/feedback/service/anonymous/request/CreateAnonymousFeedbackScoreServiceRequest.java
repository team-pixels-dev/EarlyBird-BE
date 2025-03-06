package earlybird.earlybird.feedback.service.anonymous.request;

import earlybird.earlybird.feedback.controller.request.CreateFeedbackScoreRequest;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateAnonymousFeedbackScoreServiceRequest {
    private int score;
    private String clientId;
    private LocalDateTime createdAt;
    private Integer dayCount;

    @Builder
    private CreateAnonymousFeedbackScoreServiceRequest(
            int score, String clientId, LocalDateTime createdAt, Integer dayCount) {
        this.score = score;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.dayCount = dayCount;
    }

    public static CreateAnonymousFeedbackScoreServiceRequest of(
            CreateFeedbackScoreRequest request) {
        return CreateAnonymousFeedbackScoreServiceRequest.builder()
                .score(request.getScore())
                .clientId(request.getClientId())
                .createdAt(request.getCreatedAt())
                .dayCount(request.getDayCount())
                .build();
    }
}
