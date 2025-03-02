package earlybird.earlybird.log.click.service.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
public class CreateClickLogServiceRequest {
    private final String clientId;
    private final String clickType;
    private final LocalDateTime clickTime;
}
