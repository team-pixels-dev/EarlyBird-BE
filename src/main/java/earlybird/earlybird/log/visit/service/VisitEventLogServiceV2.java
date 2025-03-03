package earlybird.earlybird.log.visit.service;

import static org.springframework.boot.logging.LogLevel.INFO;

import earlybird.earlybird.common.util.LogUtil;
import earlybird.earlybird.log.visit.domain.ClientId;
import earlybird.earlybird.log.visit.domain.ClientIdRepository;
import earlybird.earlybird.log.visit.service.request.VisitEventLoggingServiceRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Primary
@Slf4j
@RequiredArgsConstructor
@Service
public class VisitEventLogServiceV2 implements VisitEventLogService {

    private final ClientIdRepository clientIdRepository;

    @Override
    public void create(VisitEventLoggingServiceRequest request) {
        Map<String, String> logAttributes = new HashMap<>();
        logAttributes.put("client-id", request.getClientId());
        logAttributes.put("event-type", "client-visit");

        clientIdRepository
                .findByClientId(request.getClientId())
                .ifPresentOrElse(
                        clientId -> {
                            logAttributes.put("first-visit", "false");
                        },
                        () -> {
                            clientIdRepository.save(
                                    ClientId.builder().clientId(request.getClientId()).build());
                            logAttributes.put("first-visit", "true");
                        });

        LogUtil.log(INFO, logAttributes, "visit log: client-id={}", request.getClientId());
    }
}
