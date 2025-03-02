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

import java.util.Map;

@Primary
@Slf4j
@RequiredArgsConstructor
@Service
public class VisitEventLogServiceV2 implements VisitEventLogService {

    private final ClientIdRepository clientIdRepository;

    @Override
    public void create(VisitEventLoggingServiceRequest request) {
        LogUtil.log(
                INFO,
                Map.of("client-id", request.getClientId(), "event-type", "client-visit"),
                "visit log: client-id={}",
                request.getClientId());

        clientIdRepository
                .findByClientId(request.getClientId())
                .ifPresentOrElse(
                        clientId -> {},
                        () ->
                                clientIdRepository.save(
                                        ClientId.builder()
                                                .clientId(request.getClientId())
                                                .build()));
    }
}
