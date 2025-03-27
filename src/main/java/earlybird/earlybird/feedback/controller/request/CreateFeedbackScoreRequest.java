package earlybird.earlybird.feedback.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CreateFeedbackScoreRequest {
    @NotNull private Integer score;
    @NotBlank private String clientId;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @NotNull private Integer dayCount;

    @Builder
    private CreateFeedbackScoreRequest(
            Integer score, String clientId, LocalDateTime createdAt, Integer dayCount) {
        this.score = score;
        this.clientId = clientId;
        this.createdAt = createdAt;
        this.dayCount = dayCount;
    }
}
