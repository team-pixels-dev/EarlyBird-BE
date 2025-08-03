package earlybird.earlybird.promotion.entity;

import lombok.Getter;

public enum PromotionCampaignType {
    EDU_EMAIL_6_MONTH_FREE("대학생 6개월 무료 프로모션");

    @Getter
    private final String description;

    PromotionCampaignType(String description) {
        this.description = description;
    }
}
