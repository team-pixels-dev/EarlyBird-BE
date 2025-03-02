package earlybird.earlybird.task.service.request;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UpdateTaskServiceRequest {
    private String title;
    private LocalDateTime startTime;
    private Boolean isAlarmOn;
    private Boolean isVibrationOn;
    private String clientId;
    private Long taskId;
}
