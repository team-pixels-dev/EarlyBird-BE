package earlybird.earlybird.feedback.dto;

import earlybird.earlybird.user.dto.UserAccountInfoDTO;
import earlybird.earlybird.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class FeedbackDTO {
    private Long id;
    private String content;
    private UserAccountInfoDTO userAccountInfoDTO;
    private LocalDateTime createdAt;
}
