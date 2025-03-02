package earlybird.earlybird.task.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import earlybird.earlybird.error.exception.NotFoundException;
import earlybird.earlybird.task.domain.Task;
import earlybird.earlybird.task.domain.TaskRepository;
import earlybird.earlybird.task.service.request.UpdateTaskServiceRequest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UpdateTaskServiceTest {
    @Mock private TaskRepository taskRepository;

    @InjectMocks private UpdateTaskService updateTaskService;

    @DisplayName("요청한 정보로 Task 정보를 업데이트한다")
    @Test
    void updateTaskWithValidRequest() {
        Long taskId = 1L;
        String title = "title";
        LocalDateTime startTime = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        Boolean isAlarmOn = true;
        Boolean isVibrationOn = false;
        String clientId = "clientId";

        Task task =
                Task.builder()
                        .clientId(clientId)
                        .title(title)
                        .startTime(startTime)
                        .isAlarmOn(isAlarmOn)
                        .isVibrationOn(isVibrationOn)
                        .build();

        String expectedTitle = "new title";
        LocalDateTime expectedStartTime = LocalDateTime.of(2025, 3, 2, 0, 0, 1);
        Boolean expectedIsAlarmOn = false;
        Boolean expectedIsVibrationOn = true;
        String expectedClientId = "clientId";

        UpdateTaskServiceRequest request =
                UpdateTaskServiceRequest.builder()
                        .taskId(taskId)
                        .title(expectedTitle)
                        .startTime(expectedStartTime)
                        .isAlarmOn(expectedIsAlarmOn)
                        .isVibrationOn(expectedIsVibrationOn)
                        .clientId(expectedClientId)
                        .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        updateTaskService.update(request);

        verify(taskRepository).findById(taskId);
        Assertions.assertThat(task.getTitle()).isEqualTo(expectedTitle);
        Assertions.assertThat(task.getStartTime()).isEqualTo(expectedStartTime);
        Assertions.assertThat(task.getIsAlarmOn()).isEqualTo(expectedIsAlarmOn);
        Assertions.assertThat(task.getIsVibrationOn()).isEqualTo(expectedIsVibrationOn);
        Assertions.assertThat(task.getClientId()).isEqualTo(expectedClientId);
    }

    @DisplayName("요청한 Task Id로 Task 객체를 찾을 수 없으면 예외가 발생한다")
    @Test
    void throwExceptionWhenTaskNotFoundByTaskId() {
        // given
        Long taskId = 1L;

        UpdateTaskServiceRequest request =
                UpdateTaskServiceRequest.builder().taskId(taskId).build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> updateTaskService.update(request))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("요청의 Client ID와 조회된 Task 객체의 Client ID 값이 다르면 예외가 발생한다")
    @Test
    void throwExceptionWhenClientIdIsNotEqual() {
        // given
        String clientId = "clientId";
        Long taskId = 1L;

        UpdateTaskServiceRequest request =
                UpdateTaskServiceRequest.builder().taskId(taskId).clientId(clientId).build();

        String savedClientId = "savedClientId";
        Task task = Task.builder().clientId(savedClientId).build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        // when // then
        assertThatThrownBy(() -> updateTaskService.update(request))
                .isInstanceOf(NotFoundException.class);
    }
}
