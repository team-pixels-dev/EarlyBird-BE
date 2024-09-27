package earlybird.earlybird.feedback.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedbackRequestDTO {
    private String content;

    /**
     * format: "yyyy-MM-dd HH:mm:ss.SSS"
     */
    private String createdAt;
}
