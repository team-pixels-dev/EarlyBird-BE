package earlybird.earlybird.log.click.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import earlybird.earlybird.log.click.domain.UserClickLogCount;
import earlybird.earlybird.log.click.domain.UserClickLogCountRepository;
import earlybird.earlybird.log.click.service.request.CreateClickLogServiceRequest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ExtendWith({MockitoExtension.class, OutputCaptureExtension.class})
@TestPropertySource("classpath:logback-spring.xml")
class CreateClickLogServiceTest {

    @Mock private UserClickLogCountRepository userClickLogCountRepository;

    @Mock private UserClickLogCount userClickLogCount;

    @InjectMocks private CreateClickLogService service;

    @DisplayName("클릭 이벤트에 대한 로그를 남긴다")
    @Test
    void createLogWithInfoLevel(CapturedOutput output) throws Exception {
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        String clientId = "clientId";
        String clickType = "clickType";

        CreateClickLogServiceRequest request =
                CreateClickLogServiceRequest.builder()
                        .clickTime(clickTime)
                        .clientId(clientId)
                        .clickType(clickType)
                        .build();

        when(userClickLogCountRepository.findByClientIdAndClickTypeAndClickDate(
                        any(), any(), any()))
                .thenReturn(Optional.of(userClickLogCount));

        service.create(request);

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
        assertThat(output.getOut()).contains("INFO");
    }

    @DisplayName("클릭 이벤트 로그가 출력된 후 출력 횟수를 증가시킨다")
    @Test
    void invokeIncreaseClickCount() {
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        String clientId = "clientId";
        String clickType = "clickType";
        CreateClickLogServiceRequest request =
                CreateClickLogServiceRequest.builder()
                        .clickTime(clickTime)
                        .clientId(clientId)
                        .clickType(clickType)
                        .build();

        when(userClickLogCountRepository.findByClientIdAndClickTypeAndClickDate(
                        any(), any(), any()))
                .thenReturn(Optional.of(userClickLogCount));

        service.create(request);

        verify(userClickLogCount).increaseClickCount();
    }

    @DisplayName(
            "UserClickLogCountRepository 에 요청과 관련된 정보가 저장되어 있지 않다면 새로운 UserClickLogCount 객체를 만든다")
    @Test
    void saveNewUserClickLogCount() {
        LocalDateTime clickTime = LocalDateTime.of(2025, 3, 2, 0, 0, 0);
        String clientId = "clientId";
        String clickType = "clickType";
        CreateClickLogServiceRequest request =
                CreateClickLogServiceRequest.builder()
                        .clickTime(clickTime)
                        .clientId(clientId)
                        .clickType(clickType)
                        .build();
        when(userClickLogCountRepository.findByClientIdAndClickTypeAndClickDate(
                        any(), any(), any()))
                .thenReturn(Optional.empty());
        when(userClickLogCountRepository.save(any(UserClickLogCount.class)))
                .thenReturn(userClickLogCount);

        service.create(request);

        verify(userClickLogCountRepository).save(any(UserClickLogCount.class));
        verify(userClickLogCount).increaseClickCount();
    }
}
