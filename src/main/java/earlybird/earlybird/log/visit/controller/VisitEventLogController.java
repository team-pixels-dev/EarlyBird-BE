package earlybird.earlybird.log.visit.controller;

import earlybird.earlybird.log.visit.controller.request.VisitEventLoggingRequest;
import earlybird.earlybird.log.visit.service.VisitEventLogService;
import earlybird.earlybird.log.visit.service.request.VisitEventLoggingServiceRequest;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/log")
@RestController
public class VisitEventLogController {

    private final VisitEventLogService visitEventLogService;

    @PostMapping("/visit-event")
    public ResponseEntity<?> visitEventLogging(
            @Valid @RequestBody VisitEventLoggingRequest request) {
        VisitEventLoggingServiceRequest serviceRequest =
                new VisitEventLoggingServiceRequest(request.getClientId());
        visitEventLogService.create(serviceRequest);
        return ResponseEntity.ok().build();
    }
}
