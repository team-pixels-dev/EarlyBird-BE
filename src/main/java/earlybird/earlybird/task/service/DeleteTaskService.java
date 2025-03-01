package earlybird.earlybird.task.service;

import earlybird.earlybird.error.exception.NotFoundException;
import earlybird.earlybird.task.domain.Task;
import earlybird.earlybird.task.domain.TaskRepository;
import earlybird.earlybird.task.service.request.DeleteTaskServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class DeleteTaskService {

    private final TaskRepository taskRepository;

    public void delete(DeleteTaskServiceRequest request) {
        Task target = taskRepository.findById(request.getTaskId())
                .orElseThrow(NotFoundException::new);

        if (!target.getClientId().equals(request.getClientId()))
            throw new NotFoundException();

        taskRepository.delete(target);
    }
}
