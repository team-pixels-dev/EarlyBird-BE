package earlybird.earlybird.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import earlybird.earlybird.task.service.CreateTaskService;
import earlybird.earlybird.task.service.DeleteTaskService;
import earlybird.earlybird.task.service.UpdateTaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
public class TaskControllerDeleteApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateTaskService createTaskService;
    @MockBean
    private DeleteTaskService deleteTaskService;
    @MockBean
    private UpdateTaskService updateTaskService;

    @DisplayName("정상 delete 요청이 들어오면 204 No Content 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return204WithValidRequest() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/tasks")
                                .header("clientId", "c-id")
                                .header("taskId", 100)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @DisplayName("요청의 client id가 null, 빈 문자열, 공백이면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenClientIdIsBlank() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/tasks")
                                .header("taskId", 100)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        delete("/api/v1/tasks")
                                .header("clientId", "")
                                .header("taskId", 100)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        delete("/api/v1/tasks")
                                .header("clientId", "  ")
                                .header("taskId", 100)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 Task Id가 null 이면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenTaskIdIsNull() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/tasks")
                                .header("clientId", "c-id")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 Task Id가 숫자가 아니면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenTaskIdIsNotNumber() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/tasks")
                                .header("taskId", "not valid")
                                .header("clientId", "c-id")
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}
