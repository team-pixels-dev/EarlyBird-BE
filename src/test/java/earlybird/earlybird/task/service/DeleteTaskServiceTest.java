package earlybird.earlybird.task.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import earlybird.earlybird.error.exception.NotFoundException;
import earlybird.earlybird.task.domain.Task;
import earlybird.earlybird.task.domain.TaskRepository;
import earlybird.earlybird.task.service.request.DeleteTaskServiceRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DeleteTaskServiceTest {

    @Mock private TaskRepository taskRepository;

    @InjectMocks private DeleteTaskService deleteTaskService;

    @DisplayName("요청된 Task 객체를 삭제한다")
    @Test
    void deleteTask() {
        // given
        Long taskId = 100L;
        String clientId = "clientId";

        DeleteTaskServiceRequest request =
                DeleteTaskServiceRequest.builder().taskId(taskId).clientId(clientId).build();

        Task task = Task.builder().clientId(clientId).build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // when
        deleteTaskService.delete(request);

        // then
        verify(taskRepository).delete(task);
    }

    @DisplayName("요청된 Task ID로 Task 객체를 찾을 수 없으면 예외가 발생한다")
    @Test
    void throwExceptionWhenTaskNotFound() {
        // given
        Long taskId = 100L;
        String clientId = "clientId";

        DeleteTaskServiceRequest request =
                DeleteTaskServiceRequest.builder().taskId(taskId).clientId(clientId).build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> deleteTaskService.delete(request))
                .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("조회된 Task의 clientId와 요청 clientId가 다르면 예외가 발생한다")
    @Test
    void throwExceptionWhenClientIdIsNotEqual() {
        // given
        Long taskId = 100L;
        String requestedClientId = "requestedClientId";
        String savedClientId = "savedClientId";

        DeleteTaskServiceRequest request =
                DeleteTaskServiceRequest.builder()
                        .taskId(taskId)
                        .clientId(requestedClientId)
                        .build();

        Task task = Task.builder().clientId(savedClientId).build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // when // then
        assertThatThrownBy(() -> deleteTaskService.delete(request))
                .isInstanceOf(NotFoundException.class);
    }
}
