package earlybird.earlybird.feedback.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import earlybird.earlybird.feedback.domain.pay.PaymentFeedbackLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CreatePaymentFeedbackRequest {
    @NotNull private PaymentFeedbackLevel level;
    @NotBlank private String clientId;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @Builder
    private CreatePaymentFeedbackRequest(
            PaymentFeedbackLevel level, String clientId, LocalDateTime createdAt) {
        this.level = level;
        this.clientId = clientId;
        this.createdAt = createdAt;
    }
}
