package earlybird.earlybird.promotion.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import earlybird.earlybird.promotion.entity.PromotionCampaignType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreatePromotionCampaignRequest {

    @NotBlank
    private String promotionCampaignName;

    @NotBlank
    private String promotionCampaignDescription;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime startTime;

    @NotNull
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime endTime;

    @NotNull
    private Long perUserLimit;

    @NotNull
    private Long totalIssueLimit;

    @NotNull
    private PromotionCampaignType promotionCampaignType;
}
