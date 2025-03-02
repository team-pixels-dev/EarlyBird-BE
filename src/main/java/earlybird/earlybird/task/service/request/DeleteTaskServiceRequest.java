package earlybird.earlybird.task.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class DeleteTaskServiceRequest {
    private final String clientId;
    private final Long taskId;
}
