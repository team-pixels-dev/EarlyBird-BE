package earlybird.earlybird.log.visit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import earlybird.earlybird.log.visit.domain.ClientId;
import earlybird.earlybird.log.visit.domain.ClientIdRepository;
import earlybird.earlybird.log.visit.service.request.VisitEventLoggingServiceRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
class VisitEventLogServiceV2Test {

    @Mock private ClientIdRepository clientIdRepository;

    @InjectMocks private VisitEventLogServiceV2 logService;

    @DisplayName("사용자 방문 로그를 생성한다")
    @Test
    void createVisitEventLog(CapturedOutput output) {
        // given
        String clientId = "CLIENT-ID";
        VisitEventLoggingServiceRequest serviceRequest =
                new VisitEventLoggingServiceRequest(clientId);

        // when
        logService.create(serviceRequest);

        // then
        assertThat(output.getOut()).contains("visit log: client-id=" + clientId);
        assertThat(output.getOut()).contains("\"client-id\":\"" + clientId + "\"");
        assertThat(output.getOut()).contains("\"event-type\":\"client-visit\"");
    }

    @DisplayName("처음 요청한 client 의 clientId 값을 저장한다")
    @Test
    void saveClientIdOfFirstRequestClient() {
        String clientId = "CLIENT-ID";
        VisitEventLoggingServiceRequest serviceRequest =
                new VisitEventLoggingServiceRequest(clientId);

        logService.create(serviceRequest);

        verify(clientIdRepository).save(any());
    }

    @DisplayName("처음 방문한 사용자의 방문 로그에는 first-visit 이 true 로 기록된다")
    @Test
    void firstVisitIsTrue(CapturedOutput output) {
        // given
        String clientId = "CLIENT-ID";
        VisitEventLoggingServiceRequest serviceRequest =
                new VisitEventLoggingServiceRequest(clientId);

        when(clientIdRepository.findByClientId(clientId)).thenReturn(Optional.empty());

        // when
        logService.create(serviceRequest);

        // then
        assertThat(output.getOut()).contains("\"first-visit\":\"true\"");
    }

    @DisplayName("이전에 방문한 적 있는 사용자의 방문 로그에는 first-visit 이 false 로 기록된다")
    @Test
    void firstVisitIsFalse(CapturedOutput output) {
        // given
        String clientId = "CLIENT-ID";
        VisitEventLoggingServiceRequest serviceRequest =
                new VisitEventLoggingServiceRequest(clientId);

        when(clientIdRepository.findByClientId(clientId)).thenReturn(Optional.of(new ClientId()));

        // when
        logService.create(serviceRequest);

        // then
        assertThat(output.getOut()).contains("\"first-visit\":\"false\"");
    }
}
