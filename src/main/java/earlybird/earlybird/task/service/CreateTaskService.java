package earlybird.earlybird.task.service;

import earlybird.earlybird.task.domain.Task;
import earlybird.earlybird.task.domain.TaskRepository;
import earlybird.earlybird.task.service.request.CreateTaskServiceRequest;
import earlybird.earlybird.task.service.response.CreateTaskServiceResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CreateTaskService {

    private final TaskRepository taskRepository;

    public CreateTaskServiceResponse create(CreateTaskServiceRequest request) {
        Task task = request.toEntity();
        Task saved = taskRepository.save(task);
        return CreateTaskServiceResponse.builder().taskId(saved.getId()).build();
    }
}
