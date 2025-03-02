package earlybird.earlybird.log.click.controller;

import earlybird.earlybird.log.click.controller.request.CreateClickLogRequest;
import earlybird.earlybird.log.click.service.CreateClickLogService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/log/click")
@RestController
public class ClickLogController {

    private final CreateClickLogService createClickLogService;

    @PostMapping
    public ResponseEntity<?> createClickLog(@Valid @RequestBody CreateClickLogRequest request) {
        if (!checkClickTypeIsValid(request)) {
            throw new IllegalArgumentException();
        }
        createClickLogService.create(request.toServiceRequest());
        return ResponseEntity.ok().build();
    }

    private boolean checkClickTypeIsValid(CreateClickLogRequest request) {
        List<String> validClickTypes = List.of("timer-start-button-click");
        return validClickTypes.contains(request.getClickType());
    }
}
