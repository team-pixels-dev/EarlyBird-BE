package earlybird.earlybird.log.click.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import earlybird.earlybird.log.click.service.request.CreateClickLogServiceRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CreateClickLogRequest {

    @NotBlank private String clientId;

    @NotBlank private String clickType;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime clickTime;

    public CreateClickLogServiceRequest toServiceRequest() {
        return CreateClickLogServiceRequest.builder()
                .clientId(clientId)
                .clickType(clickType)
                .clickTime(clickTime)
                .build();
    }
}
