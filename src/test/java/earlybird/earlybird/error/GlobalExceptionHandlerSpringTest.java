package earlybird.earlybird.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerSpringTest {

    @Autowired private MockMvc mockMvc;

    @WithMockUser(
            username = "user",
            roles = {"USER"})
    @DisplayName("예외가 발생하면 예외 공통 로그가 기록된다")
    @Test
    void exception(CapturedOutput output) throws Exception {
        mockMvc.perform(get("/not-found-uri")).andExpect(status().isNotFound());

        assertThat(output.getOut()).contains("\"exception-type\":\"NoResourceFoundException\"");
        assertThat(output.getOut())
                .contains("\"exception-message\":\"No static resource not-found-uri.\"");
        //        assertThat(output.getOut()).contains("\"request-uri\":\"/not-found-uri\"");
    }

    @WithMockUser(
            username = "user",
            roles = {"USER"})
    @DisplayName("예외가 발생하지 않으면 예외 공통 로그가 기록되지 않는다")
    @Test
    void notException(CapturedOutput output) throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());

        assertThat(output.getOut()).doesNotContain("\"exception-type\"");
        assertThat(output.getOut()).doesNotContain("\"exception-message\"");
    }
}
