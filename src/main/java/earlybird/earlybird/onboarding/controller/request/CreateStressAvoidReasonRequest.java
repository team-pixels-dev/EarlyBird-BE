package earlybird.earlybird.onboarding.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CreateStressAvoidReasonRequest {
    @NotBlank private String comment;
    @NotBlank private String clientId;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @Builder
    private CreateStressAvoidReasonRequest(
            String comment, String clientId, LocalDateTime createdAt) {
        this.comment = comment;
        this.clientId = clientId;
        this.createdAt = createdAt;
    }
}
