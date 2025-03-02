package earlybird.earlybird.task.service.request;

import earlybird.earlybird.task.domain.Task;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CreateTaskServiceRequest {
    private String title;
    private LocalDateTime startTime;
    private Boolean isAlarmOn;
    private Boolean isVibrationOn;
    private String clientId;

    public Task toEntity() {
        return Task.builder()
                .clientId(clientId)
                .title(title)
                .startTime(startTime)
                .isAlarmOn(isAlarmOn)
                .isVibrationOn(isVibrationOn)
                .build();
    }
}
