package earlybird.earlybird.log.visit.controller;

import static org.assertj.core.api.Assertions.assertThat;

import earlybird.earlybird.log.visit.controller.request.VisitEventLoggingRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@ExtendWith(OutputCaptureExtension.class)
@SpringBootTest
public class VisitEventLogControllerLoggingTest {

    @Autowired private VisitEventLogController visitEventLogController;

    @DisplayName("테스트 용 ClientId가 요청으로 들어오면 로그를 남기지 않는다.")
    @Test
    void noLoggingWithTestClientId(CapturedOutput output) {
        String clientId = "test-id";
        VisitEventLoggingRequest request = new VisitEventLoggingRequest(clientId);

        visitEventLogController.visitEventLogging(request);

        assertThat(output.getOut()).doesNotContain("visit log: client-id=" + clientId);
        assertThat(output.getOut()).doesNotContain("\"client-id\":\"" + clientId + "\"");
        assertThat(output.getOut()).doesNotContain("\"event-type\":\"client-visit\"");
    }

    @DisplayName("테스트 용이 아닌 ClientId가 요청으로 들어오면 로그를 정상적으로 남긴다.")
    @Test
    void loggingWithNotTestClientId(CapturedOutput output) {
        String clientId = "client-id";
        VisitEventLoggingRequest request = new VisitEventLoggingRequest(clientId);

        visitEventLogController.visitEventLogging(request);

        assertThat(output.getOut()).contains("visit log: client-id=" + clientId);
        assertThat(output.getOut()).contains("\"client-id\":\"" + clientId + "\"");
        assertThat(output.getOut()).contains("\"event-type\":\"client-visit\"");
    }
}
