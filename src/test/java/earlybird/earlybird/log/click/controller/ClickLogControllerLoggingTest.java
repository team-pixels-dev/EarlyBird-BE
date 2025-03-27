package earlybird.earlybird.log.click.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import earlybird.earlybird.log.click.controller.request.CreateClickLogRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
public class ClickLogControllerLoggingTest {
    @Autowired private ObjectMapper objectMapper;

    @Autowired private ClickLogController clickLogController;

    @DisplayName("테스트 용 ClientId가 요청으로 들어오면 로그를 남기지 않는다.")
    @Test
    void noLoggingWithTestClientId(CapturedOutput output) throws Exception {
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        String clientId = "test-id";
        String clickType = "timer-start-button-click";

        CreateClickLogRequest requestObject =
                CreateClickLogRequest.builder()
                        .clickTime(clickTime)
                        .clientId(clientId)
                        .clickType(clickType)
                        .build();

        clickLogController.createClickLog(requestObject);

        assertThat(output.getOut()).doesNotContain("\"event-type\":\"client-click\"");
        assertThat(output.getOut()).doesNotContain("\"click-type\":\"" + clickType + "\"");
        assertThat(output.getOut()).doesNotContain("\"client-id\":\"" + clientId + "\"");
        assertThat(output.getOut())
                .doesNotContain(
                        "\"click-time\":\""
                                + clickTime.format(
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                + "\"");
        assertThat(output.getOut())
                .doesNotContain(
                        "Click 이벤트 발생 [click-type: "
                                + clickType
                                + ", client-id: "
                                + clientId
                                + "]");
    }

    @DisplayName("테스트 용이 아닌 ClientId가 요청으로 들어오면 로그를 정상적으로 남긴다.")
    @Test
    void loggingWithNotTestClientId(CapturedOutput output) throws Exception {
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        String clientId = "client-id";
        String clickType = "timer-start-button-click";

        CreateClickLogRequest requestObject =
                CreateClickLogRequest.builder()
                        .clickTime(clickTime)
                        .clientId(clientId)
                        .clickType(clickType)
                        .build();

        clickLogController.createClickLog(requestObject);

        assertThat(output.getOut()).contains("\"event-type\":\"client-click\"");
        assertThat(output.getOut()).contains("\"click-type\":\"" + clickType + "\"");
        assertThat(output.getOut()).contains("\"client-id\":\"" + clientId + "\"");
        assertThat(output.getOut())
                .contains(
                        "\"click-time\":\""
                                + clickTime.format(
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                                + "\"");
        assertThat(output.getOut())
                .contains(
                        "Click 이벤트 발생 [click-type: "
                                + clickType
                                + ", client-id: "
                                + clientId
                                + "]");
    }
}
