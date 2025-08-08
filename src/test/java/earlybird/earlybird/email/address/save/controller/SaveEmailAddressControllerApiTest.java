package earlybird.earlybird.email.address.save.controller;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import earlybird.earlybird.email.address.save.controller.request.SaveEmailAddressRequest;
import earlybird.earlybird.email.address.save.entity.MarketingEvent;
import earlybird.earlybird.email.address.save.service.SaveEmailAddressService;
import earlybird.earlybird.email.address.save.service.request.SaveEmailAddressServiceRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = SaveEmailAddressController.class)
class SaveEmailAddressControllerApiTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;

    @MockBean private SaveEmailAddressService saveEmailAddressService;

    @DisplayName("정상 이메일 저장 요청이 들어오면 200 OK 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return200WithValidRequest() throws Exception {
        SaveEmailAddressRequest requestObject =
                SaveEmailAddressRequest.builder()
                        .email("test@example.com")
                        .sourceEvent(MarketingEvent.WEB_MINI_GAME_1)
                        .build();
        String request = objectMapper.writeValueAsString(requestObject);

        doNothing().when(saveEmailAddressService).save(any(SaveEmailAddressServiceRequest.class));

        mockMvc.perform(
                        post("/api/v1/marketing/email/address")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isOk());
    }

    @DisplayName("요청의 이메일이 null, 빈 문자열, 공백이면 400 Bad Request 응답이 반환된다 (Not Blank)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenEmailIsBlank() throws Exception {
        String nullEmail = null;
        String emptyEmail = "";
        String whitespaceEmail = "  ";

        String requestWithNullEmail =
                """
                {
                    "sourceEvent": MarketingEvent.WEB_MINI_GAME_1
                }        
                """;
        String requestWithEmptyEmail =
                """
                {   
                    "email": "",
                    "sourceEvent": MarketingEvent.WEB_MINI_GAME_1
                }        
                """;
        String requestWithWhitespaceEmail =
                """
                {
                    "email": "     ",
                    "sourceEvent": MarketingEvent.WEB_MINI_GAME_1
                }        
                """;

        mockMvc.perform(
                        post("/api/v1/marketing/email/address")
                                .content(requestWithNullEmail)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/marketing/email/address")
                                .content(requestWithEmptyEmail)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());

        mockMvc.perform(
                        post("/api/v1/marketing/email/address")
                                .content(requestWithWhitespaceEmail)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 소스 이벤트가 null 이면 400 Bad Request 응답이 반환된다 (Not Null)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenSourceEventIsNull() throws Exception {
        String request =
                """
                {
                    "email": "test@test.com"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/marketing/email/address")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("요청의 소스 이벤트가 존재하지 않는 값 이면 400 Bad Request 응답이 반환된다 (Not Null)")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenInvalidSourceEvent() throws Exception {
        String request =
                """
                {
                    "email": "test@test.com",
                    "sourceEvent": "INVALID"
                }
                """;

        mockMvc.perform(
                        post("/api/v1/marketing/email/address")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }



    @DisplayName("잘못된 이메일 형식이면 IllegalArgumentException으로 인해 400 Bad Request 응답이 반환된다")
    @WithMockUser(
            username = "mock-user",
            roles = {"SUPER"})
    @Test
    void return400WhenEmailFormatIsInvalid() throws Exception {
        SaveEmailAddressRequest requestObject =
                SaveEmailAddressRequest.builder()
                        .email("invalid-email")
                        .sourceEvent(MarketingEvent.WEB_MINI_GAME_1)
                        .build();
        String request = objectMapper.writeValueAsString(requestObject);

        doThrow(new IllegalArgumentException("Invalid email address: invalid-email"))
                .when(saveEmailAddressService).save(any(SaveEmailAddressServiceRequest.class));

        mockMvc.perform(
                        post("/api/v1/marketing/email/address")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }
}