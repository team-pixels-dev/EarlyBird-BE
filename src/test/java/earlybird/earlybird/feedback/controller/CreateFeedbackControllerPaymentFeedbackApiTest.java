package earlybird.earlybird.feedback.controller;

import static earlybird.earlybird.feedback.domain.pay.PaymentFeedbackLevel.WILL_PAY;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import earlybird.earlybird.feedback.controller.request.CreatePaymentFeedbackRequest;
import earlybird.earlybird.feedback.domain.pay.PaymentFeedbackLevel;
import earlybird.earlybird.feedback.service.anonymous.CreateAnonymousFeedbackCommentService;
import earlybird.earlybird.feedback.service.anonymous.CreateAnonymousFeedbackScoreService;
import earlybird.earlybird.feedback.service.anonymous.CreateAnonymousPaymentFeedbackService;
import earlybird.earlybird.feedback.service.auth.CreateAuthFeedbackCommentService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

@WebMvcTest(controllers = CreateFeedbackController.class)
class CreateFeedbackControllerPaymentFeedbackApiTest {
    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private CreateAuthFeedbackCommentService createAuthFeedbackCommentService;
    @MockBean private CreateAnonymousFeedbackCommentService createAnonymousFeedbackCommentService;
    @MockBean private CreateAnonymousFeedbackScoreService createAnonymousFeedbackScoreService;
    @MockBean private CreateAnonymousPaymentFeedbackService createAnonymousPaymentFeedbackService;

    @DisplayName("정상 create 요청이 들어오면 200 OK 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return200WithValidRequest() throws Exception {
        CreatePaymentFeedbackRequest requestObject =
                CreatePaymentFeedbackRequest.builder()
                        .level(WILL_PAY)
                        .createdAt(LocalDateTime.of(2025, 3, 19, 0, 0, 0))
                        .clientId("client_id")
                        .build();

        String request = objectMapper.writeValueAsString(requestObject);

        mockMvc.perform(
                        post("/api/v1/feedbacks/payments")
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
        LocalDateTime createdAt = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        PaymentFeedbackLevel level = WILL_PAY;

        String url = "/api/v1/feedbacks/payments";

        String requestWithNullClientId =
                objectMapper.writeValueAsString(
                        CreatePaymentFeedbackRequest.builder()
                                .createdAt(createdAt)
                                .level(level)
                                .build());

        String requestWithEmptyClientId =
                objectMapper.writeValueAsString(
                        CreatePaymentFeedbackRequest.builder()
                                .clientId("")
                                .createdAt(createdAt)
                                .level(level)
                                .build());

        String requestWithWhitespaceClientId =
                objectMapper.writeValueAsString(
                        CreatePaymentFeedbackRequest.builder()
                                .clientId("   ")
                                .createdAt(createdAt)
                                .level(level)
                                .build());

        mockMvc.perform(
                        post(url)
                                .content(requestWithNullClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post(url)
                                .content(requestWithEmptyClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post(url)
                                .content(requestWithWhitespaceClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청에 level 값이 null 이면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WithNullPaymentFeedbackLevel() throws Exception {
        LocalDateTime createdAt = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        PaymentFeedbackLevel level = WILL_PAY;

        String url = "/api/v1/feedbacks/payments";

        String requestWithWhitespaceClientId =
                objectMapper.writeValueAsString(
                        CreatePaymentFeedbackRequest.builder()
                                .clientId("   ")
                                .createdAt(createdAt)
                                .level(level)
                                .build());

        mockMvc.perform(
                        post(url)
                                .content(requestWithWhitespaceClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청에 level 값이 PaymentFeedbackLevel Enum 에 정의된 값이 아니면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WithPaymentFeedbackLevelNotInEnum() throws Exception {
        String url = "/api/v1/feedbacks/payments";

        String requestWithInvalidLevel =
                "{\n"
                        + "    \"level\": \"INVALID\",\n"
                        + "    \"clientId\": \"test-id\",\n"
                        + "    \"createdAt\": \"2025-03-19 15:55:00\"\n"
                        + "}";

        mockMvc.perform(
                        post(url)
                                .content(requestWithInvalidLevel)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청에 createdAt 값이 null 이면 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WithNullCreatedAt() throws Exception {
        String url = "/api/v1/feedbacks/payments";

        String requestWithWhitespaceClientId =
                objectMapper.writeValueAsString(
                        CreatePaymentFeedbackRequest.builder()
                                .clientId("client_id")
                                .level(WILL_PAY)
                                .build());

        mockMvc.perform(
                        post(url)
                                .content(requestWithWhitespaceClientId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}
