package earlybird.earlybird.log.visit.service;

import earlybird.earlybird.log.visit.domain.VisitEventLog;
import earlybird.earlybird.log.visit.domain.VisitEventLogRepository;
import earlybird.earlybird.log.visit.service.request.VisitEventLoggingServiceRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class VisitEventLogServiceV1 implements VisitEventLogService {

    private final VisitEventLogRepository visitEventLogRepository;

    @Transactional
    public void create(VisitEventLoggingServiceRequest request) {
        visitEventLogRepository.save(new VisitEventLog(request.getClientId()));
    }
}
