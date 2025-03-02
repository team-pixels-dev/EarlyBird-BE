package earlybird.earlybird.task.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import earlybird.earlybird.task.controller.request.CreateTaskRequest;
import earlybird.earlybird.task.service.CreateTaskService;
import earlybird.earlybird.task.service.DeleteTaskService;
import earlybird.earlybird.task.service.UpdateTaskService;
import earlybird.earlybird.task.service.request.CreateTaskServiceRequest;
import earlybird.earlybird.task.service.response.CreateTaskServiceResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerCreateApiTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private CreateTaskService createTaskService;
    @MockBean private DeleteTaskService deleteTaskService;
    @MockBean private UpdateTaskService updateTaskService;

    @DisplayName("정상 create 요청이 들어오면 200 OK 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return200WithValidRequest() throws Exception {
        CreateTaskRequest requestObject =
                CreateTaskRequest.builder()
                        .title("title")
                        .startTime(LocalDateTime.of(2025, 1, 1, 0, 0))
                        .isAlarmOn(true)
                        .isVibrationOn(true)
                        .clientId("clientId")
                        .build();
        String request = objectMapper.writeValueAsString(requestObject);

        Long expectedTaskId = 100L;
        CreateTaskServiceResponse expectedServiceResponse =
                CreateTaskServiceResponse.builder().taskId(expectedTaskId).build();
        when(createTaskService.create(any(CreateTaskServiceRequest.class)))
                .thenReturn(expectedServiceResponse);

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskId").value(expectedTaskId));
    }

    @DisplayName("요청의 Task 제목이 null, 빈 문자열, 공백이면 400 Bad Request 응답이 반환된다 (Not Blank)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenTitleIsBlank() throws Exception {
        String nullTitle = null;
        String emptyTitle = "";
        String whitespaceTitle = "  ";

        String requestWithNullTitle =
                objectMapper.writeValueAsString(initCreateTaskRequest(nullTitle, "clientId"));
        String requestWithEmptyTitle =
                objectMapper.writeValueAsString(initCreateTaskRequest(emptyTitle, "clientId"));
        String requestWithWhitespaceTitle =
                objectMapper.writeValueAsString(initCreateTaskRequest(whitespaceTitle, "clientId"));

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(requestWithNullTitle)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(requestWithEmptyTitle)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(requestWithWhitespaceTitle)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 Task 시작 시간이 null 이면 400 Bad Request 응답이 반환된다 (Not Null)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenStartTimeIsNull() throws Exception {
        CreateTaskRequest requestObject =
                CreateTaskRequest.builder()
                        .title("title")
                        .startTime(null)
                        .isAlarmOn(true)
                        .isVibrationOn(true)
                        .clientId("clientId")
                        .build();
        String request = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 Task 시작 시간 형식이 yyyy-MM-dd HH:mm:ss가 아니면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenStartTimeFormatIsInvalid() throws Exception {
        String request =
                """
                {
                    "title": "title",
                    "startTime": "2025/01/01 12-00-00",
                    "isAlarmOn": true,
                    "isVibrationOn": true,
                    "clientId": "clientId",
                }
                """;

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 Task 알람 정보가 null 이면 400 Bad Request 응답이 반환된다 (Not Null)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenIsAlarmOnIsNull() throws Exception {
        CreateTaskRequest requestObject =
                CreateTaskRequest.builder()
                        .title("title")
                        .startTime(LocalDateTime.of(2025, 1, 1, 0, 0))
                        .isVibrationOn(true)
                        .clientId("clientId")
                        .build();
        String request = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 Task 진동 정보가 null 이면 400 Bad Request 응답이 반환된다 (Not Null)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenIsVibrationOnIsNull() throws Exception {
        CreateTaskRequest requestObject =
                CreateTaskRequest.builder()
                        .title("title")
                        .startTime(LocalDateTime.of(2025, 1, 1, 0, 0))
                        .isAlarmOn(true)
                        .clientId("clientId")
                        .build();
        String request = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 client id가 null, 빈 문자열, 공백이면 400 Bad Request 응답이 반환된다 (Not Blank)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenClientIdIsBlank() throws Exception {
        String nullClientId = null;
        String emptyClientId = "";
        String whitespaceClientId = "  ";

        String requestWithNullClientId =
                objectMapper.writeValueAsString(initCreateTaskRequest("title", nullClientId));
        String requestWithEmptyClientId =
                objectMapper.writeValueAsString(initCreateTaskRequest("title", emptyClientId));
        String requestWithWhitespaceClientId =
                objectMapper.writeValueAsString(initCreateTaskRequest("title", whitespaceClientId));

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(requestWithNullClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(requestWithEmptyClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/tasks")
                                .content(requestWithWhitespaceClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    private CreateTaskRequest initCreateTaskRequest(String title, String clientId) {
        return CreateTaskRequest.builder()
                .title(title)
                .startTime(LocalDateTime.of(2025, 1, 1, 0, 0))
                .isAlarmOn(true)
                .isVibrationOn(true)
                .clientId(clientId)
                .build();
    }
}
