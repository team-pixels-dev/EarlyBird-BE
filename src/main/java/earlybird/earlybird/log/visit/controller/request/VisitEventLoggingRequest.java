package earlybird.earlybird.log.visit.controller.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VisitEventLoggingRequest {

    @NotBlank
    private String clientId;
}
