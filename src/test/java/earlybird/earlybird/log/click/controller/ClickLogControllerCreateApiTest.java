package earlybird.earlybird.log.click.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import earlybird.earlybird.log.click.controller.request.CreateClickLogRequest;
import earlybird.earlybird.log.click.service.CreateClickLogService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(controllers = ClickLogController.class)
class ClickLogControllerCreateApiTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private CreateClickLogService createClickLogService;

    @DisplayName("정상 create 요청이 들어오면 200 OK 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return200WithValidRequest() throws Exception {
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        String clientId = "clientId";
        String clickType = "timer-start-button-click";

        CreateClickLogRequest requestObject =
                CreateClickLogRequest.builder()
                        .clickTime(clickTime)
                        .clientId(clientId)
                        .clickType(clickType)
                        .build();

        String request = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("요청에 clientId 값이 null/빈 문자열/공백이면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WithBlankClientId() throws Exception {
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        String clickType = "timer-start-button-click";

        String requestWithNullClientId =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clickTime(clickTime)
                                .clickType(clickType)
                                .build());

        String requestWithEmptyClientId =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clientId("")
                                .clickTime(clickTime)
                                .clickType(clickType)
                                .build());

        String requestWithWhitespaceClientId =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clientId("   ")
                                .clickTime(clickTime)
                                .clickType(clickType)
                                .build());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithNullClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithEmptyClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithWhitespaceClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청에 clickType 값이 null/빈 문자열/공백이면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WithBlankClickType() throws Exception {
        String clientId = "clientId";
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);

        String requestWithNullClickType =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clientId(clientId)
                                .clickTime(clickTime)
                                .build());

        String requestWithEmptyClickType =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clientId(clientId)
                                .clickTime(clickTime)
                                .clickType("")
                                .build());

        String requestWithWhitespaceClickType =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clientId(clientId)
                                .clickTime(clickTime)
                                .clickType("   ")
                                .build());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithNullClickType)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithEmptyClickType)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithWhitespaceClickType)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청에 clickTime 값이 null이면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WithNullClickTime() throws Exception {
        String clickType = "timer-start-button-click";
        String clientId = "clientId";

        String requestWithNullClickTime =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clientId(clientId)
                                .clickType(clickType)
                                .build());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithNullClickTime)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청에 유효하지 않은 clickType 값이 있으면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WithInvalidClickType() throws Exception {
        String clickType = "invalid";
        String clientId = "clientId";
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);

        String requestWithNullClickTime =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clickTime(clickTime)
                                .clientId(clientId)
                                .clickType(clickType)
                                .build());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithNullClickTime)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("timer-start-button-click 은 유효한 clickType 값이다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return200WithClickTypeTimerStartButtonClick() throws Exception {
        String clickType = "timer-start-button-click";
        String clientId = "clientId";
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);

        String requestWithNullClickTime =
                objectMapper.writeValueAsString(
                        CreateClickLogRequest.builder()
                                .clickTime(clickTime)
                                .clientId(clientId)
                                .clickType(clickType)
                                .build());

        mockMvc.perform(
                        post("/api/v1/log/click")
                                .content(requestWithNullClickTime)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isOk());
    }
}
