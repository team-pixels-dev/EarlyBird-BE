package earlybird.earlybird.task.controller.response;

import earlybird.earlybird.task.service.response.CreateTaskServiceResponse;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateTaskResponse {
    private final Long taskId;

    public static CreateTaskResponse from(CreateTaskServiceResponse serviceResponse) {
        return CreateTaskResponse.builder().taskId(serviceResponse.getTaskId()).build();
    }
}
