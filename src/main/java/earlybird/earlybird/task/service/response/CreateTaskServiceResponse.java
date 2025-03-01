package earlybird.earlybird.task.service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateTaskServiceResponse {
    private final Long taskId;
}
