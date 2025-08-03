package earlybird.earlybird.promotion.apple;

import earlybird.earlybird.common.BaseTimeEntity;
import earlybird.earlybird.promotion.entity.PromotionCampaign;

import jakarta.persistence.*;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Table(name = "apple_promotion_urls")
@Entity
public class ApplePromotionUrl extends BaseTimeEntity {

    @Column(name = "apple_promotion_urls_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    // 애플에서 제공하는 프로모션 URL
    @Column(name = "apple_promotion_urls_url", nullable = false, unique = true)
    private String url;

    // 설명
    @Column(name = "apple_promotion_urls_description")
    private String description;

    // URL 발급 여부
    // 발급된 URL은 다시 사용하면 안 됨
    @Column(name = "apple_promotion_urls_is_used", nullable = false)
    private Boolean isUsed;

    // 프로모션 URL 만료 시간
    @Column(name = "apple_promotion_urls_expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_campaigns_id", nullable = false)
    private PromotionCampaign promotionCampaign;

    public void setUsed() {
        this.isUsed = true;
    }
}
