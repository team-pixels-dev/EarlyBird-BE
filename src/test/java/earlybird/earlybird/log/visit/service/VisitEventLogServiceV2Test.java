package earlybird.earlybird.log.visit.service;

import earlybird.earlybird.log.visit.service.request.VisitEventLoggingServiceRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(OutputCaptureExtension.class)
class VisitEventLogServiceV2Test {

    @DisplayName("사용자 방문 로그를 생성한다")
    @Test
    void createVisitEventLog(CapturedOutput output) {
        // given
        VisitEventLogServiceV2 logService = new VisitEventLogServiceV2();
        String clientId = "CLIENT-ID";
        VisitEventLoggingServiceRequest serviceRequest = new VisitEventLoggingServiceRequest(clientId);

        // when
        logService.create(serviceRequest);

        // then
        assertThat(output.getOut()).contains("visit log: client-id=" + clientId);
        assertThat(output.getOut()).contains("\"client-id\":\"" + clientId + "\"");
        assertThat(output.getOut()).contains("\"event-type\":\"client-visit\"");
    }
}