package earlybird.earlybird.task.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import earlybird.earlybird.task.service.request.CreateTaskServiceRequest;
import earlybird.earlybird.task.service.request.UpdateTaskServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UpdateTaskRequest {
    @NotBlank
    private String title;
    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @NotNull
    private Boolean isAlarmOn;
    @NotNull
    private Boolean isVibrationOn;
    @NotBlank
    private String clientId;
    @NotNull
    private Long taskId;

    public UpdateTaskServiceRequest toServiceRequest() {
        return new UpdateTaskServiceRequest(
                title, startTime, isAlarmOn, isVibrationOn, clientId, taskId
        );
    }
}
