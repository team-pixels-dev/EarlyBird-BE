package earlybird.earlybird.log;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(OutputCaptureExtension.class)
@ExtendWith(SpringExtension.class)
class MdcLogTraceFilterTest {

    @Autowired private MockMvc mockMvc;

    @WithMockUser(username="user", roles = {"USER"})
    @DisplayName("모든 요청의 각 로그에는 요청마다 고유한 trace id가 기록된다")
    @Test
    void traceId(CapturedOutput output) throws Exception {
        // given
        mockMvc.perform(MockMvcRequestBuilders.get("/not-found-uri"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        assertThat(output.getOut()).contains("\"trace-id\":");
    }

    @WithMockUser(username="user", roles = {"USER"})
    @DisplayName("모든 HTTP 요청의 각 로그에는 요청 URI 가 기록된다")
    @Test
    void requestUri(CapturedOutput output) throws Exception {
        // given
        mockMvc.perform(MockMvcRequestBuilders.get("/not-found-uri"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        assertThat(output.getOut()).contains("\"request-uri\":\"/not-found-uri\"");
    }

    @WithMockUser(username="user", roles = {"USER"})
    @DisplayName("모든 HTTP 요청의 각 로그에는 요청 IP 가 기록된다")
    @Test
    void test(CapturedOutput output) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/uri"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        assertThat(output.getOut())
                .containsAnyOf(
                        "\"request-ip\":\"0:0:0:0:0:0:0:1\"", "\"request-ip\":\"127.0.0.1\"");
    }
}
