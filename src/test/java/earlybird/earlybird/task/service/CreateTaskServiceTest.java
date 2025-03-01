package earlybird.earlybird.task.service;

import earlybird.earlybird.task.domain.Task;
import earlybird.earlybird.task.domain.TaskRepository;
import earlybird.earlybird.task.service.request.CreateTaskServiceRequest;
import earlybird.earlybird.task.service.response.CreateTaskServiceResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CreateTaskService createTaskService;

    @DisplayName("요청된 Task 객체를 저장한다")
    @Test
    void createTask() throws Exception {
        // given
        Long id = 10L;
        CreateTaskServiceRequest request = CreateTaskServiceRequest.builder()
                .clientId("clientId")
                .build();
        Task task = request.toEntity();
        Field taskIdField = Task.class.getDeclaredField("id");
        taskIdField.setAccessible(true);
        taskIdField.set(task, id);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // when
        CreateTaskServiceResponse response = createTaskService.create(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getTaskId()).isEqualTo(id);
        verify(taskRepository).save(any(Task.class));
    }

}