package earlybird.earlybird.log.visit.service;

import earlybird.earlybird.common.util.LogUtil;
import earlybird.earlybird.log.visit.service.request.VisitEventLoggingServiceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

import static org.springframework.boot.logging.LogLevel.INFO;

@Slf4j
@Primary
@Service
public class VisitEventLogServiceV2 implements VisitEventLogService {
    @Override
    public void create(VisitEventLoggingServiceRequest request) {
        LogUtil.log(
                INFO, Map.of("client-id", request.getClientId(), "event-type", "client-visit"),
                "visit log: client-id={}", request.getClientId());
    }
}
