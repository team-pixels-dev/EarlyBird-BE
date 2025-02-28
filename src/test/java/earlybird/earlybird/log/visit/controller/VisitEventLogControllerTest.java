package earlybird.earlybird.log.visit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import earlybird.earlybird.log.visit.controller.request.VisitEventLoggingRequest;
import earlybird.earlybird.log.visit.service.VisitEventLogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VisitEventLogController.class)
class VisitEventLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VisitEventLogService visitEventLogService;

    @DisplayName("정상 동작 시 200 응답이 반환된다")
    @WithMockUser(username = "mock-user", roles = {"SUPER"})
    @Test
    void return200WithValidRequest() throws Exception {
        String requestContent = objectMapper.writeValueAsString(new VisitEventLoggingRequest("client-id"));

        mockMvc.perform(post("/api/v1/log/visit-event")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isOk());
    }

    @DisplayName("요청에 client id 값이 누락되면 400 응답이 반환된다")
    @WithMockUser(username = "mock-user", roles = {"SUPER"})
    @Test
    void return400WithNoClientId() throws Exception {
        mockMvc.perform(post("/api/v1/log/visit-event")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 client id 값이 공백이면 400 응답이 반환된다")
    @WithMockUser(username = "mock-user", roles = {"SUPER"})
    @Test
    void return400WithBlankClientId() throws Exception {
        String requestContent = objectMapper.writeValueAsString(new VisitEventLoggingRequest(" "));

        mockMvc.perform(post("/api/v1/log/visit-event")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 client id 값이 빈 문자열이면 400 응답이 반환된다")
    @WithMockUser(username = "mock-user", roles = {"SUPER"})
    @Test
    void return400WithEmptyClientId() throws Exception {
        String requestContent = objectMapper.writeValueAsString(new VisitEventLoggingRequest(""));

        mockMvc.perform(post("/api/v1/log/visit-event")
                        .content(requestContent)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isBadRequest());
    }

}