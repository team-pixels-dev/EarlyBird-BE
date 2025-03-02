package earlybird.earlybird.log.click.service;

import earlybird.earlybird.common.util.LogUtil;
import earlybird.earlybird.log.click.domain.UserClickLogCount;
import earlybird.earlybird.log.click.domain.UserClickLogCountRepository;
import earlybird.earlybird.log.click.service.request.CreateClickLogServiceRequest;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Transactional
@RequiredArgsConstructor
@Service
public class CreateClickLogService {

    private final UserClickLogCountRepository userClickLogCountRepository;

    public void create(CreateClickLogServiceRequest request) {
        createLog(request);
        updateClickLogStats(request);
    }

    private void createLog(CreateClickLogServiceRequest request) {
        Map<String, String> logAttributes =
                Map.of(
                        "event-type", "client-click",
                        "click-type", request.getClickType(),
                        "client-id", request.getClientId(),
                        "click-time",
                                request.getClickTime()
                                        .format(
                                                DateTimeFormatter.ofPattern(
                                                        "yyyy-MM-dd HH:mm:ss")));
        String logMessage = "Click 이벤트 발생 [click-type: {}, client-id: {}]";
        LogUtil.log(
                LogLevel.INFO,
                logAttributes,
                logMessage,
                request.getClickType(),
                request.getClientId());
    }

    private void updateClickLogStats(CreateClickLogServiceRequest request) {
        UserClickLogCount userClickLogCount =
                userClickLogCountRepository
                        .findByClientIdAndClickTypeAndClickDate(
                                request.getClientId(),
                                request.getClickType(),
                                request.getClickTime().toLocalDate())
                        .orElseGet(() -> createUserClickLogCount(request));

        userClickLogCount.increaseClickCount();
    }

    private UserClickLogCount createUserClickLogCount(CreateClickLogServiceRequest request) {
        return userClickLogCountRepository.save(
                UserClickLogCount.builder()
                        .clientId(request.getClientId())
                        .clickType(request.getClickType())
                        .clickDate(request.getClickTime().toLocalDate())
                        .build());
    }
}
