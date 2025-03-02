package earlybird.earlybird.task.service;

import earlybird.earlybird.error.exception.NotFoundException;
import earlybird.earlybird.task.domain.Task;
import earlybird.earlybird.task.domain.TaskRepository;
import earlybird.earlybird.task.service.request.UpdateTaskServiceRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UpdateTaskService {

    private final TaskRepository taskRepository;

    public void update(UpdateTaskServiceRequest request) {
        Task target =
                taskRepository.findById(request.getTaskId()).orElseThrow(NotFoundException::new);

        if (!target.getClientId().equals(request.getClientId())) throw new NotFoundException();

        modifyTask(request, target);
    }

    private void modifyTask(UpdateTaskServiceRequest request, Task task) {
        task.setTitle(request.getTitle());
        task.setStartTime(request.getStartTime());
        task.setIsAlarmOn(request.getIsAlarmOn());
        task.setIsVibrationOn(request.getIsVibrationOn());
    }
}
