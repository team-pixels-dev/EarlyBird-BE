package earlybird.earlybird.log.visit.service;

import earlybird.earlybird.log.visit.service.request.VisitEventLoggingServiceRequest;

public interface VisitEventLogService {
    void create(VisitEventLoggingServiceRequest request);
}
