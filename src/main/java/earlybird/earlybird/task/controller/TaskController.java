package earlybird.earlybird.task.controller;

import earlybird.earlybird.task.controller.request.CreateTaskRequest;
import earlybird.earlybird.task.controller.request.UpdateTaskRequest;
import earlybird.earlybird.task.controller.response.CreateTaskResponse;
import earlybird.earlybird.task.service.CreateTaskService;
import earlybird.earlybird.task.service.DeleteTaskService;
import earlybird.earlybird.task.service.UpdateTaskService;
import earlybird.earlybird.task.service.request.DeleteTaskServiceRequest;
import earlybird.earlybird.task.service.response.CreateTaskServiceResponse;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
@RestController
public class TaskController {

    private final CreateTaskService createTaskService;
    private final DeleteTaskService deleteTaskService;
    private final UpdateTaskService updateTaskService;

    @PostMapping
    public ResponseEntity<?> createTask(@Valid @RequestBody CreateTaskRequest request) {
        CreateTaskServiceResponse serviceResponse =
                createTaskService.create(request.toServiceRequest());
        return ResponseEntity.ok(CreateTaskResponse.from(serviceResponse));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTask(
            @Valid @NotBlank @RequestHeader("clientId") String clientId,
            @RequestHeader("taskId") Long taskId) {
        DeleteTaskServiceRequest serviceRequest =
                DeleteTaskServiceRequest.builder().clientId(clientId).taskId(taskId).build();

        deleteTaskService.delete(serviceRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateTask(@Valid @RequestBody UpdateTaskRequest request) {
        updateTaskService.update(request.toServiceRequest());
        return ResponseEntity.ok().build();
    }
}
